---
phase: quick
plan: 3
type: execute
wave: 1
depends_on: []
files_modified:
  - lab-7/app/src/main/java/com/willpower/tracker/viewmodel/HomeViewModel.kt
  - lab-7/app/src/main/java/com/willpower/tracker/viewmodel/DetailsViewModel.kt
  - lab-7/app/src/main/java/com/willpower/tracker/viewmodel/SettingsViewModel.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt
  - lab-7/app/src/main/res/layout/fragment_home.xml
  - lab-7/app/src/main/res/layout/item_challenge.xml
  - lab-7/app/src/main/res/anim/fragment_fade_enter.xml
  - lab-7/app/src/main/res/anim/fragment_fade_exit.xml
  - lab-7/app/src/main/res/anim/recycler_item_enter.xml
  - lab-7/app/src/main/res/navigation/nav_graph.xml
  - lab-7/app/src/main/res/values/colors.xml
  - lab-7/app/src/main/res/values/strings.xml
  - lab-7/app/src/main/res/values/themes.xml

autonomous: true

must_haves:
  truths:
    - "Fragments use ViewModels to observe Flow data from Room database"
    - "Home screen displays tasks from database with reactive updates"
    - "Details screen shows task details from database (not sample data)"
    - "Settings screen persists data through ViewModel and DataStore"
    - "Fragment transitions have smooth animations"
    - "RecyclerView items animate in when displayed"
    - "UI uses improved Material Design 3 colors and typography"
  artifacts:
    - path: "lab-7/app/src/main/java/com/willpower/tracker/viewmodel/HomeViewModel.kt"
      provides: "ViewModel for Home screen with Flow-based task and advice data"
      contains: "class HomeViewModel : ViewModel()"
    - path: "lab-7/app/src/main/java/com/willpower/tracker/viewmodel/DetailsViewModel.kt"
      provides: "ViewModel for Details screen with task loading and completion"
      contains: "class DetailsViewModel : ViewModel()"
    - path: "lab-7/app/src/main/java/com/willpower/tracker/viewmodel/SettingsViewModel.kt"
      provides: "ViewModel for Settings screen with DataStore integration"
      contains: "class SettingsViewModel : ViewModel()"
    - path: "lab-7/app/src/main/res/anim/fragment_fade_enter.xml"
      provides: "Fragment enter animation"
    - path: "lab-7/app/src/main/res/anim/recycler_item_enter.xml"
      provides: "RecyclerView item animation"
  key_links:
    - from: "HomeFragment.kt"
      to: "HomeViewModel.kt"
      via: "viewModels() delegate and collectFlow/lifecycle.repeatOnLifecycle"
      pattern: "private val viewModel.*by viewModels\\(\\)"
    - from: "HomeViewModel.kt"
      to: "ChallengeRepository"
      via: "Repository Flow methods for reactive data"
      pattern: "fun getAllTasks\\(\\): Flow<List<TaskEntity>>"
    - from: "ChallengeAdapter"
      to: "RecyclerView"
      via: "ListAdapter with DiffUtil for efficient updates"
      pattern: "class ChallengeAdapter.*ListAdapter"
    - from: "nav_graph.xml"
      to: "fragment animations"
      via: "enterAnim, exitAnim, popEnterAnim, popExitAnim attributes"
      pattern: "enterAnim.*@anim/fragment_fade_enter"
---

<objective>
Implement MVVM ViewModels with Flow-based reactive UI for Lab 7, add polish with animations and Material Design 3.

Purpose: Transform Lab 7 from basic fragment implementation to proper MVVM architecture with reactive data streams from Room database using Kotlin Flow, while polishing the UI with smooth transitions, animations, and improved Material Design 3 styling.

Output:
- HomeViewModel, DetailsViewModel, SettingsViewModel with Flow-based reactive data
- Refactored fragments using ViewModels and lifecycle-aware Flow collection
- Fragment transition animations and RecyclerView item animations
- Improved Material Design 3 colors, typography, and spacing
- Buildable and testable Lab 7 APK on device/emulator
</objective>

<execution_context>
@/home/snow/.claude/get-shit-done/workflows/execute-plan.md
@/home/snow/.claude/get-shit-done/templates/summary.md
</execution_context>

<context>
@.planning/STATE.md
@.planning/LABS.md
@lab-7/app/src/main/java/com/willpower/tracker/database/AppDatabase.kt
@lab-7/app/src/main/java/com/willpower/tracker/repository/ChallengeRepository.kt
@lab-7/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt
@lab-7/app/src/main/java/com/willpower/tracker/database/dao/TaskDao.kt
@lab-7/app/src/main/java/com/willpower/tracker/database/dao/AiAdviceDao.kt
@lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
@lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt
@lab-7/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt
@lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt
@lab-7/app/build.gradle.kts
</context>

<tasks>

<task type="auto">
  <name>Task 1: Create ViewModels with Flow-based reactive data</name>
  <files>
    lab-7/app/src/main/java/com/willpower/tracker/viewmodel/HomeViewModel.kt
    lab-7/app/src/main/java/com/willpower/tracker/viewmodel/DetailsViewModel.kt
    lab-7/app/src/main/java/com/willpower/tracker/viewmodel/SettingsViewModel.kt
  </files>
  <action>
    Create viewmodel package and implement three ViewModels:

    **HomeViewModel.kt:**
    - Use ViewModel() factory (no custom factory needed for now)
    - Inject ChallengeRepository via constructor parameter
    - Expose tasks: Flow<List<TaskEntity>> from repository.getAllTasks()
    - Expose latestAdvice: Flow<AiAdviceEntity?> from repository.getLatestAdvice()
    - Implement refreshAdvice() suspend function calling repository.refreshAdvice()
    - Add UI state: _isLoading, _errorMessage using MutableStateFlow
    - Handle cold start: check if database empty, pre-populate if needed

    **DetailsViewModel.kt:**
    - Accept taskId: Long as constructor parameter (will need factory)
    - Expose task: Flow<TaskEntity?> from taskDao.getTaskById(taskId)
    - Implement markComplete() to create CompletionEntity and save via completionDao
    - Add countdown timer state using StateFlow for focus mode

    **SettingsViewModel.kt:**
    - Accept Context for UserPreferencesManager (or inject directly)
    - Expose preference Flows: notificationEnabled, reminderTime, themeMode, etc.
    - Implement update methods: setNotificationEnabled, setReminderTime, etc.
    - Add backup/restore state: _backupStatus, _restoreStatus

    DO NOT use Hilt/DI - use manual ViewModelProvider.Factory in fragments for DetailsViewModel (needs taskId parameter). For HomeViewModel and SettingsViewModel, use viewModels() delegate with default factory.
  </action>
  <verify>
    Compile check: "./gradlew assembleDebug" in lab-7 directory succeeds
  </verify>
  <done>
    ViewModels created with:
    - HomeViewModel exposing tasks and latestAdvice flows
    - DetailsViewModel loading task by ID from database
    - SettingsViewModel wrapping UserPreferencesManager
  </done>
</task>

<task type="auto">
  <name>Task 2: Refactor fragments to use ViewModels with Flow collection</name>
  <files>
    lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
    lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt
    lab-7/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt
    lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt
    lab-7/app/src/main/res/layout/fragment_home.xml
  </files>
  <action>
    Refactor fragments to use ViewModels and collect Flow reactively:

    **HomeFragment:**
    - Create HomeViewModel using "by viewModels()" delegate
    - Replace sample data with Flow collection using lifecycle.repeatOnLifecycle(STARTED) or viewLifecycleOwner.lifecycleScope.launch + collect
    - Update adapter with ListAdapter with DiffUtil (ChallengeEntity diff)
    - Add SwipeRefreshLayout for refresh functionality calling viewModel.refreshAdvice()
    - Remove hardcoded challenges list, use database Flow

    **ChallengeAdapter:**
    - Convert from RecyclerView.Adapter to ListAdapter with AsyncListDiffer
    - Create ChallengeDiffItemCallback for diff comparison
    - Change data type from Challenge model to TaskEntity (or create mapping function)
    - Add submitList() handling

    **DetailsFragment:**
    - Create DetailsViewModel with taskId from navArgs using custom ViewModelProvider.Factory
    - Collect task Flow and display when available
    - Use viewModel.markComplete() instead of Toast placeholder
    - Pass taskId to FocusModeFragment for completion tracking

    **SettingsFragment:**
    - Create SettingsViewModel and collect preference Flows
    - Move DataStore operations from fragment to ViewModel
    - Keep UI interactions in fragment, data operations in ViewModel

    **fragment_home.xml:**
    - Add SwipeRefreshLayout wrapping RecyclerView
    - Add CardView for AI advice display above RecyclerView
  </action>
  <verify>
    - Code compiles without errors
    - ViewModels are properly referenced in fragments
    - Flow collection uses lifecycle-aware patterns
  </verify>
  <done>
    Fragments use ViewModels with:
    - Reactive Flow collection using lifecycle-aware patterns
    - ListAdapter for efficient RecyclerView updates
    - Data operations moved from UI to ViewModel layer
    - SettingsViewModel wraps all DataStore operations
  </done>
</task>

<task type="auto">
  <name>Task 3: Add animations, polish UI, and build/test APK</name>
  <files>
    lab-7/app/src/main/res/anim/fragment_fade_enter.xml
    lab-7/app/src/main/res/anim/fragment_fade_exit.xml
    lab-7/app/src/main/res/anim/recycler_item_enter.xml
    lab-7/app/src/main/res/navigation/nav_graph.xml
    lab-7/app/src/main/res/values/colors.xml
    lab-7/app/src/main/res/values/strings.xml
    lab-7/app/src/main/res/values/themes.xml
    lab-7/app/src/main/res/layout/item_challenge.xml
  </files>
  <action>
    Add smooth animations and Material Design 3 polish:

    **Fragment Animations (res/anim/):**
    - fragment_fade_enter.xml: <alpha> from 0 to 1, duration 300ms, interpolator accelerate
    - fragment_fade_exit.xml: <alpha> from 1 to 0, duration 250ms, interpolator decelerate
    - recycler_item_enter.xml: combination of <translate> Y from 20dp to 0 and <alpha> 0 to 1, duration 250ms, staggered

    **Apply animations in nav_graph.xml:**
    - Add enterAnim, exitAnim, popEnterAnim, popExitAnim to all actions using @anim references
    - Example: app:enterAnim="@anim/fragment_fade_enter" app:exitAnim="@anim/fragment_fade_exit"

    **RecyclerView Item Animation:**
    - Create ItemAnimator with DefaultItemAnimator or custom animator
    - Apply staggered delay in adapter for cascading entrance effect
    - Add in item_challenge.xml: android:stateListAnimator="@animator/item_state_animator"

    **Material Design 3 Polish (colors.xml, themes.xml):**
    - Define proper Material 3 color scheme: primary, secondary, tertiary, error, surface, on-surface variants
    - Use dynamic colors if available (android:allowDynamic="true")
    - Update typography: TextAppearance.Material3 with proper hierarchy
    - Add elevation and rounded corners to cards

    **Strings and Localization:**
    - Consolidate hardcoded strings into strings.xml
    - Add proper string resources for all UI text

    **Build and Test:**
    - Run "./gradlew clean assembleDebug" in lab-7
    - Install APK to connected device/emulator: "./gradlew installDebug"
    - Test: cold start shows data from Room, refresh updates UI, fragment transitions animate smoothly
  </action>
  <verify>
    Build command: "./gradlew assembleDebug" in lab-7 completes successfully
    APK generated: lab-7/app/build/outputs/apk/debug/app-debug.apk exists
    Install test: "./gradlew installDebug" installs APK to device
  </verify>
  <done>
    Lab 7 is complete with:
    - MVVM architecture with ViewModels and Flow reactive streams
    - Smooth fragment transitions and RecyclerView item animations
    - Material Design 3 colors, typography, and spacing
    - Buildable and testable APK that runs on device/emulator
    - Room database as SSOT with cold start/refresh functionality
  </done>
</task>

</tasks>

<verification>
Overall verification checks:
- [ ] ViewModels use Flow from Room database for reactive data
- [ ] Fragments collect Flow using lifecycle-aware patterns (repeatOnLifecycle or lifecycleScope)
- [ ] HomeFragment displays tasks from database, not sample data
- [ ] DetailsFragment loads task by ID from database
- [ ] SettingsFragment uses ViewModel for DataStore operations
- [ ] ChallengeAdapter extends ListAdapter with DiffUtil
- [ ] Fragment transitions animate smoothly
- [ ] RecyclerView items have entrance animation
- [ ] Material Design 3 color scheme applied
- [ ] APK builds successfully: ./gradlew assembleDebug
- [ ] APK installs on device/emulator: ./gradlew installDebug
- [ ] App runs without crashes and shows data from Room
</verification>

<success_criteria>
Measurable completion criteria:
1. ViewModels created for Home, Details, Settings with Flow-based reactive data exposure
2. Fragments refactored to use ViewModels and collect Flow reactively
3. ChallengeAdapter converted to ListAdapter with DiffUtil for efficient updates
4. Fragment transition animations defined in XML and applied in nav_graph
5. RecyclerView item animations with staggered entrance effect
6. Material Design 3 color scheme, typography, and spacing applied
7. Lab 7 APK builds and installs successfully on device/emulator
8. App demonstrates cold start (loads from Room), refresh (updates Room), and reactive UI updates
</success_criteria>

<output>
After completion, create `.planning/quick/3-implement-mvvm-viewmodels-with-flow-base/3-SUMMARY.md`
</output>
