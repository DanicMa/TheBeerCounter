import org.gradle.api.JavaVersion

/**
 * Created by MD on 18.10.23.
 */
object BuildValues {
    const val minSdk = 23
    const val targetSdk = 33
    const val compileSdk = 33

    val javaVersion : JavaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
    const val kotlinCompilerExtensionVersion = "1.3.2"
}