plugins {
    `kotlin-dsl`
}

group = "com.djekgrif.weather.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    // Kotlin sub-plugins applied by-id from convention plugins must be on the runtime classpath.
    implementation(libs.compose.compiler.gradlePlugin)
    implementation(libs.kotlin.serialization.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "weather.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "weather.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = "weather.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "weather.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("jvmDomain") {
            id = "weather.jvm.domain"
            implementationClass = "JvmDomainConventionPlugin"
        }
        register("koin") {
            id = "weather.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("ktor") {
            id = "weather.ktor"
            implementationClass = "KtorConventionPlugin"
        }
        register("serialization") {
            id = "weather.serialization"
            implementationClass = "SerializationConventionPlugin"
        }
    }
}