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
<<<<<<< HEAD
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ToastOutApplication"
include(":app")
 
=======
        maven("https://repository.map.naver.com/archive/maven") // 네이버 지도 SDK 저장소 추가
    }
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
>>>>>>> 3c45077 (하하하 컨텐츠 추천에서 음악 미리듣기까지 내가 해냄)
