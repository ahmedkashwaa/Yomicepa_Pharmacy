package com.yomicepa.pharmacy.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yomicepa.pharmacy.data.model.CreateReturnRequest
import com.yomicepa.pharmacy.interceptor.AuthInterceptor
import com.yomicepa.pharmacy.ui.theme.PharmacyTheme
import com.yomicepa.pharmacy.ui.view.AddItemScreen
import com.yomicepa.pharmacy.ui.view.CreateReturnRequestScreen
import com.yomicepa.pharmacy.ui.view.ItemsScreen
import com.yomicepa.pharmacy.ui.view.LoginScreen
import com.yomicepa.pharmacy.ui.view.PharmaciesScreen
import com.yomicepa.pharmacy.ui.view.PharmacyDetailScreen
import com.yomicepa.pharmacy.ui.view.ReturnRequestsScreen
import com.yomicepa.pharmacy.ui.viewmodel.AuthViewModel
import com.yomicepa.pharmacy.ui.viewmodel.PharmacyDetailViewModel
import com.yomicepa.pharmacy.ui.viewmodel.ReturnViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var authInterceptor: AuthInterceptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           PharmacyAppScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PharmacyAppScreen() {
    PharmacyTheme {
        val navController = rememberNavController()
        val authViewModel: AuthViewModel = hiltViewModel()

        NavHost(navController, startDestination = "login") {
            composable("login") {
                LoginScreen(viewModel = authViewModel) {
                    navController.navigate("pharmacies")
                }
            }
            composable("pharmacies"){
                PharmaciesScreen(navController)
            }
            composable("pharmacy_detail/{pharmacyId}") { backStackEntry ->
                val pharmacyId = backStackEntry.arguments?.getString("pharmacyId")
                if (pharmacyId != null) {
                    // Implement PharmacyDetailScreen
                     PharmacyDetailScreen(pharmacyId,navController)
                }
            }
            composable("return_detail/{returnRequestId}/{pharmacyId}"){backStackEntry->
                val returnViewModel: ReturnViewModel = hiltViewModel()

                val returnRequestId = backStackEntry.arguments?.getString("returnRequestId")
                val pharmacyId = backStackEntry.arguments?.getString("pharmacyId")

                ReturnRequestsScreen(
                    viewModel = returnViewModel,
                    pharmacyId = pharmacyId?:"",
                    returnRequestId = returnRequestId?:"",
                    onNavigateToCreateRequest = { /*TODO*/ }) {

                }
            }
            composable("create_return_request/{pharmacyId}") { backStackEntry ->
                val pharmacyId = backStackEntry.arguments?.getString("pharmacyId")
                if (pharmacyId != null) {
                        CreateReturnRequestScreen(pharmacyId = pharmacyId, navController = navController, onSubmit = {x,y->})
                }
            }
            composable("addItem/{returnRequestId}/{pharmacyId}"){backStackEntry->

                val returnRequestId = backStackEntry.arguments?.getString("returnRequestId")
                val pharmacyId = backStackEntry.arguments?.getString("pharmacyId")

                AddItemScreen(returnRequestId = returnRequestId?:"", pharmacyId = pharmacyId?:"",navController=navController, navToItemsScreen = {
                    navController.navigate("items/$returnRequestId/$pharmacyId")
                })
            }



            composable("items/{returnRequestId}/{pharmacyId}") { backStackEntry ->
                val returnRequestId = backStackEntry.arguments?.getString("returnRequestId")
                val pharmacyId = backStackEntry.arguments?.getString("pharmacyId")
                if (returnRequestId != null) {
                    // Implement ItemsScreen
                    ItemsScreen(pharmacyId = pharmacyId?:"", returnRequestId =returnRequestId )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PharmacyTheme {
        Greeting("Android")
    }
}