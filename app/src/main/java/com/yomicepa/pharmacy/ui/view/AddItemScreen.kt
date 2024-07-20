package com.yomicepa.pharmacy.ui.view

// AddItemScreen.kt

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yomicepa.pharmacy.data.model.Item
import com.yomicepa.pharmacy.ui.viewmodel.ItemViewModel
import kotlinx.coroutines.launch

@Composable
fun AddItemScreen(pharmacyId: String,returnRequestId: String,navToItemsScreen: () -> Unit,navController: NavController) {
    val viewModel: ItemViewModel = hiltViewModel()
    val addItemResponse = viewModel.addItem.collectAsState()
    val scope = rememberCoroutineScope()
    var ndc by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var manufacturer by remember { mutableStateOf("") }
    var fullQuantity by remember { mutableStateOf("") }
    var partialQuantity by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var lotNumber by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = ndc,
            onValueChange = { ndc = it },
            label = { Text("NDC") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = manufacturer,
            onValueChange = { manufacturer = it },
            label = { Text("Manufacturer") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = fullQuantity,
            onValueChange = { fullQuantity = it },
            label = { Text("Full Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = partialQuantity,
            onValueChange = { partialQuantity = it },
            label = { Text("Partial Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = expirationDate,
            onValueChange = { expirationDate = it },
            label = { Text("Expiration Date") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lotNumber,
            onValueChange = { lotNumber = it },
            label = { Text("Lot Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val item = Item(
                ndc = ndc,
                description = description,
                manufacturer = manufacturer,
                fullQuantity = fullQuantity.toIntOrNull() ?: 0,
                partialQuantity = partialQuantity.toIntOrNull() ?: 0,
                expirationDate = expirationDate,
                lotNumber = lotNumber,
            )
            scope.launch {
                viewModel.addItem(returnRequestId = returnRequestId,pharmacyId =pharmacyId , item = item)
                ndc = ""
                description = ""
                manufacturer = ""
                fullQuantity = ""
                partialQuantity = ""
                expirationDate = ""
                lotNumber = ""
                Toast.makeText(navController.context, "Item added successfully", Toast.LENGTH_SHORT).show()


            }
        }) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navToItemsScreen() }) {
            Text("Go to Items")
        }
    }
}
