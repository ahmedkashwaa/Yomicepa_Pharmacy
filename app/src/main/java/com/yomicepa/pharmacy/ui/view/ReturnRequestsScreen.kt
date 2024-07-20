package com.yomicepa.pharmacy.ui.view

// ReturnRequestsScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yomicepa.pharmacy.ui.viewmodel.ReturnViewModel

@Composable
fun ReturnRequestsScreen(viewModel: ReturnViewModel, pharmacyId: String,returnRequestId:String, onNavigateToCreateRequest: () -> Unit, onNavigateToItems: (String) -> Unit) {
    val returnRequests by viewModel.returnRequests.collectAsState()
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.getReturnRequests(pharmacyId,returnRequestId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onNavigateToCreateRequest,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Return Request")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            errorMessage?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            } ?: run {
                returnRequests.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                            }
                    ) {
                        Column {
                            Text(it?.body()?.id.toString()?:"")
                            Text(it?.body()?.createdAt?:"")
                            Text(it?.body()?.returnRequestStatusLabel?:"")
                            Text(it?.body()?.serviceType?:"")

                        }
                    }
                }
            }
        }
    }
}
