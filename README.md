# TheBeerCounter app
## Showcase app for Android development allowing users to keep track of their bar tab
This is an Android app in which the users can count their bar consumption and avoid arguments about who should pay what at the end of the night. The app is also used as a showcase for Android development principles.

## Features
### Current tab
+ List of current items and their counts
+ Adding new items
+ Incrementing count of an item
+ Modifying the count of items manually
+ Clearing the tab

<img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134427.png?raw=true" width=300px/> <img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134912.png?raw=true" width=300px/> <img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134926.png?raw=true" width=300px/>

### History
+ Expandable list of days
+ History of items adding/removing with timestamps

<img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134849.png?raw=true" width=300px/>

### Future improvements
+ Showing a graph of consumption over time
+ Showing "consumption speed" based on time difference between count incrementing
+ Calculating the total cost of the tab based on the prices of items
+ Calculating blood alcohol level based on the drinks and user's weight

## Implementation
### MVI architecture
+ [ViewModels](https://github.com/DanicMa/TheBeerCounter/blob/master/app/src/main/java/cz/damat/thebeercounter/common/base/BaseViewModel.kt) provide state data classes with every piece of information that should be shown to the Composables using a StateFlow. This flow is updated with new instances exclusively from the ViewModel.
+ UI events (such as user clicks, a.k.a. "intents") are passed from the UI to the ViewModel using a Channel.
+ One time "commands" (such as Toast showing) are passed from the ViewModel to the UI using a Flow.
+ This removes the need to pass ViewModel instance references further down the Composable tree thus allowing the Composables to be considered [skippable](https://developer.android.com/jetpack/compose/performance/stability#functions) and therefore reducing the number and scope of recompositions.
+ The UI is implemented fully in Jetpack Compose

// TODO: Use Material3 composables once they are released as stable and not marked as experimental

### Modularized clean architecture
+ The app is following the [clean architecture principles](https://www.scaler.com/topics/clean-architecture-android/) where the code is effectively split into 3 layers:
  + `data` - contains all the data related classes (entities, DAOs, repositories, ...)
  + `domain` - contains all the business logic related classes (use cases, mappers, ...) as well as the repository interfaces
  + `presentation` a.k.a. `ui` - contains all the presentation related classes (ViewModels, Composables, ...)
+ Modularization is applied by grouping related classes by rules suggested in an [article by Denis Brandi](https://betterprogramming.pub/the-real-clean-architecture-in-android-modularization-e26940fd0a23):
  + data and domain classes relating to a feature (or a related set of features) are grouped into a single module called `componentXXX`
  + presentation classes of a feature (or their set) are grouped into a single module called `featureYYY`
  + external libraries (e.g. Room) and classes that are needed by all "components" are placed in a `commonLib` module on which all "component" modules depend
  + presentation classes that are used by multiple "features" (e.g. base MVI classes, shared components, utils, ...) are placed in a `commonUI` module on which all "feature" modules depend
  + "feature" modules (presentation) may depend on multiple "component" modules (data and domain) but not vice versa
  + an `app` module is used as a "glue" module that depends on "all" other modules and contains the application class and handles navigating between feature modules

<img src="https://miro.medium.com/v2/resize:fit:720/format:webp/1*vMo2FbipyXkr6BUOxjNr8Q.png" width=300px/>


#### Deviations and simplifications in this project:
  + Use cases are not present as the business logic is not complex enough to warrant their use (they would be used only as a call-through objects that directly call the repository methods). In more complex project with a non-trivial business logic use cases would make for a cleaner, understandable and more reusable code.
  + `Product` and `HistoryItem` DB entities that should be in the `componentCounter` module are placed in `commonLib` module since they are needed during the RoomDB initialization which is done in the `commonLib` module. (For bigger projects this could be mitigated by using separate DBs for each of the "component" modules)
  + DB entities are used directly in the presentation layer as the data is not complex enough to warrant the use of mappers of 1:1 entities.


### Library dependencies management
+ Dependencies are managed using the buildSrc module with a [Dependencies.kt](https://github.com/DanicMa/TheBeerCounter/tree/master/buildSrc/src/main/java/Dependencies.kt) file
+ This allows dependencies to be centrally defined, which assures that all modules are using the same versions and version-incompatibility bugs, that can be hard to find and fix, will not become a problem.
+ Since Gradle's Kotlin DSL is used, these dependency variables are "autosuggested" by the IDE, easily referenced and their usages can be easily found.
+ Disadvantage is that Android Studio does not highlight dependencies with updates available - this can be solved by using the [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin), although user-friendliness still leaves something to be desired.

### Navigation
+ Compose Navigation handles navigation between screens in the [DashboardNavigation.kt](https://github.com/DanicMa/TheBeerCounter/blob/master/app/src/main/java/cz/damat/thebeercounter/scene/dashboard/DashboardNavigation.kt)
+ In case of a larger number of features (that don't require sophisticated data transfers between them) a dedicated Activity with its own NavHost could be used for each feature to better separation of code and navigation readability

### Data persistence
+ Room is used for data persistence of all the products and history items.
+ The data is accessed using a Repository and DAO pattern (e.g. [HistoryRepository](https://github.com/DanicMa/TheBeerCounter/blob/master/app/src/main/java/cz/damat/thebeercounter/repository/HistoryRepository.kt))
+ For non-structured data persistence the DataStore would be used if the need for it arises.

### Dependency injection
+ Koin is used for dependency injection (providing the Repository, DAO and ViewModel instances).

### Codestyle
+ [Detekt](https://github.com/detekt/detekt) (with custom-configured rules for Android/Compose) is used to improve codestyle and warn about possible code-smells and codestyle deviations.

### Networking
+ Currently the app is not using any networking, but if the need for it arises (maybe for the blood alcohol level calculation?), Ktor would be used for making REST requests.
