import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.r_jeff.android_tools_ktx"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

val jeffProperties = Properties().apply {
    load(FileInputStream(rootDir.resolve("local.properties")))
}
val jeffUsername = jeffProperties.getProperty("jeffUsername") ?: ""
val jeffPassword = jeffProperties.getProperty("jeffPassword") ?: ""
val jeffUrlRelease = jeffProperties.getProperty("jeffUrlRelease") ?: ""
val jeffUrlSnapshot = jeffProperties.getProperty("jeffUrlSnapshot") ?: ""

val jeffGroupId = findProperty("jeffGroupId").toString()
val jeffArtifactId = findProperty("jeffArtifactId").toString()
val jeffVersion = findProperty("jeffVersion").toString()


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                url = uri(
                    if (jeffVersion.endsWith("SNAPSHOT")) {
                        jeffUrlSnapshot
                    } else {
                        jeffUrlRelease
                    }
                )
                credentials {
                    username = jeffUsername
                    password = jeffPassword
                }
            }
        }

        publications {
            create<MavenPublication>("product") {
                print(components["release"])
                from(components["release"])
                groupId = jeffGroupId
                artifactId = jeffArtifactId
                version = jeffVersion
            }
        }
    }
}