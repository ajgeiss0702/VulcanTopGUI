plugins {
    java
}

group = "us.ajg0702"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    compileOnly(files("libs/private/VulcanAPI.jar"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}