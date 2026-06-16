package com.djekgrif.weather.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Enables Compose on whichever Android extension the module has and wires the standard
 * Compose BOM-aligned dependencies. Assumes an Android plugin is already applied.
 */
internal fun Project.configureAndroidCompose() {
    pluginManager.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)

    extensions.findByType(ApplicationExtension::class.java)?.apply { buildFeatures.compose = true }
    extensions.findByType(LibraryExtension::class.java)?.apply { buildFeatures.compose = true }

    val bom = libs.findLibrary("androidx-compose-bom").get()
    dependencies {
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))
        add("implementation", libs.findLibrary("androidx-compose-ui").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        add("implementation", libs.findLibrary("androidx-compose-material3").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
        add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
    }
}