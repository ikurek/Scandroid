pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.android.gms.oss-licenses-plugin") {
                useModule("com.google.android.gms:oss-licenses-plugin:${requested.version}")
            }
        }
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
include(":analytics")
include(":analytics:data:model")
include(":app")
include(":common:coroutines")
include(":common:ui:pdfview")
include(":core:database")
include(":core:datastore")
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
include(":features:settings")
include(":features:settings:data:model")
include(":features:settings:data:repository")
include(":features:settings:usecase")
