plugins {
    // plugin that checks for dependency updates and suggests new versions (since automated version checks don't work with buildSrc defined dependencies)
    id("com.github.ben-manes.versions") version ("0.49.0")
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // for the ben-manes:gradle-versions plugin
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath(kotlin("gradle-plugin", version = "1.7.0"))
        classpath("com.github.ben-manes:gradle-versions-plugin:0.49.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}