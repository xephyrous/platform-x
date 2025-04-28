import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.20"
}

val ktorVersion = "3.1.2"

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
    
    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // KotlinX
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

            // AndroidX
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")

            // KTor
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-js:$ktorVersion")
            implementation("io.ktor:ktor-client-json:$ktorVersion")
            implementation("io.ktor:ktor-client-serialization:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("io.ktor:ktor-client-js:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "platformx.composeapp.generated.resources"
    generateResClass = auto
}

tasks.register("validateViewClasses") {
    group = "validation"
    description = "Validates all class signatures in the 'views' directory."

    doLast {
        val dir = File("src/wasmJsMain/kotlin/org/xephyrous/views")
        if (!dir.exists()) {
            throw GradleException("Views directory not found, validation failed!")
        }

        val viewFiles = dir.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .toList()

        viewFiles.forEach fileLoop@{ file ->
            file.readLines().forEach lineLoop@{ line ->
                if (!line.contains("class")) {
                    return@lineLoop
                }

                val className = line.substringAfter("class ").substringBefore("{").trim()
            }
        }
    }
}

tasks.named("build") {
    dependsOn("validateViewClasses")
}