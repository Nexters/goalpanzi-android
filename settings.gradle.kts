pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.5.0")
}

rootProject.name = "mission-mate"
include(":app")
include(":core:network")
include(":core:datastore")
include(":core:domain")
include(":core:designsystem")
include(":core:navigation")
include(":feature:login")
include(":feature:main")
include(":feature:board")
include(":feature:onboarding")
include(":feature:profile")
include(":feature:setting")
include(":core:data:mission")
include(":core:data:common")
include(":core:data:onboarding")
include(":core:data:auth")
include(":core:data:setting")
include(":core:data:user")
