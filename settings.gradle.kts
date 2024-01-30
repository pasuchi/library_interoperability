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
include(":interop")
