# Technology Stack

**Analysis Date:** 2025-02-14

## Languages

**Primary:**
- Kotlin 2.0.21 - Android app development across all 7 labs

**Secondary:**
- Java 17 - Toolchain for Gradle builds (JVM)

## Runtime

**Environment:**
- Android Runtime (ART) - Target SDK 34 (Android 14)

**Package Manager:**
- Gradle 8.9 - Build automation
- Lockfile: Present (`gradle-wrapper.properties` in each lab)

## Frameworks

**Core:**
- Android Gradle Plugin 8.7.3 - Android build system
- Kotlin 2.0.21 - Kotlin language support
- Kotlin Parcelize - Parcelable data generation (Labs 2-7)
- Kotlin Serialization 2.0.21 - JSON serialization (Labs 5-7)
- Navigation Safe Args 2.7.6 - Type-safe navigation (Labs 4-7)
- KSP 2.0.21-1.0.28 - Kotlin Symbol Processing (Lab 7)

**Testing:**
- JUnit 4.13.2 - Unit testing
- AndroidX Test Extensions 1.1.5 - Android JUnit runner
- Espresso 3.5.1 - UI testing

**Build/Dev:**
- Gradle Wrapper 8.9 - Build distribution management

## Key Dependencies

**Critical:**

**AndroidX Core (all labs):**
- `androidx.core:core-ktx:1.12.0` - Kotlin extensions for Android
- `androidx.appcompat:appcompat:1.6.1` - AppCompat backward compatibility
- `com.google.android.material:material:1.11.0` - Material Design components
- `androidx.constraintlayout:constraintlayout:2.1.4` - Constraint layouts
- `androidx.recyclerview:recyclerview:1.3.2` - RecyclerView for lists

**Navigation (Labs 3-7):**
- `androidx.fragment:fragment-ktx:1.6.2` - Fragment KTX extensions (Labs 3-7)
- `androidx.navigation:navigation-fragment-ktx:2.7.6` - Navigation Fragment (Labs 4-7)
- `androidx.navigation:navigation-ui-ktx:2.7.6` - Navigation UI (Labs 4-7)

**Networking (Labs 5-7):**
- `com.squareup.retrofit2:retrofit:2.9.0` - REST API client
- `com.squareup.okhttp3:okhttp:4.12.0` - HTTP client
- `com.squareup.okhttp3:logging-interceptor:4.12.0` - HTTP logging
- `org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2` - JSON serialization
- `com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0` - Retrofit serialization converter
- `io.coil-kt:coil:2.5.0` - Image loading (Lab 5)

**Lifecycle (Labs 5-7):**
- `androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0` - ViewModel KTX
- `androidx.lifecycle:lifecycle-livedata-ktx:2.7.0` - LiveData KTX
- `androidx.lifecycle:lifecycle-runtime-ktx:2.7.0` - Lifecycle runtime KTX

**Coroutines (Labs 5-7):**
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3` - Kotlin coroutines for Android

**Storage:**
- `androidx.datastore:datastore-preferences:1.0.0` - Key-value storage (Labs 6-7)

**Database (Lab 7):**
- `androidx.room:room-runtime:2.6.1` - Room database runtime
- `androidx.room:room-ktx:2.6.1` - Room KTX extensions
- `androidx.room:room-compiler:2.6.1` - Room annotation processor (KSP)

**Paging (Lab 7):**
- `androidx.paging:paging-runtime-ktx:3.2.1` - Paging 3 library
- `androidx.room:room-paging:2.6.1` - Room Paging integration

**Swipe Refresh (Lab 7):**
- `androidx.swiperefreshlayout:swiperefreshlayout:1.1.0` - Swipe refresh UI

## Configuration

**Environment:**
- BuildConfig for compile-time constants (Labs 5-7)
- BuildConfig fields: `API_KEY`, `API_BASE_URL`
- gradle.properties: `API_KEY` property for GLM API key

**Build:**
- JVM target: Java 1.8 compatibility
- Kotlin compiler: jvmTarget = "1.8"
- Namespace: `com.willpower.tracker`
- Application IDs by lab: `com.willpower.tracker.lab{N}`

**Build Features:**
- ViewBinding enabled (Labs 4-7)
- BuildConfig enabled (Labs 5-7)

## Platform Requirements

**Development:**
- JDK 17 (Microsoft OpenJDK at `/usr/lib/jvm/java-17-microsoft-openjdk`)
- Android SDK 34 (compileSdk)
- Gradle 8.9+

**Production:**
- Min SDK: 24 (Android 7.0 Nougat)
- Target SDK: 34 (Android 14)
- Deployment target: Android devices API 24+

---

*Stack analysis: 2025-02-14*
