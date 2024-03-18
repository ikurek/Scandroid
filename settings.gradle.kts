pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Scandroid"
include(":app")
include(":core:design")
include(":core:filestore")
include(":core:translations")
include(":features:createscan")
include(":features:createscan:data:model")
include(":features:createscan:data:repository")
include(":features:createscan:usecase")
include(":features:home")
include(":features:savedscans")