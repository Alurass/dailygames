plugins {
    id("java")
}

group = "be.aluras.dailygames"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.minestom:minestom-snapshots:39d445482f")
}

tasks.test {
    useJUnitPlatform()
}