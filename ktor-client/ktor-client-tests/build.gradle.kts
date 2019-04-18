import org.jetbrains.kotlin.gradle.plugin.*
import java.io.*
import java.net.*

description = "Common tests for client"

val junit_version: String by project.extra
val kotlin_version: String by project.extra
val logback_version: String by project.extra

val ideaActive: Boolean by project.extra

plugins {
    id("kotlinx-serialization")
}

open class KtorTestServer : DefaultTask() {
    var server: Closeable? = null
    lateinit var main: String
    lateinit var classpath: FileCollection

    @TaskAction
    fun exec() {
        println("[TestServer] start")
        val urlClassLoaderSource = classpath.map { file -> file.toURI().toURL() }.toTypedArray()
        val loader = URLClassLoader(urlClassLoaderSource, ClassLoader.getSystemClassLoader())

        val mainClass = loader.loadClass(main)
        val main = mainClass.getMethod("startServer")
        try {
            server = main.invoke(null) as Closeable
        } catch (ignored: Throwable) {
        }
    }

}

kotlin.sourceSets {
    commonMain {
        dependencies {
            api(project(":ktor-client:ktor-client-core"))
            api(project(":ktor-client:ktor-client-mock"))
            api(project(":ktor-client:ktor-client-tests:ktor-client-tests-dispatcher"))
            api(project(":ktor-client:ktor-client-features:ktor-client-json:ktor-client-serialization"))
        }
    }
    commonTest {
        dependencies {
            api(project(":ktor-client:ktor-client-features:ktor-client-auth"))
            api(project(":ktor-client:ktor-client-features:ktor-client-logging"))
        }
    }
    jvmMain {
        dependencies {
            api(project(":ktor-server:ktor-server-jetty"))
            api(project(":ktor-server:ktor-server-netty"))
            api(project(":ktor-features:ktor-auth"))
            api(project(":ktor-features:ktor-websockets"))
            api("ch.qos.logback:logback-classic:$logback_version")
            api("junit:junit:$junit_version")
            api("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
        }
    }
    jvmTest {
        dependencies {
            runtimeOnly(project(":ktor-client:ktor-client-apache"))
            runtimeOnly(project(":ktor-client:ktor-client-cio"))
            runtimeOnly(project(":ktor-client:ktor-client-android"))
            runtimeOnly(project(":ktor-client:ktor-client-okhttp"))
            runtimeOnly(project(":ktor-client:ktor-client-jetty"))
        }
    }
    jsTest {
        dependencies {
            api(project(":ktor-client:ktor-client-js"))
        }
    }

    if (!ideaActive) {
        listOf("linuxX64Test", "mingwX64Test", "macosX64Test").map { getByName(it) }.forEach {
            it.dependencies {
                api(project(":ktor-client:ktor-client-curl"))
            }
        }

        listOf("iosX64Test", "iosArm64Test", "iosArm64Test", "macosX64Test").map { getByName(it) }.forEach {
            it.dependencies {
                api(project(":ktor-client:ktor-client-ios"))
            }
        }
    }
}

val startTestServer = task<KtorTestServer>("startTestServer") {
    dependsOn(tasks.jvmMainClasses)

    main = "io.ktor.client.tests.utils.TestServerKt"
    val kotlinCompilation = kotlin.targets.getByName("jvm").compilations["test"]
    classpath = (kotlinCompilation as KotlinCompilationToRunnableFiles<*>).runtimeDependencyFiles
}

val testTasks = mutableListOf(
    "jvmTest", "jvmBenchmark"
)

if (!ideaActive) {
    testTasks += listOf(
        "macosX64Test",
        "linuxX64Test",
        "iosTest",
        "mingwX64Test",
        "jsTestMochaChrome",
        "jsTestMochaNode"
    )
}

rootProject.allprojects {
    tasks.matching { it.name in testTasks }.configureEach({
        dependsOn(startTestServer)
    })
}

gradle.buildFinished {
    if (startTestServer.server != null) {
        startTestServer.server?.close()
        println("[TestServer] stop")
    }
}
