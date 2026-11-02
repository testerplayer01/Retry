plugins {
    id("java")
    id("io.qameta.allure")
}

dependencies {
    testImplementation(project(":common"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    testImplementation("io.qameta.allure:allure-rest-assured:2.29.0")
    testImplementation("io.qameta.allure:allure-junit5:2.29.0")
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    systemProperty(
        "allure.results.directory",
        layout.buildDirectory.dir("allure-results").get().asFile
    )
}

tasks.register<Test>("smokeApi") {
    description = "Runs the API smoke tests."
    group = "verification"
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    useJUnitPlatform {
        includeTags("smoke")
    }
}