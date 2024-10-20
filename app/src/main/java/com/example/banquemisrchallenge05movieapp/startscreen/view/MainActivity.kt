package com.example.banquemisrchallenge05movieapp.startscreen.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.mainhome.view.MainHomeScreen
import com.example.banquemisrchallenge05movieapp.startscreen.components.SplashLottie
import com.example.banquemisrchallenge05movieapp.utils.data_layer.RepositoryImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.LocalDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.RemoteDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.navigation.Screen
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.InternetChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var internetChecker: InternetChecker
    private val repository by lazy {
        RepositoryImpl.getInstance(
            RemoteDataSourceImpl.getInstance(),
            LocalDataSourceImpl.getInstance(this)
        )
    }
    private val listScreenViewModelFactory by lazy {
        ListScreenViewModelFactory(repository, this)
    }
    private val detailScreenViewModelFactory by lazy {
        DetailScreenViewModelFactory(repository, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internetChecker = InternetChecker(this)
        internetChecker.startMonitoring()


        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                  NavHost(navController, startDestination = Screen.SplashScreen.route) {
                          composable(Screen.SplashScreen.route) { SplashLottie(navController) }

                          composable(Screen.ListScreen.route) {
                            MainHomeScreen(
                                listScreenViewModelFactory,
                                detailScreenViewModelFactory,

                                )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        internetChecker.stopMonitoring()
    }
}

