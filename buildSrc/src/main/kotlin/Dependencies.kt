object ProjectDependencies {
    const val KOTLIN_VERSION = "1.7.10"
    const val MOCKK_VERSION = "1.12.5"
    const val DEPENDENCY_UPDATES_CHECKER_PLUGIN_VERSION = "0.42.0"
    private const val ANDROID_GRADLE_PLUGIN_VERSION = "7.2.1"
    private const val COROUTINES_VERSION = "1.6.4"
    private const val KOTLIN_DATE_TIME_VERSION = "0.4.0"
    private const val KOTLIN_TEST_VERSION = "1.6.0"
    private const val DETEKT_FORMATTING_VERSION = "1.21.0"

    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    const val ANDROID_GRADLE_PLUGIN =
        "com.android.tools.build:gradle:$ANDROID_GRADLE_PLUGIN_VERSION"

    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"
    const val MOCKK = "io.mockk:mockk:$MOCKK_VERSION"
    const val KOTLIN_DATE_TIME = "org.jetbrains.kotlinx:kotlinx-datetime:$KOTLIN_DATE_TIME_VERSION"
    const val KOTLIN_TEST = "org.jetbrains.kotlin:kotlin-test-junit:$KOTLIN_TEST_VERSION"
    const val DETEKT_FORMATTING =
        "io.gitlab.arturbosch.detekt:detekt-formatting:$DETEKT_FORMATTING_VERSION"
}

object AndroidDependencies {
    private const val ANDROID_CORE_KTX_VERSION = "1.9.0-alpha05"
    private const val ANDROID_COLLECTIONS_VERSION = "1.3.0-alpha02"
    private const val ANDROID_TEST_VERSION = "1.4.1-alpha07"
    private const val TIMBER_VERSION = "5.0.1"
    private const val CORE_LIBRARY_DESUGARING_VERSION = "1.1.5"

    const val ANDROID_CORE_KTX = "androidx.core:core-ktx:$ANDROID_CORE_KTX_VERSION"
    const val ANDROID_COLLECTIONS =
        "androidx.collection:collection:$ANDROID_COLLECTIONS_VERSION"
    const val ANDROID_TEST_RUNNER = "androidx.test:runner:$ANDROID_TEST_VERSION"
    const val ANDROID_TEST_RULES = "androidx.test:rules:$ANDROID_TEST_VERSION"
    const val ANDROID_MOCKK = "io.mockk:mockk-android:${ProjectDependencies.MOCKK_VERSION}"
    const val TIMBER = "com.jakewharton.timber:timber:$TIMBER_VERSION"
    const val CORE_LIBRARY_DESUGARING =
        "com.android.tools:desugar_jdk_libs:$CORE_LIBRARY_DESUGARING_VERSION"
}

object DataDependencies {
    private const val ROOM_VERSION = "2.5.0-alpha02"
    private const val RETROFIT_VERSION = "2.9.0"
    private const val KOTLINX_SERIALIZATION_RETROFIT_CONVERTER_VERSION = "0.8.0"
    private const val KOTLINX_SERIALIZATION_VERSION = "1.4.0-RC"
    private const val MOCK_WEB_SERVER_VERSION = "5.0.0-alpha.10"
    private const val WORK_MANAGER_VERSION = "2.8.0-alpha02"


    const val ROOM = "androidx.room:room-ktx:$ROOM_VERSION"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    const val ROOM_TESTING = "androidx.room:room-testing:$ROOM_VERSION"

    const val KOTLINX_SERIALIZATION =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:$KOTLINX_SERIALIZATION_VERSION"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val RETROFIT_KOTLINX_SERIALIZATION_CONVERTER =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$KOTLINX_SERIALIZATION_RETROFIT_CONVERTER_VERSION"

    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:$MOCK_WEB_SERVER_VERSION"

    const val WORK_MANAGER_KTX = "androidx.work:work-runtime-ktx:$WORK_MANAGER_VERSION"

}

object PresentationDependencies {
    private const val KOIN_VERSION = "3.2.0"
    private const val KOTPREF_VERSION = "2.13.2"
    private const val EPOXY_VERSION = "5.0.0-beta05"
    private const val LIFECYCLE_VERSION = "2.6.0-alpha01"
    private const val NAVIGATION_VERSION = "2.5.1"
    private const val ACTIVITY_VERSION = "1.6.0-alpha05"
    private const val FRAGMENT_VERSION = "1.6.0-alpha01"
    private const val APP_COMPAT_VERSION = "1.6.0-alpha05"
    private const val BROWSER_VERSION = "1.4.0"
    private const val MATERIAL_COMPONENTS_VERSION = "1.7.0-alpha03"
    private const val CONSTRAINT_LAYOUT_VERSION = "2.2.0-alpha03"
    private const val FLEX_BOX_LAYOUT_VERSION = "3.0.0"
    private const val SWIPE_REFRESH_LAYOUT_VERSION = "1.2.0-alpha01"
    private const val ASYNC_LAYOUT_INFLATER_VERSION = "1.0.0"
    private const val COIL_VERSION = "2.1.0"
    private const val SPLITTIES_VERSION = "3.0.0"
    private const val VIEW_BINDING_PROPERTY_DELEGATE_VERSION = "1.5.6"
    private const val LEAK_CANARY_VERSION = "2.9.1"

    const val KOIN_ANDROID = "io.insert-koin:koin-android:$KOIN_VERSION"
    const val KOIN_WORK_MANAGER = "io.insert-koin:koin-androidx-workmanager:$KOIN_VERSION"

    const val KOTPREF = "com.chibatching.kotpref:kotpref:$KOTPREF_VERSION"
    const val KOTPREF_INITIALIZER = "com.chibatching.kotpref:initializer:$KOTPREF_VERSION"
    const val KOTPREF_LIVEDATA = "com.chibatching.kotpref:livedata-support:$KOTPREF_VERSION"
    const val KOTPREF_ENUM = "com.chibatching.kotpref:enum-support:$KOTPREF_VERSION"

    const val EPOXY = "com.airbnb.android:epoxy:$EPOXY_VERSION"
    const val EPOXY_PROCESSOR = "com.airbnb.android:epoxy-processor:$EPOXY_VERSION"
    const val EPOXY_DATABINDING = "com.airbnb.android:epoxy-databinding:$EPOXY_VERSION"

    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"
    const val LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-common-java8:$LIFECYCLE_VERSION"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"

    const val NAVIGATION_SAFE_ARGS_PLUGIN =
        "androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VERSION"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"

    const val ACTIVITY = "androidx.activity:activity-ktx:$ACTIVITY_VERSION"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:$FRAGMENT_VERSION"
    const val APP_COMPAT = "androidx.appcompat:appcompat:$APP_COMPAT_VERSION"
    const val BROWSER = "androidx.browser:browser:$BROWSER_VERSION"

    const val MATERIAL_COMPONENTS =
        "com.google.android.material:material:$MATERIAL_COMPONENTS_VERSION"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
    const val FLEX_BOX_LAYOUT = "com.google.android.flexbox:flexbox:$FLEX_BOX_LAYOUT_VERSION"
    const val SWIPE_REFRESH_LAYOUT =
        "androidx.swiperefreshlayout:swiperefreshlayout:$SWIPE_REFRESH_LAYOUT_VERSION"
    const val ASYNC_LAYOUT_INFLATER =
        "androidx.asynclayoutinflater:asynclayoutinflater:$ASYNC_LAYOUT_INFLATER_VERSION"

    const val COIL = "io.coil-kt:coil:$COIL_VERSION"
    const val SPLITTIES =
        "com.louiscad.splitties:splitties-fun-pack-android-material-components:$SPLITTIES_VERSION"

    const val VIEW_BINDING_PROPERTY_DELEGATE =
        "com.github.kirich1409:viewbindingpropertydelegate:$VIEW_BINDING_PROPERTY_DELEGATE_VERSION"

    const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:$LEAK_CANARY_VERSION"

}
