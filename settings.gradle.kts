import java.io.FileInputStream
import java.util.Properties

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
val jeffProperties = Properties().apply {
    load(FileInputStream(rootDir.resolve("local.properties")))
}
val jeffUsername = jeffProperties.getProperty("jeffUsername") ?: ""
val jeffPassword = jeffProperties.getProperty("jeffPassword") ?: ""
val jeffVersion = jeffProperties.getProperty("jeffVersion") ?: ""
val jeffUrlRelease = jeffProperties.getProperty("jeffUrlRelease") ?: ""
val jeffUrlSnapshot = jeffProperties.getProperty("jeffUrlSnapshot") ?: ""

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://maven.aliyun.com/repository/public")
        }
        maven {
            credentials {
                username = jeffUsername
                password = jeffPassword
            }
            url = uri(
                if (jeffVersion.endsWith("SNAPSHOT")) {
                    jeffUrlSnapshot
                } else {
                    jeffUrlRelease
                }
            )
        }
    }
}

rootProject.name = "Android-tools-ext"
include(":android-tools-demo")
include(":android-tools-ktx")
