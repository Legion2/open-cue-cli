plugins {
    val kotlinVersion = "1.8.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    application
    id("org.beryx.runtime") version "1.13.0"
}

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/ktor")
}

application {
    mainClass.set("io.github.legion2.open_cue_cli.MainKt")
    executableDir = ""
    applicationName = "open-cue-cli"
}

runtime {
    addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    addModules(
        "java.base",
        "java.sql",//because of gson
        "jdk.unsupported"//https://stackoverflow.com/questions/61727613/unexpected-behaviour-from-gson
    )

    imageZip.set(file("$buildDir/${project.application.applicationName}.zip"))
    targetPlatform("linux-x64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("linux-aarch64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_aarch64_linux_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("linux-arm32") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_arm_linux_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("windows-x64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.3_7.zip"))
    }
    targetPlatform("mac-x64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_x64_mac_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("mac-aarch64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_aarch64_mac_hotspot_17.0.3_7.tar.gz"))
    }
}

val ktorVersion = "2.2.4"
val cliktVersion = "3.5.2"

dependencies {
    implementation("com.github.ajalt.clikt:clikt:$cliktVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.7"
        apiVersion = "1.7"
    }
}

val generateCliCompletions by tasks.registering(JavaExec::class) {
    dependsOn("classes")
    classpath = sourceSets.main.get().runtimeClasspath
    main = application.mainClass.get()
    environment("OPEN_CUE_CLI_COMPLETE", "bash")

    val completions = file("$buildDir/completions")
    outputs.dir(completions)
    doFirst {
        completions.mkdirs()
        standardOutput = File(completions, "bash-complete-open-cue-cli.sh").outputStream()
    }
}

distributions {
    main {
        contents {
            from(generateCliCompletions)
        }
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "7.3"
    distributionType = Wrapper.DistributionType.ALL
}
