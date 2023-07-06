package com.example.cmsjetpack.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.cmsjetpack.screens.splash.SplashScreen
import com.example.cmsjetpack.screens.user.details.UserDetailsScreen
import com.example.cmsjetpack.screens.user.details.UserDetailsViewModel
import com.example.cmsjetpack.screens.user.list.UserListScreen
import com.example.cmsjetpack.screens.user.list.UserListViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object User : Screen("user")
}

sealed class SplashScreen(val route: String) {
    object Splash : SplashScreen("splash/index")
}

sealed class UserScreen(val route: String) {
    object UserList : UserScreen("users")

    object UserDetails : UserScreen("users/{userId}") {
        const val USER_ID = "userId"
    }
}

@Composable
fun CMSNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        addSplashScreens(navController = navController)
        addUsersScreens(
            navController = navController
        )
    }
}

private fun NavGraphBuilder.addSplashScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.Splash.route,
        startDestination = SplashScreen.Splash.route
    ) {
        composable(SplashScreen.Splash.route) {
            SplashScreen(gotoHomeIndex = {
                navController.navigate(Screen.User.route) {
                    popUpTo(SplashScreen.Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}

private fun NavGraphBuilder.addUsersScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.User.route,
        startDestination = UserScreen.UserList.route
    ) {
        composable(UserScreen.UserList.route) {
            val viewModel: UserListViewModel = hiltViewModel()

            UserListScreen(
                viewModel = viewModel,

                gotoUserDetails = { userId ->
                    navController.navigate(
                        UserScreen.UserDetails.route.replaceFirst(
                            "{${UserScreen.UserDetails.USER_ID}}",
                            "$userId"
                        )
                    )
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            UserScreen.UserDetails.route,
            arguments = listOf(
                navArgument(UserScreen.UserDetails.USER_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: UserDetailsViewModel = hiltViewModel()
            UserDetailsScreen(
                viewModel = viewModel,
                userId = it.arguments?.getInt(UserScreen.UserDetails.USER_ID) ?: 0,
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
