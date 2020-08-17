import java.io.ByteArrayOutputStream

plugins {
    kotlin("jvm") version "1.4.0"
    application
    id("org.beryx.runtime") version "1.11.2"
}

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/ktor")
}

application {
    mainClassName = "io.github.legion2.open_cue_cli.MainKt"
    executableDir = ""
    applicationName = "open-cue-cli"
}

runtime {
    addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    addModules("java.base",
            "java.sql",//because of gson
            "jdk.unsupported"//https://stackoverflow.com/questions/61727613/unexpected-behaviour-from-gson
    )

    imageZip.set(file("$buildDir/${project.application.applicationName}.zip"))
    targetPlatform("linux-x64") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_x64_linux_hotspot_14.0.2_12.tar.gz"))
    }
    targetPlatform("linux-aarch64") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_aarch64_linux_hotspot_14.0.2_12.tar.gz"))
    }
    targetPlatform("linux-arm32") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_arm_linux_hotspot_14.0.2_12.tar.gz"))
    }
    targetPlatform("windows-x64") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_x64_windows_hotspot_14.0.2_12.zip"))
    }
    targetPlatform("mac-x64") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_x64_mac_hotspot_14.0.2_12.tar.gz"))
    }
}

val ktorVersion = "1.3.2"
val cliktVersion = "2.6.0"

dependencies {
    implementation("com.github.ajalt:clikt:$cliktVersion")
    implementation("io.ktor:ktor-client:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "14"
        languageVersion = "1.4"
        apiVersion = "1.4"
        freeCompilerArgs += "-Xopt-in=com.github.ajalt.clikt.completion.ExperimentalCompletionCandidates"
    }
}

val generateCliCompletions by tasks.registering(JavaExec::class) {
    dependsOn("classes")
    classpath = sourceSets.main.get().runtimeClasspath
    main = application.mainClassName
    environment("OPEN_CUE_CLI_COMPLETE", "bash")

    val completions = file("$buildDir/completions")
    outputs.dir(completions)
    doFirst {
        println("This is executed first during the execution phase.")
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
    gradleVersion = "6.6"
    distributionType = Wrapper.DistributionType.ALL
}

// workaround for https://github.com/beryx/badass-runtime-plugin/issues/67
tasks.withType<CreateStartScripts> {
    doLast {
        windowsScript.writeText(windowsScript.readText()
                .replace("set JAVA_HOME=\"%~dp0..\"", "set JAVA_HOME=\"%~dp0\""))
    }
}
