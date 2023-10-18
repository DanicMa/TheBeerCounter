plugins {
    id("com.android.application")
    id("kotlin-kapt")
    kotlin("android")
}

android {
    namespace = "cz.damat.thebeercounter"
    compileSdk = BuildValues.compileSdk

    defaultConfig {
        applicationId = "cz.damat.thebeercounter"
        minSdk = BuildValues.minSdk
        targetSdk = BuildValues.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildValues.kotlinCompilerExtensionVersion
    }
    packagingOptions {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(project(":commonUI"))
    implementation(project(":commonLib"))
    implementation(project(":componentCounter"))
    implementation(project(":featureCounter"))

    implementation(Dependencies.core_ktx)
    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_runtime_compose)
    implementation(Dependencies.activity_compose)

    // COMPOSE
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_tooling_preview)
    implementation(Dependencies.compose_material)
    implementation(Dependencies.compose_ui_tooling)
    implementation(Dependencies.compose_ui_test_manifest)
    implementation(Dependencies.compose_navigation)

    implementation(Dependencies.collections_immutable)

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

    coreLibraryDesugaring(Dependencies.desugar)
}