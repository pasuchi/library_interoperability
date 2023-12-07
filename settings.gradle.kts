pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Interoperability"
include(":app")
include(":interoperability")
include(":interoperability:domain")
include(":interoperability:data")
include(":interoperability:presenter")
