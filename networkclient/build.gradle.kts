plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
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

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

apply(from = rootProject.file("github.properties"))

project.afterEvaluate {
    publishing {
        publications {
            // Simple convenience function to hide the nullability of `findProperty`.
            fun getProperty(key: String): String {
                return findProperty(key)?.toString() ?: error("Failed to find property for $key")
            }
            create<MavenPublication>("gpr"){
                from(components.getByName("release"))
                groupId = getProperty("LIBRARY_GROUP")
                artifactId = "network-client"
                version = getProperty("LIBRARY_VERSION")
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/rahulabrol/NetworkClient")
                credentials {
                    username = project.findProperty("ext.usr").toString()
                    password = project.findProperty("ext.key").toString()
                }
            }
        }
    }
}