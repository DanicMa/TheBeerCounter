/**
 * Created by MD on 18.10.23.
 */
object Versions {
    const val composeVersion = "1.1.1"
    const val koinVersion = "3.4.0"
    const val roomVersion = "2.5.1"
}

object Dependencies {
    const val core_ktx = "androidx.core:core-ktx:1.10.0"
    const val activity_compose = "androidx.activity:activity-compose:1.7.1"

    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    /**
     * LIFECYCLE AWARE FLOW COLLECTION (for lifecycle aware viewstate collection in compose)
     */
    const val lifecycle_runtime_compose = "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"

    // COMPOSE
    const val compose_ui = "androidx.compose.ui:ui:${Versions.composeVersion}"
    // val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"

    const val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:1.4.2"
    const val compose_material = "androidx.compose.material:material:${Versions.composeVersion}"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    const val compose_ui_test_manifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"
    const val compose_navigation = "androidx.navigation:navigation-compose:2.5.3"
    const val compose_ui_test_junit = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"

    /**
     * IMMUTABLE COLLECTIONS FOR KOTLIN (for composable methods stability)
     */
    const val collections_immutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5"

    // KOIN
    const val koin_core = "io.insert-koin:koin-core:${Versions.koinVersion}"
    const val koin_navigation = "io.insert-koin:koin-androidx-navigation:${Versions.koinVersion}"
    const val koin_compose = "io.insert-koin:koin-androidx-compose:${Versions.koinVersion}"
    const val koin_junit = "io.insert-koin:koin-test-junit4:${Versions.koinVersion}"

    // ROOM
    const val room_runtime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val room_room = "androidx.room:room-ktx:${Versions.roomVersion}"

    /**
     * DESUGARING (e.g. for LocalDate manipulation on API < 26)
     */
    const val desugar = "com.android.tools:desugar_jdk_libs:1.2.2"

    // TESTS
    const val test_junit = "junit:junit:4.13.2"
    const val test_ext_junit = "androidx.test.ext:junit:1.1.5"
    const val test_espresso = "androidx.test.espresso:espresso-core:3.5.1"
}