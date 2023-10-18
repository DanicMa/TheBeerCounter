plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "cz.damat.thebeercounter.commonUI"
    compileSdk = 33

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = "1.2.0"
    }
}

dependencies {

    implementation(Dependencies.core_ktx)

    // COMPOSE
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_tooling_preview)
    implementation(Dependencies.compose_material)
    implementation(Dependencies.compose_ui_tooling)
    implementation(Dependencies.compose_ui_test_manifest)
    implementation(Dependencies.compose_navigation)


    // TESTS
    testImplementation(Dependencies.test_junit)
    androidTestImplementation(Dependencies.test_ext_junit)
    androidTestImplementation(Dependencies.test_espresso)
    implementation(Dependencies.compose_ui_test_junit)
}