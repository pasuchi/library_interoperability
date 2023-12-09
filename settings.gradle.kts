pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        google()
        mavenCentral()
        repositories {
            maven {
                url = uri("https://maven.pkg.github.com/pasuchi/library_interoperability")
                credentials {
                    username = "Pasuchi"
                    password = "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"
                }
            }
        }

    }
}

rootProject.name = "Interoperability"
include(":app")
include(":interoperability")
include(":interoperability:domain")
include(":interoperability:data")
include(":interoperability:presenter")
include(":domain-aar")
