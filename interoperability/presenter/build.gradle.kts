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
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation(libs.bundles.compose)
    implementation("androidx.compose.material:material:1.5.4")

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.test.ui.manifest)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.test.pe.domain:interoperability:1.0")
}
publishing {
    publications.create<MavenPublication>("lib") {
        groupId = "com.test.pe"
        artifactId = "interoperability"
        version = "1.0"
        artifact("$buildDir/outputs/aar/presenter-release.aar")
    }

    repositories.maven("https://maven.pkg.github.com/pasuchi/library_interoperability") {
        name = "GitPackegs"
        credentials {
            username = "Pasuchi"
            password = "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"
        }
    }

}
