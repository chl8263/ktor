description = "CIO backend for ktor http client"

val logback_version: String by project.extra

kotlin.sourceSets {
    commonMain {
        dependencies {
            api(project(":ktor-client:ktor-client-core"))
            api(project(":ktor-http:ktor-http-cio"))
        }
    }
    commonTest {
        dependencies {
            api(project(":ktor-client:ktor-client-tests"))
        }
    }
    jvmMain {
        dependencies {
            api(project(":ktor-network:ktor-network-tls"))
        }
    }
}
