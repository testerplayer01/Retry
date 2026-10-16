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
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    implementation("org.seleniumhq.selenium:selenium-java:4.50.0")
    implementation("com.codeborne:selenide:7.18.2")
}

tasks.test {
    useJUnitPlatform() {
        includeTags("ParameterizedTest", "RestAssuredHomework", "Selenium", "Selenide", "UIHomework")
    }
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
    filter {
        includeTestsMatching("secondLessonTest")
        includeTestsMatching("AssertLessonTest")
        includeTestsMatching("RestAssuredHomework")
        includeTestsMatching("SeleniumLesson")
        includeTestsMatching("SelenideLesson")
        includeTestsMatching("UIHomeworkSelenium")
        includeTestsMatching("UIHomeworkSelenide")
    }
}

tasks.register<Test>("apiTest") {
    description = "Runs all REST Assured API tests."
    group = "verification"
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    useJUnitPlatform {
        includeTags("RestAssuredHomework")
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