# Technology Stack

**Project:** Willpower Tracker (AI-Enhanced Habit Tracker)
**Researched:** 2025-05-22
**Confidence:** HIGH

## Recommended Stack

### Core Framework
| Technology | Version | Purpose | Why |
|------------|---------|---------|-----|
| Kotlin | 2.1.0 | Primary Language | Industry standard, null-safety, coroutines. |
| Jetpack Compose | 1.7+ | UI Framework | Modern declarative UI, faster development than XML. |
| Android SDK | 34+ | Platform | Targeting latest features and security. |

### Architecture & State Management
| Technology | Purpose | Why |
|------------|---------|-----|
| Hilt | Dependency Injection | Standard for Android, reduces boilerplate, simplifies testing. |
| ViewModel | UI Logic | Lifecycle-aware state holder. |
| Kotlin Flow | Reactive Streams | Type-safe, built-in support for Coroutines, superior to LiveData. |

### Data Persistence
| Technology | Purpose | Why |
|------------|---------|-----|
| Room | Local Database | Type-safe SQLite abstraction for habit data and history. |
| DataStore | Preferences | Replacement for SharedPreferences; async and transactional. |

### Networking & AI
| Technology | Purpose | Why |
|------------|---------|-----|
| Retrofit | API Client | Standard for RESTful communication. |
| OkHttp | HTTP Library | Robust networking foundation. |
| Gemini SDK | AI Integration | Powering "AI Coach" and "Smart Insights". |

### Supporting Libraries
| Library | Purpose | When to Use |
|---------|---------|-------------|
| Coil | Image Loading | Loading user avatars or challenge icons. |
| Navigation Compose | Routing | Type-safe navigation between Compose screens. |
| KSP | Annotation Processing | Faster build times for Room and Hilt. |

## Alternatives Considered

| Category | Recommended | Alternative | Why Not |
|----------|-------------|-------------|---------|
| UI | Jetpack Compose | XML Views | XML is legacy; Compose is the future and already used in industry. |
| DI | Hilt | Koin | Hilt is better integrated with Jetpack and provides compile-time safety. |
| State | Flow | LiveData | LiveData is largely deprecated in favor of Flow/StateFlow. |

## Installation

```kotlin
// Example build.gradle.kts (Catalog versioning recommended)
dependencies {
    implementation("androidx.compose.ui:ui:1.7.0")
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
}
```

## Sources

- [Official Android Documentation (2025)](https://developer.android.com/modern-android-development)
- [Jetpack Compose Roadmap](https://developer.android.com/jetpack/androidx/compose-roadmap)
- [Google AI SDK for Android](https://ai.google.dev/android/sdk)
