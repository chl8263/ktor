import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

val ideaActive: Boolean by project.extra
val serialization_version: String by project.extra

plugins {
    id("kotlinx-serialization")
}

kotlin {
    targets {
        val current = mutableListOf<KotlinTarget>()
        if (ideaActive) {
            current.add(getByName("posix"))
        } else {
            current.addAll(listOf(getByName("macosX64"), getByName("linuxX64"), getByName("mingwX64")))
        }


        val paths = listOf("C:/msys64/mingw64/include", "C:/Tools/msys64/mingw64/include")
        current.filterIsInstance<KotlinNativeTarget>().forEach { platform ->
            platform.compilations.getByName("main") {
                val libcurl by cinterops.creating {
                    defFile = File("posix/interop/libcurl.def")

                    if (platform.name == "mingwX64") {
                        includeDirs.headerFilterOnly(paths)
                    } else {
                        includeDirs.headerFilterOnly(
                            listOf(
                                "/opt/local/include",
                                "/usr/local/include",
                                "/usr/include",
                                "/usr/include/x86_64-linux-gnu",
                                "/usr/local/Cellar/curl/7.62.0/include",
                                "/usr/local/Cellar/curl/7.63.0/include"
                            )
                        )

                    }
                }

            }

            platform.compilations.getByName("test") {
                if (platform.name == "mingwX64") {
                    linkerOpts("-LC:/msys64/mingw64/lib -LC:/Tools/msys64/mingw64/lib -lcurl")
                }
            }
        }
    }

    sourceSets {
        posixMain {
            dependencies {
                api(project(":ktor-client:ktor-client-core"))
                api(project(":ktor-http:ktor-http-cio"))
            }
        }
        posixTest {
            dependencies {
                api(project(":ktor-client:ktor-client-features:ktor-client-logging"))
                api(project(":ktor-client:ktor-client-features:ktor-client-json"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization_version")
            }
        }
    }
}
