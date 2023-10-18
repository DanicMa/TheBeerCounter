/**
 * Created by MD on 18.10.23.
 */
object Versions {
    val composeVersion = "1.1.1"
    val koinVersion = "3.4.0"
    val roomVersion = "2.5.1"
}

object Dependencies {
    val core_ktx = "androidx.core:core-ktx:1.10.0"
    val activity_compose = "androidx.activity:activity-compose:1.7.1"

    val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    /**
     * LIFECYCLE AWARE FLOW COLLECTION (for lifecycle aware viewstate collection in compose)
     */
    val lifecycle_runtime_compose = "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"

    // COMPOSE
    val compose_ui = "androidx.compose.ui:ui:${Versions.composeVersion}"
    // val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
    val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:1.4.2"
    val compose_material = "androidx.compose.material:material:${Versions.composeVersion}"
    val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    val compose_ui_test_manifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"
    val compose_navigation = "androidx.navigation:navigation-compose:2.5.3"
    val compose_ui_test_junit = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"

    /**
     * IMMUTABLE COLLECTIONS FOR KOTLIN (for composable methods stability)
     */
    val collections_immutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5"

    // KOIN
    val koin_core = "io.insert-koin:koin-core:${Versions.koinVersion}"
    val koin_navigation = "io.insert-koin:koin-androidx-navigation:${Versions.koinVersion}"
    val koin_compose = "io.insert-koin:koin-androidx-compose:${Versions.koinVersion}"
    val koin_junit = "io.insert-koin:koin-test-junit4:${Versions.koinVersion}"

    // ROOM
    val room_runtime = "androidx.room:room-runtime:${Versions.roomVersion}"
    val room_compiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    val room_room = "androidx.room:room-ktx:${Versions.roomVersion}"

    /**
     * DESUGARING (e.g. for LocalDate manipulation on API < 26)
     */
    val desugar = "com.android.tools:desugar_jdk_libs:1.2.2"

    // TESTS
    val test_junit = "junit:junit:4.13.2"
    val test_ext_junit = "androidx.test.ext:junit:1.1.5"
    val test_espresso = "androidx.test.espresso:espresso-core:3.5.1"
}