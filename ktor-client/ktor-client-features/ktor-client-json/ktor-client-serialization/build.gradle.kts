val serialization_version: String by project.extra

kotlin.sourceSets {
    commonMain {
        dependencies {
            api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
            api(project(":ktor-client:ktor-client-features:ktor-client-json"))
        }
    }

    jvmMain {
        dependencies {
            api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization_version")
        }
    }

    jsMain {
        dependencies {
            api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialization_version")
        }
    }

    posixMain {
        dependencies {
            api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization_version")
        }
    }
}
