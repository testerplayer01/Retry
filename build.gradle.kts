plugins {
    base
    id("io.qameta.allure") version "4.1.0" apply false
}

allprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    tasks.withType<Test>().configureEach {
        systemProperty("file.encoding", "UTF-8")

        providers.environmentVariable("BASE_URI").orNull?.let {
            systemProperty("base.uri", it)
        }
        providers.environmentVariable("ADMIN_LOGIN").orNull?.let {
            systemProperty("admin.login", it)
        }
        providers.environmentVariable("ADMIN_PASSWORD").orNull?.let {
            systemProperty("admin.password", it)
        }
    }
}
