# Codebase Structure

**Analysis Date:** 2025-02-14

## Directory Layout

```
Android_Main/
├── .agent/              # AI agent system (20 agents, 36 skills)
├── apks/                # Built APK files
├── gradle/              # Gradle wrapper (shared)
├── lab-1/               # Activity-based UI
├── lab-2/               # Activity + Lifecycle
├── lab-3/               # Fragment-based navigation
├── lab-4/               # Navigation Component
├── lab-5/               # Network integration
├── lab-6/               # Storage + Focus Mode
├── lab-7/               # MVVM + Room (final state)
├── CLAUDE.md            # Project instructions for Claude
├── LABS.md             # Detailed lab specifications
├── .gitignore
└── gradle.properties
```

## Lab Directory Purposes

**lab-1/**: Activity-based UI foundation
- 4 Activities with RecyclerView
- Intent-based navigation
- Basic validation and sample data

**lab-2/**: Lifecycle + data transfer
- BaseActivity for lifecycle logging
- Intent/Parcelable data passing
- Same structure as lab-1 plus BaseActivity

**lab-3/**: Fragment architecture
- Single MainActivity with FragmentManager
- 4 Fragments replacing activities
- Listener interface pattern for communication

**lab-4/**: Navigation Component
- NavController with XML navigation graph
- Safe Args for type-safe navigation
- ViewBinding for view access
- DetailsFragment addition

**lab-5/**: Networking
- Retrofit client for API calls
- Network models and Repository pattern
- Character data from public API (educational)

**lab-6/**: Storage + Focus Mode
- DataStore for preferences
- File I/O for reports (.txt export)
- SettingsFragment and ReportsFragment
- FocusModeFragment with fullscreen timer

**lab-7/**: MVVM + Room (Complete)
- Room database with Flow queries
- ViewModels with StateFlow
- Repository pattern (SSOT)
- All previous features integrated

## Lab Internal Structure

Each lab follows standard Android project structure:

```
lab-N/
├── app/
│   ├── src/main/
│   │   ├── java/com/willpower/tracker/
│   │   │   ├── activities/      # Labs 1-2: UI activities
│   │   │   ├── fragments/       # Labs 3-7: Fragments
│   │   │   ├── adapters/        # RecyclerView adapters
│   │   │   ├── models/          # Data models
│   │   │   ├── network/         # Labs 5-7: Retrofit layer
│   │   │   │   ├── models/      # Network DTOs
│   │   │   │   └── RetrofitClient.kt
│   │   │   ├── storage/         # Labs 6-7: DataStore, File I/O
│   │   │   ├── database/        # Lab 7: Room entities + DAOs
│   │   │   │   ├── entities/
│   │   │   │   └── dao/
│   │   │   ├── viewmodel/       # Lab 7: ViewModels
│   │   │   ├── repository/      # Labs 5-7: Data repositories
│   │   │   └── MainActivity.kt # Single host (Labs 3-7)
│   │   ├── res/
│   │   │   ├── layout/         # XML layouts
│   │   │   ├── drawable/       # Graphics resources
│   │   │   ├── values/         # Strings, colors, etc.
│   │   │   ├── mipmap-*/       # App icons
│   │   │   └── navigation/    # Labs 4-7: Nav graph
│   │   └── AndroidManifest.xml
│   ├── schemas/               # Lab 7: Room schema exports
│   ├── build.gradle.kts        # App-level build config
│   └── proguard-rules.pro
├── gradle/
│   └── wrapper/
├── build.gradle.kts            # Project-level build config
├── settings.gradle.kts         # Project settings
├── gradle.properties          # Gradle configuration
├── gradlew                   # Gradle wrapper script
└── local.properties           # Local SDK path
```

## Key File Locations

### Entry Points

**Labs 1-2 Activities:**
- `lab-N/app/src/main/java/com/willpower/tracker/activities/OnboardActivity.kt`
- `lab-N/app/src/main/java/com/willpower/tracker/activities/SignInActivity.kt`
- `lab-N/app/src/main/java/com/willpower/tracker/activities/SignUpActivity.kt`
- `lab-N/app/src/main/java/com/willpower/tracker/activities/HomeActivity.kt`

**Labs 3-7 Main Activity:**
- `lab-N/app/src/main/java/com/willpower/tracker/MainActivity.kt`

### Configuration

**Build Configuration:**
- `lab-N/app/build.gradle.kts` - App-level dependencies and config
- `lab-N/build.gradle.kts` - Project-level config
- `lab-N/gradle.properties` - Gradle properties

**Navigation (Labs 4-7):**
- `lab-N/app/src/main/res/navigation/nav_graph.xml` - Navigation graph

**Manifest:**
- `lab-N/app/src/main/AndroidManifest.xml` - App permissions, components

### Core Logic by Lab

**Lab 1-2 - Activities:**
- `lab-N/app/src/main/java/com/willpower/tracker/activities/`
- `lab-N/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt`
- `lab-N/app/src/main/java/com/willpower/tracker/models/Challenge.kt`
- `lab-N/app/src/main/java/com/willpower/tracker/models/User.kt`

**Lab 2 - Lifecycle:**
- `lab-2/app/src/main/java/com/willpower/tracker/activities/BaseActivity.kt`

**Lab 3 - Fragments:**
- `lab-3/app/src/main/java/com/willpower/tracker/fragments/OnboardFragment.kt`
- `lab-3/app/src/main/java/com/willpower/tracker/fragments/SignInFragment.kt`
- `lab-3/app/src/main/java/com/willpower/tracker/fragments/SignUpFragment.kt`
- `lab-3/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt`

**Lab 4 - Navigation + Details:**
- `lab-4/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt`
- `lab-4/app/src/main/res/navigation/nav_graph.xml`

**Lab 5 - Network:**
- `lab-5/app/src/main/java/com/willpower/tracker/network/ApiService.kt`
- `lab-5/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt`
- `lab-5/app/src/main/java/com/willpower/tracker/repository/CharacterRepository.kt`
- `lab-5/app/src/main/java/com/willpower/tracker/network/models/Character.kt`

**Lab 6 - Storage:**
- `lab-6/app/src/main/java/com/willpower/tracker/storage/UserPreferencesManager.kt`
- `lab-6/app/src/main/java/com/willpower/tracker/storage/ReportManager.kt`
- `lab-6/app/src/main/java/com/willpower/tracker/storage/BackupManager.kt`
- `lab-6/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt`
- `lab-6/app/src/main/java/com/willpower/tracker/fragments/ReportsFragment.kt`

**Lab 7 - Complete MVVM:**
- `lab-7/app/src/main/java/com/willpower/tracker/database/AppDatabase.kt`
- `lab-7/app/src/main/java/com/willpower/tracker/database/dao/`
- `lab-7/app/src/main/java/com/willpower/tracker/database/entities/`
- `lab-7/app/src/main/java/com/willpower/tracker/viewmodel/`
- `lab-7/app/src/main/java/com/willpower/tracker/repository/ChallengeRepository.kt`
- `lab-7/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt`
- `lab-7/app/src/main/java/com/willpower/tracker/fragments/FocusModeFragment.kt`

### Testing

**Test Location:** Not implemented in current labs (placeholder structure only)

### Layouts

**Activity Layouts (Labs 1-2):**
- `activity_onboard.xml` - Onboarding screen
- `activity_sign_in.xml` - Sign in form
- `activity_sign_up.xml` - Registration form
- `activity_home.xml` - Challenge list

**Activity Main (Labs 3-7):**
- `activity_main.xml` - Fragment container host

**Fragment Layouts (Labs 3-7):**
- `fragment_onboard.xml` - Onboarding content
- `fragment_sign_in.xml` - Sign in form
- `fragment_sign_up.xml` - Registration form
- `fragment_home.xml` - Challenge list with AI advice
- `fragment_details.xml` - Challenge detail (Labs 4-7)
- `fragment_settings.xml` - Settings screen (Labs 6-7)
- `fragment_reports.xml` - Reports list (Labs 6-7)
- `fragment_focus_mode.xml` - Fullscreen timer (Lab 7)

**Item Layouts:**
- `item_challenge.xml` - RecyclerView item for challenges
- `item_character.xml` - Rick and Morty API item (Lab 5 only)

## Naming Conventions

**Files:**
- Activities: `PascalCaseActivity.kt` (Labs 1-2)
- Fragments: `PascalCaseFragment.kt` (Labs 3-7)
- Adapters: `PascalCaseAdapter.kt`
- Models: `PascalCase.kt`
- Layouts: `snake_case.xml` matching class name

**Directories:**
- Package structure: lowercase, dot-separated
- Resource directories: lowercase plural (layouts, values, drawable)

**IDs (XML):**
- Views: `camelCase` starting with type prefix
  - `rvChallenges` (RecyclerView)
  - `tvTitle` (TextView)
  - `fabAdd` (FloatingActionButton)
  - `cbCompleted` (CheckBox)

**Navigation Actions:**
- `action_from_to_destination` (e.g., `action_home_to_details`)

## Where to Add New Code

**New Fragment (Labs 3-7):**
- Implementation: `lab-N/app/src/main/java/com/willpower/tracker/fragments/NewFragment.kt`
- Layout: `lab-N/app/src/main/res/layout/fragment_new.xml`
- Add to navigation graph: `lab-N/app/src/main/res/navigation/nav_graph.xml`

**New Activity (Labs 1-2 only):**
- Implementation: `lab-N/app/src/main/java/com/willpower/tracker/activities/NewActivity.kt`
- Layout: `lab-N/app/src/main/res/layout/activity_new.xml`
- Register in AndroidManifest.xml

**New ViewModel (Lab 7):**
- Implementation: `lab-7/app/src/main/java/com/willpower/tracker/viewmodel/NewViewModel.kt`
- Use in Fragment with `by viewModels()` delegate

**New Repository:**
- Implementation: `lab-N/app/src/main/java/com/willpower/tracker/repository/NewRepository.kt`
- Instantiate in ViewModel or use singleton pattern

**New Database Entity (Lab 7):**
- Entity: `lab-7/app/src/main/java/com/willpower/tracker/database/entities/NewEntity.kt`
- DAO: `lab-7/app/src/main/java/com/willpower/tracker/database/dao/NewDao.kt`
- Update `AppDatabase.kt` to include new entity
- Increment database version

**New Network Model:**
- DTO: `lab-N/app/src/main/java/com/willpower/tracker/network/models/NewModel.kt`
- Add to ApiService interface
- Handle in Repository

**Utilities:**
- Create: `lab-N/app/src/main/java/com/willpower/tracker/utils/`
- Or add to existing classes if closely related

## Special Directories

**.agent/**: AI agent orchestration system
- Purpose: Modular specialist agents for development assistance
- Generated: No
- Committed: Yes
- Structure: 20 agents, 36 skills, 11 workflows

**apks/**: Built APK files
- Purpose: Store compiled application binaries
- Generated: Yes
- Committed: Yes
- Contents: Debug/release APKs for each lab

**gradle/wrapper/**: Gradle wrapper files
- Purpose: Gradle distribution management
- Generated: Partially (JAR downloaded)
- Committed: Yes
- Required: Yes - for gradlew command

**schemas/** (Lab 7 only): Room database schema exports
- Purpose: Version control for database migrations
- Location: `lab-7/app/schemas/com.willpower.tracker.database.AppDatabase/`
- Generated: Yes (by KSP Room compiler)
- Committed: Yes

**build/**: Build artifacts
- Purpose: Intermediate compilation outputs
- Generated: Yes
- Committed: No (.gitignoreed)

**.gradle/**: Gradle cache
- Purpose: Incremental build state
- Generated: Yes
- Committed: No (.gitignoreed)

---

*Structure analysis: 2025-02-14*
