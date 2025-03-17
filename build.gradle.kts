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
    implementation("net.minestom:minestom-snapshots:1_21_4-b7c38fd36b")
}

tasks.test {
    useJUnitPlatform()
}