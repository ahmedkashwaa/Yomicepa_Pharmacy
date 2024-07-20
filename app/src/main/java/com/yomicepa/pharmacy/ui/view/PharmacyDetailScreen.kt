package com.yomicepa.pharmacy.ui.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yomicepa.pharmacy.data.model.CreateReturnRequest
import com.yomicepa.pharmacy.data.model.PharmacyDetailResponse
import com.yomicepa.pharmacy.data.model.PharmacyLink
import com.yomicepa.pharmacy.data.model.ReturnRequestItem
import com.yomicepa.pharmacy.data.model.Wholesaler
import com.yomicepa.pharmacy.ui.viewmodel.PharmacyDetailViewModel
import com.yomicepa.pharmacy.utils.formatTimestamp

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmacyDetailScreen(pharmacyId: String, navController: NavController,viewModel: PharmacyDetailViewModel = hiltViewModel()) {
    viewModel.fetchPharmacyDetail(pharmacyId)

    val pharmacyDetail by viewModel.pharmacyDetail.collectAsState()
    val returnRequestsResponse by viewModel.returnRequests.collectAsState()
    val createReturnRequestsResponse by viewModel.createReturnRequests.collectAsState()
    val nextPage by viewModel.nextPage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showWholesalers by remember { mutableStateOf(false) }
    var returnRequests by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pharmacy Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                })

        },
        content = { padding ->
            Box(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (errorMessage != null) {
                    Text("Error: $errorMessage", modifier = Modifier.align(Alignment.Center))
                } else {
                    pharmacyDetail?.let {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Pharmacy ID: ${it.pharmacy.id}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Doing Business As: ${it.pharmacy.doingBusinessAs}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Legal Business Name: ${it.pharmacy.legalBusinessName}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Company Type: ${it.pharmacy.companyType}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "License State: ${it.pharmacy.licenseState}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "DEA: ${it.pharmacy.dea}")

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(onClick = { showWholesalers = !showWholesalers }) {
                                Text(text = if (showWholesalers) "Hide Wholesalers" else "Show Wholesalers")
                            }

                            Row {
                                Button(onClick = {
                                  //  viewModel.createReturnRequests(pharmacyId = pharmacyId, request = CreateReturnRequest(wholesalerId = "1", serviceType = "EXPRESS_SERVICE"))
                                    navController.navigate("create_return_request/${pharmacyId}")

                                }) {

                                    Text(text =  "Create Return Request" )
                                }
                                Button(onClick = {
                                    viewModel.getReturnRequests(pharmacyId)
                                    returnRequests = true
                                }) {
                                    Text(text =  "List Return Requests" )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            if (showWholesalers) {
                                it.pharmacy.wholesalersLinks.forEach { wholesaler ->
                                    WholesalerItem(wholesaler)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                            if (returnRequests) {
                                returnRequestsResponse?.let { response ->
                                    if (response.isSuccessful) {
                                        val returnRequests = response.body()?.content
                                        LazyColumn(state = listState) {
                                            items(returnRequests?: emptyList()){item->
                                                ReturnRequestItemView(item,navController,pharmacyId)
                                            }

                                        }
                                        LaunchedEffect(listState) {
                                            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull() }
                                                .collect { lastVisibleItem ->
                                                    lastVisibleItem?.let {
                                                        if (lastVisibleItem.index == (returnRequests?.size?:0) - 1 && !isLoading) {

                                                            viewModel.getReturnRequests(pharmacyId, page =nextPage)

                                                        }
                                                    }
                                                }
                                        }
                                    }

                                }
                            }
                        }
                    } ?: run {
                        Text("Pharmacy not found", modifier = Modifier.align(Alignment.Center))
                    }
                    createReturnRequestsResponse?.let { response ->
                        Toast.makeText(navController.context, response.body()?.returnRequestStatus, Toast.LENGTH_SHORT).show()
                    }


                }
            }
        }
    )
}

@Composable
fun WholesalerItem(wholesaler: PharmacyLink) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Address: ${wholesaler.address}")
            Text(text = "City: ${wholesaler.city}")
            Text(text = "State: ${wholesaler.state}")
            Text(text = "ZIP Code: ${wholesaler.zipCode}")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReturnRequestItemView(item: ReturnRequestItem,navController: NavController,pharmacyId: String) {
    val formattedDate = formatTimestamp(item.returnRequest.createdAt)
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("return_detail/${item.returnRequest.id}/${pharmacyId}")
        }
        .padding(8.dp)) {
        Text(text = "ID: ${item.returnRequest.id}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Created At: $formattedDate", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Pharmacy: ${item.returnRequest.pharmacy.doingBusinessAs}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Status: ${item.returnRequest.returnRequestStatusLabel}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Service Type: ${item.returnRequest.serviceType}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Number of Items: ${item.numberOfItems}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(4.dp))
    }
}

