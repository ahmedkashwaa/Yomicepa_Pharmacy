package com.yomicepa.pharmacy.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yomicepa.pharmacy.ui.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(pharmacyId: String,returnRequestId: String){
    val viewModel : ItemViewModel = hiltViewModel()
    val items by viewModel.items.collectAsState()
    LaunchedEffect(Unit){
        viewModel.listItems(pharmacyId = pharmacyId,returnRequestId=returnRequestId)
    }
    if (items?.isSuccessful==true){
        items?.body()?.forEach {
            val description = mutableStateOf(it.description)
            Scaffold(topBar = {
                TopAppBar(title = { Text("Items") })
            }, content = {padding->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(horizontal = 16.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row {
                            Text(text = "ID : ${it.id}")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.Delete, contentDescription = "Delete", modifier =
                            Modifier
                                .clickable {
                                    viewModel.deleteItem(pharmacyId = pharmacyId, returnRequestId = returnRequestId, item = it)
                                }
                            )
                        }

                        Text(text ="NDC : ${it.ndc}" )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text ="LOT Number : ${it.lotNumber}")
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(text = "You Can Update Description Below", fontWeight = FontWeight.ExtraBold)
                        TextField(value = description.value?:"", onValueChange = {newValue->
                            description.value = newValue
                            viewModel.updateItem(pharmacyId = pharmacyId, returnRequestId = returnRequestId, item = it.copy(description=newValue))

                        })
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(text = "Expiration Date : ${it.expirationDate}" )
                    }
                }
            })

        }
    }

}