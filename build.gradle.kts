plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform() {
        includeTags("ParameterizedTest")
    }
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
    filter {
        includeTestsMatching("secondLessonTest")
        includeTestsMatching("AssertLessonTest")
    }
}

tasks.register("runSecondLessonTest") {
    dependsOn("test")
}

tasks.register("AssertLessonTest") {
    dependsOn("runSecondLessonTest")
    doLast {
        assert(true)
    }
}
tasks.register("finishMessage") {
    dependsOn("runSecondLessonTest")
    doLast {
        println("test run is over!")
    }
}