plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("maven-publish")
}

android {
    namespace = "com.bcp.presenter"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    buildFeatures {
        compose = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.appcompat)
    //implementation(libs.material)
    implementation("com.google.android.material:material:1.3.0")
    implementation(libs.constraintlayout)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.test.ui.manifest)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.test.pe.domain:interoperability:1.0.9")

    // implementation(files("./libs/domain-release.aar"))

    //implementation(project(path = ":domain-aar",configuration = "default"))
}

publishing {
    publications.create<MavenPublication>("lib") {
        groupId = "com.test.pe"
        artifactId = "interoperability"
        version = "1.24"
        artifact("$buildDir/outputs/aar/presenter-release.aar")

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
