description = "Ktor network utilities"

kotlin.sourceSets {
    commonMain {
        dependencies {
            api(project(":ktor-utils"))
        }
    }
}
