pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        gradlePluginPortal()

    }

    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)

        repositories {
            google()
            mavenCentral()
            maven("https://repository.map.naver.com/archive/maven") // 네이버 지도 SDK 저장소 추가
        }
    }

    rootProject.name = "toastout"
    include(":app")
}
