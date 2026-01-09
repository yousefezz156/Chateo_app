import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "1.9.0"


}


android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.chateo_app"
    compileSdk = 35
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    val supabaseUrl = localProperties.getProperty("SUPABASE_URL")
    val supabaseAnonKey = localProperties.getProperty("SUPABASE_ANON_KEY")

    defaultConfig {
        applicationId = "com.example.chateo_app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"$supabaseAnonKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.firebase.auth)
    val nav_version = "2.8.4"

    val lifecycle_version = "2.8.7"

    implementation("androidx.navigation:navigation-compose:$nav_version")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation( "org.jetbrains.kotlin:kotlin-stdlib:1.9.0" ) // Use the latest version

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //FireBase
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation("com.google.firebase:firebase-auth")

//    //Supabase
//    implementation(platform("io.github.jan-tennert.supabase:bom:3.1.1")) // BOM for version management
//    implementation("io.github.jan-tennert.supabase:postgrest-kt")
//    implementation("io.github.jan-tennert.supabase:auth-kt") // GoTrue module for authentication
//    implementation("io.github.jan-tennert.supabase:realtime-kt") // Realtime module (optional)

    //ktor
//    implementation("io.ktor:ktor-client-android:3.1.0")
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")

    //Exo
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-ui:1.2.1")

    // Ktor core & engines
    implementation("io.ktor:ktor-client-core:2.3.6")
    implementation("io.ktor:ktor-client-okhttp:2.3.6") // OkHttp engine for Android

// Plugins
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
    implementation("io.ktor:ktor-client-logging:2.3.6")
    implementation("io.ktor:ktor-client-auth:2.3.6")
    implementation("io.ktor:ktor-client-encoding:2.3.6")
    implementation("io.ktor:ktor-client-resources:2.3.6")
    implementation("io.ktor:ktor-client-websockets:2.3.6")

// JSON serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")

    // country code
    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.27")



}