plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "cz.damat.thebeercounter.commonUI"
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

    // TESTS
    testImplementation(Dependencies.test_junit)
    androidTestImplementation(Dependencies.test_ext_junit)
    androidTestImplementation(Dependencies.test_espresso)
    implementation(Dependencies.compose_ui_test_junit)
}