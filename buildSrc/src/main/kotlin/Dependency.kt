@file:Suppress("unused")

object ApplicationId {
    const val packageName = "com.spoton.kdsarchitecturesample"
}

object Versions {
    const val compileSdk = 31
    const val minSdk = 22
    const val targetSdk = 31
    const val kotlin = "1.6.21"
    const val gradle = "7.1.3"
    const val junitPlugin = "1.8.2.0"
    const val lifecycle = "2.5.0"
    const val lifecycleExtensions = "2.2.0"
    const val room = "2.4.2"
    const val materialComponents = "1.6.1"
    const val constraintLayout = "2.1.4"
    const val appcompat = "1.4.2"
    const val junit5 = "5.8.2"
    const val coroutines = "1.6.1"
    const val navigation = "2.5.0"
    const val fragment = "1.5.0"
    const val activity = "1.5.0"
    const val timber = "5.0.1"
    const val archCoreTesting = "2.1.0"
    const val mockitoKotlin = "4.0.0"
    const val runner = "1.4.0"
    const val orchestrator = "1.4.1"
    const val androidJunit = "1.1.3"
    const val hilt = "2.41"
}

object Classpaths {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val navigation =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val junit = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.junitPlugin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}