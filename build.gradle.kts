import io.gitlab.arturbosch.detekt.Detekt

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath(kotlin("gradle-plugin", version = "1.7.20"))
        classpath("com.github.ben-manes:gradle-versions-plugin:0.49.0")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    // plugin that checks for dependency updates and suggests new versions (since automated version checks don't work with buildSrc defined dependencies)
    id("com.github.ben-manes.versions") version ("0.49.0")
    // detekt plugin for code analysis
    id("io.gitlab.arturbosch.detekt") version("1.23.1")
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

//subprojects {
    detekt {
        toolVersion = "1.23.1"
        config.setFrom(file("config/detekt/detekt.yml"))
    }
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType(Detekt::class.java).configureEach {
    jvmTarget = "11"
}

tasks.withType(Detekt::class.java).configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
    }
}