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

rootProject.name = "mission-mate"
include(":app")
include(":core:data")
include(":core:network")
include(":core:datastore")
include(":core:domain")
include(":core:designsystem")
include(":core:navigation")
include(":feature:login")
include(":feature:main")
include(":feature:board")
include(":core:model")
include(":feature:onboarding")
include(":feature:profile")
include(":feature:setting")
