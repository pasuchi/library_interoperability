plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")

}

android {
    namespace = "com.bcp.data"
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
    implementation(libs.androidx.core.ktx)
    implementation("com.test.pe.domain:interoperability:1.0.9")

}

publishing {
    publications.create<MavenPublication>("lib") {
        groupId = "com.test.pe.data"
        artifactId = "interoperability"
        version = "1.0.1"
        artifact("$buildDir/outputs/aar/data-release.aar")

        pom.withXml {
            asNode().appendNode("dependencies").apply {
                this.appendNode("dependency").apply {
                    this.appendNode("groupId", "com.test.pe.domain")
                    this.appendNode("artifactId", "interoperability")
                    this.appendNode("version", "1.0.9")
                }
            }
        }


    }

    repositories.maven("https://maven.pkg.github.com/pasuchi/library_interoperability") {
        name = "GitPackegs"
        credentials {
            username = "Pasuchi"
            password = "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"
        }
    }

}



