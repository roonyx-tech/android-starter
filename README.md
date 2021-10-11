# About
Android Starter Kit was created as a way to save time when
starting a new Android project. It is a simple native
single-module app based on [MVVM](https://developer.android.com/jetpack/guide)
architecture with required and optional libraries
(marked with //TODO Optional).

## Libraries

- Android
    - [AndroidX Core][1]
    - [AndroidX Appcompat][2]
- Architecture
    - [AndroidX Lifecycle][3]
- Dependency Injection
    - [Koin][4]
- Network
    - [Retrofit][5]
    - [GSON][6]
    - [OkHttp][7]
- UI
    - [Material Components][8]
    - [Constraint Layout][9]
- Optional
    - [Room][10]
    - [Paging][11]

[1]: https://developer.android.com/jetpack/androidx/releases/core
[2]: https://developer.android.com/jetpack/androidx/releases/appcompat
[3]: https://developer.android.com/jetpack/androidx/releases/lifecycle
[4]: https://insert-koin.io/docs/quickstart/android
[5]: https://square.github.io/retrofit/
[6]: https://github.com/google/gson
[7]: https://square.github.io/okhttp/
[8]: https://material.io/develop/android/docs/getting-started
[9]: https://developer.android.com/jetpack/androidx/releases/constraintlayout
[10]: https://developer.android.com/jetpack/androidx/releases/room
[11]: https://developer.android.com/topic/libraries/architecture/paging

## Getting started
1) Clone this project.
2) Open in Android Studio.
3) Sync Gradle.
4) Change the git remote repository to a new project remote repository.

## Additional functionality

### Paging

To avoid a lot of boilerplate code when create DataSource
was created **PositionalNetworkStateDataSource**.
It is used in a pair with **RetryListing** and **NetworkStateAdapter**.
More information about using in [paging package](https://github.com/roonyx-tech/android-starter/tree/master/app/src/main/java/tech/roonyx/android_starter/ui/paging).

Example of using in **Repository**:
```kotlin
fun getMessageListing(
    viewModelScope: CoroutineScope,
    pageSize: Int
): RetryListing<String> = PositionalNetworkStateFactory(viewModelScope) { startAt, limit ->
    api.getMessages(startAt, limit)
}.createRetryListing(pageSize)
```

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://github.com/roonyx-tech/android-starter/blob/master/LICENSE)