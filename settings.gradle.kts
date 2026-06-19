pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Weather"
include(":app")

include(":core:domain")
include(":core:data")
include(":core:database")
include(":core:location")
include(":core:presentation")
include(":core:design-system")

include(":feature:home:domain")
include(":feature:home:data")
include(":feature:home:presentation")

include(":feature:search:domain")
include(":feature:search:data")
include(":feature:search:presentation")