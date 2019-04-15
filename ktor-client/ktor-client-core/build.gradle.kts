description = "Ktor http client"

kotlin.sourceSets {
    val commonMain by getting {
        dependencies {
            api(project(":ktor-http"))
            api(project(":ktor-http:ktor-http-cio"))
        }
    }

    val jvmMain by getting {
        dependencies {
            api(project(":ktor-network"))
        }
    }

    val commonTest by getting {
        dependencies {
            api(project(":ktor-client:ktor-client-tests"))
            api(project(":ktor-client:ktor-client-features:ktor-client-logging"))
        }
    }

    val jvmTest by getting {
        dependencies {
            api(project(":ktor-client:ktor-client-mock"))
            api(project(":ktor-client:ktor-client-tests"))
            api(project(":ktor-client:ktor-client-cio"))
            api(project(":ktor-client:ktor-client-okhttp"))
            api(project(":ktor-client:ktor-client-tests"))
            api(project(":ktor-features:ktor-websockets"))
        }
    }
}
