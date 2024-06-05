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
include(":common:coroutines")
include(":common:ui:pdfview")
include(":core:database")
include(":core:design")
include(":core:filestore")
include(":core:platform")
include(":core:translations")
include(":features:createscan")
include(":features:createscan:data:model")
include(":features:createscan:data:repository")
include(":features:createscan:usecase")
include(":features:home")
include(":features:savedscans")
include(":features:savedscans:data:model")
include(":features:savedscans:data:repository")
include(":features:savedscans:usecase")
include(":features:scandetails")
include(":features:scandetails:usecase")
