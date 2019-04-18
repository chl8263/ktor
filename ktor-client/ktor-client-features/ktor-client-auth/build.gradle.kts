description = "Ktor client Basic Auth support"

kotlin.sourceSets {
    commonMain {
        dependencies {
            api(project(":ktor-client:ktor-client-core"))
        }
    }
    jvmTest {
        dependencies {
            api(project(":ktor-features:ktor-auth"))
            api(project(":ktor-client:ktor-client-cio"))
            api(project(":ktor-client:ktor-client-tests"))
            api(project(":ktor-server:ktor-server-cio"))
        }
    }
}
