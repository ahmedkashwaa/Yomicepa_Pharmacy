package com.yomicepa.pharmacy.ui.view

// PharmacyScreen.kt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yomicepa.pharmacy.data.model.PharmacyItem
import com.yomicepa.pharmacy.ui.viewmodel.PharmaciesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmaciesScreen(navController: NavController, viewModel: PharmaciesViewModel = hiltViewModel()) {
    val pharmacies by viewModel.pharmacies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pharmacies") })
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
                    LazyColumn {
                        items(pharmacies) { pharmacy ->
                            PharmacyItem(pharmacy){
                                // navigate to the pharmacy full details
                                navController.navigate("pharmacy_detail/${pharmacy.pharmacyId}")
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PharmacyItem(pharmacy: PharmacyItem,onClick:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
            onClick()
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = pharmacy.doingBusinessAs, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Number of Returns: ${pharmacy.numberOfReturns}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Enabled: ${if (pharmacy.enabled) "Yes" else "No"}")
        }
    }
}
