plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "cz.damat.thebeercounter"
    compileSdk = 33

    defaultConfig {
        applicationId = "cz.damat.thebeercounter"
        minSdk = 23
        targetSdk = 33
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
    packagingOptions {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.1")

    // COMPOSE
    val composeVersion = "1.1.1"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    //KOIN
    val koinVersion = "3.4.0"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-androidx-navigation:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")

//    def koin_version = "3.1.2"
//    api "io.insert-koin:koin-core:$koin_version"
//    api "io.insert-koin:koin-android:$koin_version"
//    api "io.insert-koin:koin-androidx-compose:$koin_version"

    // TESTS
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}