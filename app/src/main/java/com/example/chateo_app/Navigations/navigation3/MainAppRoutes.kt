package com.example.chateo_app.Navigations.navigation3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.chat.presentation.Scaffold_chat_screen
import com.example.chateo_app.contact.presentation.Scaffold_contact
import com.example.chateo_app.contact.presentation.mvi.ContactViewModel
import com.example.chateo_app.otp.presentation.MVI.OtpViewModel
import com.example.chateo_app.otp.presentation.Otp_screen
import com.example.chateo_app.personnalchat.insiderChat.accesgallery.GalleryViewModel
import com.example.chateo_app.profileaccount.presentation.MVI.ProfileViewModel
import com.example.chateo_app.profileaccount.presentation.Profile_account
import com.example.chateo_app.verfication.presentation.MVI.VerificationScreenViewModel
import com.example.chateo_app.verfication.presentation.Verfication_phone

@Composable
fun MainAppRoutes(verificationViewModel: VerificationScreenViewModel, modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(AppRoute.VerificationPhone)

    //    val authViewModel: SB_authViewModel = viewModel()
    val galleryViewModel: GalleryViewModel = viewModel()
//    val insiderChatViewModel: InsiderChatViewModel = viewModel()
    val contactViewModel: ContactViewModel = viewModel()
    val otpViewModel: OtpViewModel = viewModel()
    // val verificationViewModel: VerificationScreenViewModel = viewModel()
    val showBottomBar =  listOf(
        "Contacts",
        "Chats",
        "Settings"
    )

    val profileViewModel: ProfileViewModel=viewModel()
    NavDisplay(
        backStack=backStack,
        entryProvider = { key ->
                when (key) {
                    is AppRoute.VerificationPhone -> {
                        NavEntry(key) {
                            Verfication_phone(
                                verficationScreenViewModel = verificationViewModel,
                                onClick = {
                                    backStack.add(AppRoute.Otp(totalPhone = it.toString()))
                                }
                            )
                        }
                    }
                    is AppRoute.Otp -> {
                        NavEntry(key) {
                            Otp_screen(
                                onClick = {backStack.add(AppRoute.Profile) },
                                otpViewModel = otpViewModel,
                                total_phone = key.totalPhone
                            )
                        }
                    }
                    is AppRoute.Profile -> {
                        NavEntry(key) {
                            Profile_account(
                                profileViewModel = profileViewModel,
                                onClick = {
                                    backStack.add(AppRoute.MainChat)
                                }
                            )
                        }
                    }
                    is AppRoute.MainChat -> {
                        NavEntry(key) {
                            Scaffold_chat_screen(
                                onClick = {
                                    backStack.add(AppRoute.TextChat)
                                }
                            )
                        }
                    }
                    is AppRoute.MainContact -> {
                        NavEntry(key) {
                            Scaffold_contact(
                                viewModel = contactViewModel,
                                onClick = {
                                    backStack.add(AppRoute.ExternalContact)
                                }
                            )
                        }
                    }
                    is AppRoute.TextChat -> {
                        NavEntry(key) {
                            // InsiderChatScaffold(galleryViewModel = galleryViewModel)
                        }
                    }
                    is AppRoute.Gallery -> {
                        NavEntry(key) {
                            // Gallery_screen(galleryViewModel = galleryViewModel)
                        }
                    }
                    is AppRoute.Folder -> {
                        NavEntry(key) {
                            // Folder_screen()
                        }
                    }
                    is AppRoute.Audio -> {
                        NavEntry(key) {
                            // Audio_screen()
                        }
                    }
                    is AppRoute.ExternalContact -> {
                        NavEntry(key) {
                            // ExternalContact_screen()
                        }
                    }
                    is AppRoute.Location -> {
                        NavEntry(key) {
                            // Location_screen()
                        }
                    }
                    is AppRoute.Settings -> {
                        NavEntry(key) {
                            // Settings_screen()
                        }
                    }
                    else -> error("Unknown route: $key")
                }
            }
    )
}