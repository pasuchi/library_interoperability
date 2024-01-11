plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("maven-publish")
}


android {
    namespace = "com.bcp.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
}

publishing {
    publications.create<MavenPublication>("lib") {
        groupId = "com.test.pe.domain"
        artifactId = "interoperability"
        version = "1.0.7"
        artifact("$buildDir/outputs/aar/domain-release.aar")
    }

    repositories.maven("https://maven.pkg.github.com/.../library_interoperability") {
        name = "..."
        credentials {
            username = "..."
            password = "..."
        }
    }

}



