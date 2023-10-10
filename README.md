# TheBeerCounter app
## Showcase app for Android development allowing users to keep track of their bar tab
This is an Android app in which the users can count the bar consumption and avoid arguments about who should pay what at the end of the night. The app is also used as a showcase for Android development principles.

## Features
### Current tab
+ List of current items and their counts
+ Adding new items
+ Adding count of an item
+ Modifying the count of items manually
+ Clearing the tab

<img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134427.png?raw=true" width=300px/>
<img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134912.png?raw=true" width=300px/>
<img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134926.png?raw=true" width=300px/>

### History
+ Expandable list of days
+ History of items adding/removing with timestamps

<img src="https://github.com/DanicMa/TheBeerCounter/blob/screenshots/screenshots/Screenshot_20231010_134849.png?raw=true" width=300px/>

### Future improvements
+ Showing a graph of consumption over time
+ Showing "consumption speed" based on the duration and the number of items
+ Calculating the total cost of the tab based on the prices of the items
+ Calculating blood alcohol level based on the drinks and user's weight

## Implementation
### Architecture
+ Fully implemented in Jetpack Compose
+ MVI architecture
  + Business logic is implemented in [ViewModels](https://github.com/DanicMa/TheBeerCounter/blob/master/app/src/main/java/cz/damat/thebeercounter/common/base/BaseViewModel.kt)
  + State of every piece of the data that should be shown is stored in a data class that is passed to the UI using a StateFlow that is updated exclusively from the ViewModel
  + UI events (such as user clicks) are passed from the UI to the ViewModel using a Channel
  + One time "commands" (such as navigation) are passed from the ViewModel to the UI using a Flow

// TODO: Use Material3 composables once they are release as stable and not marked as experimental

### Package structure
+ Few root packages are present:
  + `common` - [base classes](https://github.com/DanicMa/TheBeerCounter/tree/master/app/src/main/java/cz/damat/thebeercounter/common/base) + [utils and extensions](https://github.com/DanicMa/TheBeerCounter/tree/master/app/src/main/java/cz/damat/thebeercounter/common/utils)
  + `room` - [database related classes](https://github.com/DanicMa/TheBeerCounter/tree/master/app/src/main/java/cz/damat/thebeercounter/room)
  + `scene` - contains [subpackage for each of the screens](https://github.com/DanicMa/TheBeerCounter/tree/master/app/src/main/java/cz/damat/thebeercounter/scene)
  + `ui` - [reusable composables](https://github.com/DanicMa/TheBeerCounter/tree/master/app/src/main/java/cz/damat/thebeercounter/ui/component) and [definitions related to styling](https://github.com/DanicMa/TheBeerCounter/tree/master/app/src/main/java/cz/damat/thebeercounter/ui/theme)

// TODO: modularization - if a larger number of features would be expected, the app would be split into multiple modules to, amongst other things, minimize build times, e.g. `common`(resources and utils), `compose`(reusable composables and styling), `data`(entities, DAOs, repositories) etc. ... possible further modularization could easily be done based on screens (or feature-scoped screen sets) due to separate packaging of all classes related to a single screen in the 'scene' package

### Navigation
+ Compose Navigation handles navigation between screens in the [DashboardNavigation.kt](https://github.com/DanicMa/TheBeerCounter/blob/master/app/src/main/java/cz/damat/thebeercounter/scene/dashboard/DashboardNavigation.kt)
+ In case of a larger number of features (that don't require sophisticated data transfers between them) a dedicated Activity with its own NavHost could be used for each feature to better separation of code and navigation readability

### Data persistence
+ Room is used for data persistence of all the products and history items
+ The data is accessed using a Repository and DAO pattern (e.g. [HistoryRepository](https://github.com/DanicMa/TheBeerCounter/blob/master/app/src/main/java/cz/damat/thebeercounter/repository/HistoryRepository.kt))
+ For non-structured data persistence the DataStore would be used if the need for it arises

### Dependency injection
+ Koin is used for dependency injection (providing the Repository, DAO and ViewModel instances)

### Networking
+ Currently the app is not using any networking, but if the need for it arises (maybe for the blood alcohol level calculation?), Ktor would be used for making REST requests
