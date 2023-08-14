import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.rahul.networkclient"
    compileSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}

project.afterEvaluate {
    publishing {
        publications {
            // Simple convenience function to hide the nullability of `findProperty`.
            fun getProperty(key: String): String {
                return findProperty(key)?.toString() ?: error("Failed to find property for $key")
            }
            create<MavenPublication>("maven") {
                artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
                groupId = getProperty("GROUP")
                artifactId = getProperty("POM_ARTIFACT_ID")
                version = getProperty("VERSION_NAME")
            }
        }
        repositories {
            maven {
                val githubProperties = Properties()
                if (file("${project.rootDir}/github.properties").exists()) {
                    try {
                        githubProperties.load(FileInputStream(file("${project.rootDir}/github.properties")))
                    } catch (e: Exception) {
                        println(e.toString())
                    }
                }

                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/rahulabrol/NetworkClient")
                credentials {
                    username = githubProperties["gpr.usr"]?.toString() ?: System.getenv("GPR_USER")
                    password =
                        githubProperties["gpr.key"]?.toString() ?: System.getenv("GPR_API_KEY")
                }
            }
        }
    }
}