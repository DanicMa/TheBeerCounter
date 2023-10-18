import org.gradle.api.JavaVersion

/**
 * Created by MD on 18.10.23.
 */
object BuildValues {
    val minSdk = 23
    val targetSdk = 33
    val compileSdk = 33

    val javaVersion : JavaVersion = JavaVersion.VERSION_1_8
    val jvmTarget = "1.8"
    val kotlinCompilerExtensionVersion = "1.2.0"
}