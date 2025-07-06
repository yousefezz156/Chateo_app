package com.example.chateo_app.Navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chateo_app.chat.ScaffoldChatTextScreen
import com.example.chateo_app.chat.Scaffold_chat_screen
import com.example.chateo_app.chat.insiderChat.InsiderChatScaffold
import com.example.chateo_app.chat.insiderChat.InsiderChatViewModel
import com.example.chateo_app.chat.insiderChat.accesgallery.GalleryViewModel
import com.example.chateo_app.chat.insiderChat.accesgallery.Gallery_screen
import com.example.chateo_app.sign_up_screens.Otp_screen
import com.example.chateo_app.sign_up_screens.Profile_account

import com.example.chateo_app.sign_up_screens.Verfication_phone
import com.example.chateo_app.supabase.ViewModel.SB_authViewModel

object AppRoutes {

    const val VERIFICATION_PHONE="verification_phone"
    const val OTP="otp"
    const val PROFILE = "profile"
    const val MAiN_CONTACT = "contact"
    const val MAINCHAT = "mainchat"
    const val TEXTCHAT = "textchat"
    const val GALLERY ="gallery"
    const val FOLDER ="folder"
    const val AUDIO = "audio"
    const val EXTERNALCONTACT = "externalContact"
    const val LOCATION = "location"

}

@Composable
fun AppRoutes() {
    val navController = rememberNavController()
    val authViewModel: SB_authViewModel = viewModel()
    val galleryViewModel: GalleryViewModel = viewModel()
    val insiderChatViewModel: InsiderChatViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppRoutes.TEXTCHAT) {
        composable(route = AppRoutes.TEXTCHAT) { InsiderChatScaffold(galleryViewModel = galleryViewModel, insiderChatViewModel = insiderChatViewModel,navController = navController) }
        composable(route = AppRoutes.VERIFICATION_PHONE){ Verfication_phone(authViewModel = authViewModel,navController = navController) }
        composable(route = "${AppRoutes.OTP}/{total_phone}", arguments = listOf(
            navArgument("total_phone"){type= NavType.StringType}
        )) {backStackEntry ->
            val total_phone = backStackEntry.arguments?.getString("total_phone") ?: ""
            Otp_screen(total_phone =total_phone, authViewModel = authViewModel,navController=navController ) }
        composable(route = AppRoutes.PROFILE){ Profile_account(navController)}
        composable(route = AppRoutes.MAINCHAT){ Scaffold_chat_screen(navController)}
        composable(route = AppRoutes.GALLERY){ Gallery_screen(galleryViewModel = galleryViewModel,navController = navController)}

    }
}