# Plan: Phase 1 - Foundation & Architecture

## Overview
This phase establishes the structural foundation of the Willpower Tracker app. We will transition from the legacy lab setup to a modern Android project using Clean Architecture, Hilt for Dependency Injection, and Jetpack Compose for the UI.

## Success Criteria
- [ ] Project compiles with Hilt and Kotlin 2.1.0.
- [ ] Directory structure follows Clean Architecture (Data, Domain, UI).
- [ ] Custom Compose theme is implemented and used in a "Hello World" screen.
- [ ] Base Repository interfaces and Result wrappers are defined in the Domain layer.

## Task Breakdown

### 1. Project Scaffolding (SETUP-01)
- **Agent:** `mobile-developer`
- **Goal:** Initialize the new app module and configure build files.
- **Input:** Current root directory.
- **Output:** New `app` module with updated `build.gradle.kts`.
- **Verify:** `./gradlew assembleDebug` passes.

### 2. Clean Architecture Structure (SETUP-02)
- **Agent:** `mobile-developer`
- **Goal:** Create the package hierarchy for layers.
- **Input:** `app` module.
- **Output:** Folders: `data/`, `domain/`, `ui/`, `di/`, `core/`.
- **Verify:** Folders exist and contain placeholder `README.md` or classes.

### 3. Hilt Integration (SETUP-03)
- **Agent:** `mobile-developer`
- **Goal:** Set up DI throughout the app.
- **Input:** `build.gradle.kts`.
- **Output:** `WillpowerApplication` class with `@HiltAndroidApp` and Hilt modules.
- **Verify:** App starts without DI errors in logs.

### 4. Compose Design System (SETUP-04)
- **Agent:** `mobile-developer`
- **Goal:** Implement the brand identity in Compose.
- **Input:** Research on habits/willpower aesthetics.
- **Output:** `Theme.kt`, `Color.kt`, `Type.kt`, `Shape.kt`.
- **Verify:** A sample screen correctly applies the theme colors and fonts.

### 5. Domain Layer: Core Models & Repositories (DATA-02, DATA-03)
- **Agent:** `mobile-developer`
- **Goal:** Define the business logic contracts.
- **Input:** REQUIREMENTS.md.
- **Output:** `Habit` domain model, `HabitRepository` interface, and `Result<T>` wrapper.
- **Verify:** Interfaces are pure Kotlin and don't depend on Android libraries.

## Phase X: Verification
- [ ] Run `python .agent/scripts/checklist.py .`
- [ ] Verify build: `./gradlew assembleDebug`
- [ ] Manual check: Directory structure matches Clean Architecture principles.
