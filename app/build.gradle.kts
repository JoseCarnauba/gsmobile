import org.gradle.api.JavaVersion.VERSION_17

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    // Definição do namespace para o projeto
    namespace = "br.com.fiap.ecopower"

    compileSdk = 34

    defaultConfig {
        // Definição do ID do aplicativo
        applicationId = "br.com.fiap.ecopower"
        minSdk = 25
        targetSdk = 34
        versionCode = 34
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // Ativando ViewBinding
    viewBinding {
        enable = true
    }

    // Configurações do Java
    compileOptions {
        sourceCompatibility = VERSION_17
        targetCompatibility = VERSION_17
    }

    // Configurações do Kotlin
    kotlinOptions {
        jvmTarget = "17"
    }

    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
}

dependencies {
    // Firebase (usando Bill of Materials - BOM)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.auth.ktx)

    // Dependências AndroidX e Material Design
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.support.annotations)
    implementation(libs.firebase.storage.ktx)

    // Dependências para testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
