package com.yomicepa.pharmacy.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yomicepa.pharmacy.data.model.CreateReturnRequest
import com.yomicepa.pharmacy.data.model.Wholesaler
import com.yomicepa.pharmacy.ui.viewmodel.PharmacyDetailViewModel

enum class ServiceType(val label: String) {
    EXPRESS_SERVICE("Express Service"),
    FULL_SERVICE("Full Service")
}

@Composable
fun CreateReturnRequestScreen(
    pharmacyId: String,
    onSubmit: (ServiceType, Wholesaler) -> Unit,
    navController: NavController
) {
    val viewModel: PharmacyDetailViewModel = hiltViewModel()
    val wholesalers by viewModel.pharmacyWholesalers.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.listPharmacyWholesalers(pharmacyId?:"")
    }
    var selectedServiceType by remember { mutableStateOf<ServiceType?>(null) }
    var selectedWholesaler by remember { mutableStateOf<Wholesaler?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Create Return Request", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Service Type Dropdown
        DropdownMenu(
            label = "Service Type",
            options = ServiceType.values().toList(),
            selectedOption = selectedServiceType,
            onOptionSelected = { selectedServiceType = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Wholesaler Dropdown
        DropdownMenu(
            label = "Wholesaler",
            options = wholesalers?.body()?: emptyList(),
            selectedOption = selectedWholesaler,
            onOptionSelected = { selectedWholesaler = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedServiceType?.let { serviceType ->
                    selectedWholesaler?.let { wholesaler ->
                        onSubmit(serviceType, wholesaler)
                        viewModel.createReturnRequests(pharmacyId = pharmacyId, request = CreateReturnRequest(wholesalerId = wholesaler.id.toString(), serviceType = serviceType.name)){
                            navController.navigate("addItem/$it/$pharmacyId")

                        }
                    }
                }
            },
            enabled = selectedServiceType != null && selectedWholesaler != null
        ) {
            Text("Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenu(
    label: String,
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Box {
            OutlinedTextField(
                value = selectedOption?.toString() ?: "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                },

            )
            androidx.compose.material3.DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach {option ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        text = { Text(option.toString()) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = { expanded = true }) {
            Text("Select $label")
        }
    }
}
