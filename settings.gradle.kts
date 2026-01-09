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

rootProject.name = "RidersWrist"
include(":app")     // Phone (UI)
include(":wear")    // Watch (UI)
include(":shared")  // Domain & Data (Common Logic)
