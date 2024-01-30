@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("maven-publish")
}

android {
    namespace = "com.bcp.interop"
    compileSdk = 33

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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    buildFeatures {
        compose = true
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
}

publishing {
    publications.create<MavenPublication>("lib") {
        groupId = "com.test.pe.monolith"
        artifactId = "interop"
        version = "1.0.0"
        artifact("$buildDir/outputs/aar/interop-release.aar")

    }

    repositories.maven("https://maven.pkg.github.com/pasuchi/library_interoperability") {
        name = "interoperabilityTest"
        credentials {
            username = "Pasuchi"
            password = "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"
        }
    }

}