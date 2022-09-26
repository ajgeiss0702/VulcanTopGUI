plugins {
    java
    id("com.github.johnrengelman.shadow").version("6.1.0")
}

group = "us.ajg0702"
version = "1.0.2"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.io/repository/nms/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.ajg0702.us/releases/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    compileOnly(group = "org.spigotmc", name = "spigot", version = "1.16.5-R0.1-SNAPSHOT")

    //compileOnly(files("libs/private/VulcanAPI.jar"))
    compileOnly("me.frep:vulcan-api:1.0.0")

    implementation("fr.mrmicky:FastInv:3.0.3")
    implementation("us.ajg0702:ajUtils:1.1.18")

    implementation("us.ajg0702.commands.platforms.bukkit:bukkit:1.0.0-pre14")
    implementation("us.ajg0702.commands.api:api:1.0.0-pre14")

    implementation("net.kyori:adventure-api:4.11.0")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")
    implementation("net.kyori:adventure-platform-bukkit:4.1.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<ProcessResources> {
    include("**/*.yml")
    filter<org.apache.tools.ant.filters.ReplaceTokens>(
        "tokens" to mapOf(
            "VERSION" to project.version.toString()
        )
    )
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.shadowJar {
    archiveClassifier.set("")
    relocate("net.kyori", "us.ajg0702.vactop.libs.kyori")
    relocate("fr.mrmicky.fastinv", "us.ajg0702.vactop.libs.fastinv")
    relocate("us.ajg0702.utils", "us.ajg0702.vactop.libs.utils")
}