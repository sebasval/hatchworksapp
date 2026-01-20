# HatchWorks App Challenge

A native Android Pokédex application built with modern Android development practices, demonstrating Clean Architecture, offline-first strategy, and comprehensive testing.

## Features

- Browse a list of Pokémon with images and IDs
- View detailed information for each Pokémon including stats, types, and abilities
- Offline-first: Data is cached locally and works without internet connection
- Pull-to-refresh to update data from the API
- Modern Material 3 UI with Jetpack Compose

## Architecture

The project follows **Clean Architecture** principles with a modular structure:

```
HatchWorksApp/
├── app/                    # Application entry point, DI configuration
├── domain/                 # Business logic (UseCases, Repository interfaces, Models)
├── data/                   # Data layer (API, Database, Repository implementations)
└── presentation/           # UI layer (Screens, ViewModels, Theme)
```

### Module Dependencies

```
:app → :presentation, :data, :domain
:presentation → :domain
:data → :domain
```

## Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Koin |
| Networking | Retrofit + OkHttp + Gson |
| Local Database | Room |
| Image Loading | Coil |
| Navigation | Navigation Compose |
| Async | Kotlin Coroutines + Flow |
| Testing | JUnit, MockK, Turbine, Espresso, Compose Testing |

## API

This app uses the [PokéAPI](https://pokeapi.co/) - a free RESTful Pokémon API.

## Project Structure

### Domain Module (`:domain`)
Pure Kotlin module with no Android dependencies:
- `model/` - Domain models (Pokemon, PokemonDetail)
- `repository/` - Repository interfaces
- `usecase/` - Business logic use cases

### Data Module (`:data`)
Android Library handling data operations:
- `remote/` - Retrofit API, DTOs, Remote DataSource
- `local/` - Room Database, Entities, Local DataSource
- `repository/` - Repository implementations with offline-first strategy

### Presentation Module (`:presentation`)
Android Library containing UI components:
- `list/` - Pokemon list screen (ViewModel, Screen, UiState)
- `detail/` - Pokemon detail screen (ViewModel, Screen, UiState)
- `navigation/` - Navigation setup
- `theme/` - Material 3 theme configuration

### App Module (`:app`)
Application configuration:
- `di/` - Koin dependency injection modules
- `MainActivity` - Single activity entry point
- `HatchWorksApplication` - Application class with Koin initialization

## Testing

The project includes comprehensive test coverage:

### Unit Tests
- **Domain**: UseCase tests
- **Data**: Repository, DataSource tests
- **Presentation**: ViewModel tests

### UI Tests (Instrumented)
- Compose UI tests for individual screens
- Navigation E2E tests

### Running Tests

```bash
# Unit tests
./gradlew testDebugUnitTest
./gradlew :domain:test

# Instrumented tests (requires emulator/device)
./gradlew :app:connectedDebugAndroidTest
```

## Building the Project

### Requirements
- Android Studio Hedgehog or newer
- JDK 11
- Android SDK with API 35
- Min SDK: API 23 (Android 6.0)

### Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Run all checks
./gradlew clean assembleDebug testDebugUnitTest
```

## Offline-First Strategy

The app implements an offline-first approach:

1. Data is first served from local Room database
2. Network request is triggered in the background
3. Fresh data is saved to local database
4. UI automatically updates via Kotlin Flow

This ensures the app works seamlessly even without internet connectivity.
