plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "cz.damat.thebeercounter.feature.counter"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildValues.kotlinCompilerExtensionVersion
    }
}

dependencies {

    implementation(project(":commonUI"))
    implementation(project(":commonLib")) // unfortunately, this is needed for having access to DB entities since they cannot be in the componentCounter module (and creating a 1to1 mapping class is not worth it ATM)
    implementation(project(":componentCounter"))

    implementation(Dependencies.core_ktx)
    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_runtime_compose)
    implementation(Dependencies.activity_compose)

    coreLibraryDesugaring(Dependencies.desugar)

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

    // TESTS
    testImplementation(Dependencies.test_junit)
    androidTestImplementation(Dependencies.test_ext_junit)
    androidTestImplementation(Dependencies.test_espresso)
    implementation(Dependencies.compose_ui_test_junit)
}