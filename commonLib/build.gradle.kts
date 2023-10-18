plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "cz.damat.thebeercounter.commonLib"
    compileSdk = BuildValues.compileSdk

    defaultConfig {
        minSdk = BuildValues.minSdk

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
        // Flag to enable support for the new language APIs (need for e.g. LocalDate manipulation)
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = BuildValues.javaVersion
        targetCompatibility = BuildValues.javaVersion
    }
    kotlinOptions {
        jvmTarget = BuildValues.jvmTarget
    }
}

dependencies {

    implementation(Dependencies.core_ktx)
    coreLibraryDesugaring(Dependencies.desugar)

    //KOIN
    implementation(Dependencies.koin_core)
    implementation(Dependencies.koin_navigation)
    implementation(Dependencies.koin_compose)
    testImplementation(Dependencies.koin_junit)

    // ROOM
    implementation(Dependencies.room_runtime)
    annotationProcessor(Dependencies.room_compiler)
    kapt(Dependencies.room_compiler)
    implementation(Dependencies.room_room)

    // TESTS
    testImplementation(Dependencies.test_junit)
    androidTestImplementation(Dependencies.test_ext_junit)
    androidTestImplementation(Dependencies.test_espresso)
    implementation(Dependencies.compose_ui_test_junit)
}