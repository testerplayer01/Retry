plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    filter {
        includeTestsMatching("secondLessonTest")
    }
}

tasks.register("runSecondLessonTest") {
    dependsOn("test")
}

tasks.register("finishMessage") {
    dependsOn("runSecondLessonTest")
    doLast {
        println("test run is over")
    }
}