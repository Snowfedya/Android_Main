# Architecture

**Analysis Date:** 2025-02-14

## Pattern Overview

**Overall:** Progressive Android Architecture Evolution Model

**Key Characteristics:**
- Each lab represents a complete, working state of the WillPower Tracker app
- Labs 1-7 demonstrate evolution from Activity-based to MVVM architecture
- Single Activity Architecture emerges in Lab 3 and persists through Lab 7
- Navigation Component integration starts in Lab 4
- MVVM with Repository pattern and Room database in Lab 7

## Architecture Evolution by Lab

### Lab 1-2: Activity-Based Architecture

**Pattern:** Multiple Activities with Intent navigation

**Components:**
- `OnboardActivity` - Landing screen with app introduction
- `SignInActivity` - Authentication entry point
- `SignUpActivity` - User registration
- `HomeActivity` - Main challenge list with RecyclerView

**Navigation:** Intents with explicit activity navigation
**Data Flow:** Intent extras (String, Parcelable)

### Lab 2: BaseActivity Pattern

**Purpose:** Centralized lifecycle logging

**BaseActivity Location:** `lab-2/app/src/main/java/com/willpower/tracker/activities/BaseActivity.kt`

**Pattern:** All activities extend BaseActivity for lifecycle callbacks
```kotlin
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG: String = this::class.java.simpleName
    // Logs onCreate, onStart, onResume, onPause, onStop, onDestroy, etc.
}
```

### Lab 3: Fragment-Based Architecture

**Pattern:** Single Activity, Multiple Fragments with FragmentManager

**Entry Point:** `MainActivity` - Host for all fragments

**Components:**
- `OnboardFragment` - Onboarding flow
- `SignInFragment` - Authentication with data passed from SignUp
- `SignUpFragment` - Registration with result back to SignIn
- `HomeFragment` - Challenge list display

**Navigation:** FragmentManager with FragmentTransaction
**Data Flow:**
- Activity → Fragment: Bundle arguments
- Fragment → Activity: Listener interfaces

**Navigation Methods in MainActivity:**
```kotlin
fun navigateToOnboard()
fun navigateToSignIn(user: User? = null)
fun navigateToSignUp()
fun navigateToHome()
```

### Lab 4: Navigation Component Architecture

**Pattern:** Single Activity with Navigation Component

**Key Additions:**
- NavController for navigation management
- Safe Args for type-safe argument passing
- ViewBinding for type-safe view access
- `DetailsFragment` - Challenge detail view with technique description

**Navigation Graph:** `lab-4/app/src/main/res/navigation/nav_graph.xml`

**Flow:** Onboard → SignIn → (SignUp) → Home → Details

**Data Flow:**
- Safe Args for fragment-to-fragment data passing
- ViewBinding replaces findViewById

### Lab 5: Network Integration Architecture

**Pattern:** Fragment + Navigation + Network Layer

**Key Additions:**
- `RetrofitClient` - HTTP client singleton with OkHttp logging
- `ApiService` - Retrofit interface for API calls
- `CharacterRepository` - Data layer for network operations
- Network models: `Character`, `CharacterResponse`

**Data Flow:**
```
Fragment → Repository → RetrofitClient → API → Response → UI
```

**Error Handling:** Try-catch with fallback UI messages

### Lab 6: Storage Integration Architecture

**Pattern:** Fragment + Network + Storage (DataStore/File I/O)

**Key Additions:**
- `UserPreferencesManager` - DataStore for user settings
- `ReportManager` - File I/O for report generation (.txt export)
- `BackupManager` - External storage backup/restore
- `SettingsFragment` - Settings management
- `ReportsFragment` - Report viewing and management

**Data Flow for Preferences:**
```
Fragment → UserPreferencesManager → DataStore → Flow → UI observation
```

**File Operations:**
- External storage for user-accessible reports
- Internal storage for backup copies
- Delete → Backup → Restore pattern

### Lab 7: MVVM + Room Architecture (Final State)

**Pattern:** MVVM with Repository, Room, Flow, and SSOT

**Layers:**

**UI Layer (Fragments):**
- `HomeFragment` - Challenge list with AI advice
- `DetailsFragment` - Challenge details
- `FocusModeFragment` - Fullscreen timer
- `SettingsFragment` - User preferences
- `ReportsFragment` - Completion history

**ViewModel Layer:**
- `HomeViewModel` - Task list and advice state management
- `DetailsViewModel` - Challenge detail state
- `SettingsViewModel` - Settings state

**Repository Layer:**
- `ChallengeRepository` - Data orchestration (Room + Network)
- `AdviceRepository` - AI API communication

**Database Layer (Room):**
- `AppDatabase` - Main database singleton
- DAOs: `TaskDao`, `AiAdviceDao`, `CompletionDao`
- Entities: `TaskEntity`, `AiAdviceEntity`, `CompletionEntity`

**Network Layer:**
- `RetrofitClient` - HTTP client (glm-4.7-flash API)
- `ApiService` - Retrofit interface
- Models: `ChatRequest`, `ChatResponse`, `AiAdvice`

**Storage Layer:**
- `UserPreferencesManager` - DataStore for settings
- `ReportManager` - File I/O for reports
- `BackupManager` - Backup/restore functionality

## Data Flow (Lab 7 - Final Architecture)

### User Data Flow

```
UI (Fragment) observes Flow from ViewModel
    ↑
ViewModel exposes StateFlow/Flow from Repository
    ↑
Repository queries Room Database (Flow queries)
    ↑
Room observes table changes and emits updates
```

### Network Data Flow (AI Advice)

```
User triggers refresh → ViewModel.refreshAdvice()
    → Repository.refreshAdvice()
    → AdviceRepository API call
    → RetrofitClient → API
    → Response → AiAdviceEntity
    → Room.insert()
    → Flow emission
    → UI updates automatically
```

### Focus Mode Flow

```
Home → Details (Safe Args: challengeId, title)
    → FocusMode (Safe Args: challengeId, durationMinutes)
    → CountdownTimer → Completion
    → Navigate back with result
```

## Key Abstractions

### Challenge (Domain Model)

**Location:** `lab-N/app/src/main/java/com/willpower/tracker/models/Challenge.kt`

**Purpose:** Represents habit/challenge entity with sample data generator

**Properties:**
- `id: Int` - Unique identifier
- `title: String` - Challenge name
- `description: String` - What to do
- `category: String` - Осознанность, Дисциплина, Развитие, etc.
- `durationMinutes: Int` - Recommended time
- `streak: Int` - Consecutive days completed
- `isCompleted: Boolean` - Current completion status
- `difficulty: String` - Easy/Medium/Hard
- `technique: String` - Willpower Instinct method

**Sample Data:** `Challenge.getSampleChallenges()` returns 7 pre-defined challenges

### User (Authentication Model)

**Location:** `lab-N/app/src/main/java/com/willpower/tracker/models/User.kt`

**Purpose:** User profile for authentication (educational, no real backend)

**Properties:**
- `name: String` - Display name
- `email: String` - Login identifier
- `password: String` - For validation demonstration

**Parcelable Support:** Used for Intent data passing in Lab 2

### Database Entities (Lab 7)

**TaskEntity:** Main challenge/task data with foreign key to AiAdviceEntity
**AiAdviceEntity:** AI-generated motivational content
**CompletionEntity:** Execution history with timer results

### Adapter Pattern

**Location:** `lab-N/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt`

**Pattern:** Callback-based RecyclerView adapter

**Callbacks:**
- `onItemClick: (Challenge) -> Unit` - Navigate to details
- `onCheckChanged: (Challenge, Boolean) -> Unit` - Toggle completion

**Update Method:** `updateChallenges(newChallenges: List<Challenge>)` for reactive updates

## Entry Points

### MainActivity (Labs 3-7)

**Location:** `lab-N/app/src/main/java/com/willpower/tracker/MainActivity.kt`

**Triggers:** App launch, deep links

**Responsibilities:**
- Host Navigation Component (Labs 4-7)
- Handle Up navigation via `onSupportNavigateUp()`
- Set up ViewBinding (Labs 4-7)

**Lab 3 Specific:** Implements fragment listener interfaces for navigation callbacks

### Individual Activities (Labs 1-2)

**OnboardActivity:** App introduction → SignInActivity
**SignInActivity:** Email/password validation → HomeActivity
**SignUpActivity:** Registration → returns User to SignInActivity
**HomeActivity:** Challenge list display

## Error Handling

**Strategy:** Graceful degradation with fallback values

**Patterns:**

**Network Errors (Lab 5-7):**
```kotlin
try {
    val response = apiService.getAdvice(request)
    if (response.isSuccessful) {
        // Process response
    } else {
        // Use fallback message
    }
} catch (e: Exception) {
    // Show error, use fallback
}
```

**Database Errors:**
- Room with `fallbackToDestructiveMigration()`
- Try-catch for file operations

**UI Errors:**
- Snackbar for network errors
- Toast for validation feedback
- Empty state messages when data unavailable

## Cross-Cutting Concerns

**Logging:**
- Lifecycle logging in BaseActivity (Labs 2-7)
- TAG constant in each class: `private val TAG = "ClassName"`
- Log.d() calls in all lifecycle methods

**Validation:**
- Email: contains `@` symbol
- Password: minimum 4 characters (Labs 1-2)

**Authentication:**
- Educational implementation (no real backend)
- Local credential storage via DataStore (Lab 6-7)
- Navigation flow preserved for demonstration

**State Management:**
- Labs 1-4: In-memory lists, manual adapter updates
- Labs 5-6: DataStore Flow for preferences
- Lab 7: Room Flow + StateFlow in ViewModels (SSOT)

**Threading:**
- Network: Coroutines with suspend functions
- Database: Room Flow on IO dispatcher
- UI: lifecycle-aware collection with `repeatOnLifecycle`

---

*Architecture analysis: 2025-02-14*
