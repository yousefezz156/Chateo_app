package com.example.chateo_app.supabase.networkClient

import com.example.chateo_app.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient

object SupabaseClient{
    val client = createSupabaseClient(
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY,
        supabaseUrl = BuildConfig.SUPABASE_URL
    ){
        install(Auth)
    }
}