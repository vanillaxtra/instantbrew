plugins {
    java
    id("com.gradleup.shadow") version "9.3.1"
}

group = "dev.instantbrew"
version = project.property("version")!!

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.2.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to version)
    }
}


tasks.shadowJar {
    archiveClassifier.set("")
    archiveFileName.set("InstantBrew-${version}.jar")
    dependencies {
        exclude { it.moduleGroup != "org.bstats" }
    }
    relocate("org.bstats", project.group.toString())
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}
