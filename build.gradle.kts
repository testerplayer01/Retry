plugins {
    id("java")
    id("io.qameta.allure") version "4.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allure {
    version.set("2.29.0")

    adapter {
        allureJavaVersion.set("2.29.0")
        aspectjWeaver.set(true)
        autoconfigure.set(true)

        frameworks {
            junit5 {
                adapterVersion.set("2.29.0")
                enabled.set(true)
                autoconfigureListeners.set(true)
            }
        }
    }
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
    implementation("org.aeonbits.owner:owner:1.0.12")
    testImplementation("io.qameta.allure:allure-selenide:2.29.0")
    testImplementation("io.qameta.allure:allure-rest-assured:2.29.0")
    testImplementation("io.qameta.allure:allure-junit5:2.29.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty(
        "allure.results.directory",
        layout.buildDirectory.dir("allure-results").get().asFile
    )
    systemProperty("file.encoding", "UTF-8")
}

tasks.test {
    useJUnitPlatform() {
        includeTags("ParameterizedTest", "RestAssuredHomework", "Selenium", "Selenide", "UIHomework", "PageObject")
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
        includeTestsMatching("UIHomeworkWeb")
        includeTestsMatching("BPLesson")
        includeTestsMatching("BPHomework")
        includeTestsMatching("PageObjectHomework")
        includeTestsMatching("ConfigTests")

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