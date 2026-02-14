# Coding Conventions

**Analysis Date:** 2025-02-14

## Naming Patterns

**Files:**
- Activities: `[Name]Activity.kt` (e.g., `HomeActivity.kt`, `SignInActivity.kt`)
- Fragments: `[Name]Fragment.kt` (e.g., `HomeFragment.kt`, `DetailsFragment.kt`)
- ViewModels: `[Name]ViewModel.kt` (e.g., `HomeViewModel.kt`, `DetailsViewModel.kt`)
- Adapters: `[Name]Adapter.kt` (e.g., `ChallengeAdapter.kt`, `CharacterAdapter.kt`)
- Models: Domain entities (e.g., `Challenge.kt`, `User.kt`) in `models/` package
- Entities: Room database entities (e.g., `TaskEntity.kt`, `AiAdviceEntity.kt`) in `database/entities/` package
- DAOs: `[Name]Dao.kt` (e.g., `TaskDao.kt`, `AiAdviceDao.kt`) in `database/dao/` package
- Repositories: `[Name]Repository.kt` (e.g., `ChallengeRepository.kt`, `AdviceRepository.kt`) in `repository/` package
- Managers: `[Name]Manager.kt` (e.g., `UserPreferencesManager.kt`, `ReportManager.kt`, `BackupManager.kt`) in `storage/` package
- Layout XML: `fragment_[name].xml` or `activity_[name].xml` in `res/layout/`

**Functions:**
- Use `camelCase` for function names
- Private functions use `camelCase` with descriptive names: `setupRecyclerView()`, `observeViewModel()`, `formatDuration()`
- Lifecycle callbacks override standard Android names: `onCreate()`, `onViewCreated()`, `onDestroyView()`

**Variables:**
- Private properties: `camelCase` with `private` modifier: `private val TAG`, `private var _binding`
- Mutable backing properties: underscore prefix: `private val _isLoading = MutableStateFlow(false)`
- Public immutable: read-only name without underscore: `val isLoading: StateFlow<Boolean>`

**Types:**
- Data classes for models: `data class Challenge(...)`
- Interfaces for callbacks: `interface OnboardListener { fun onGetStartedClicked() }`
- Abstract classes for shared behavior: `abstract class BaseActivity : AppCompatActivity()`

## Code Style

**Formatting:**
- No explicit formatter configured (no `.editorconfig`, no `.prettierrc`)
- 4-space indentation in Kotlin files
- 2-space indentation in XML layout files
- Opening brace on same line for class/function declarations
- Trailing commas in multi-parameter lists (not consistently applied)

**Linting:**
- No explicit linting configuration found (no `.eslintrc`, no `detekt.yml`)
- Android Lint is used implicitly via Android Gradle Plugin

## Import Organization

**Order:**
1. Android framework imports (`android.os.Bundle`, `android.view.LayoutInflater`)
2. AndroidX libraries (`androidx.appcompat.app.AppCompatActivity`, `androidx.fragment.app.Fragment`)
3. Third-party libraries (`com.google.android.material.*`)
4. Project imports (`com.willpower.tracker.*`)

**Path Aliases:**
- No path aliases configured
- Direct package imports used throughout

**Example from `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt`:**
```kotlin
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.willpower.tracker.R
import com.willpower.tracker.adapters.ChallengeAdapter
// ... more imports
```

## Error Handling

**Patterns:**
- `try-catch` blocks with `Result<T>` return types for repository operations
- Network errors return `Result.failure(exception)`
- Database operations use `suspend` functions with exception handling

**Example from `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt`:**
```kotlin
suspend fun getRandomAdvice(): Result<AiAdvice> = try {
    val prompt = prompts.random()
    val request = ChatRequest(messages = listOf(ChatMessage(role = "user", content = prompt)))
    val response = apiService.getAdvice(request)
    if (response.isSuccessful && response.body() != null) {
        val adviceText = response.body()!!.choices.firstOrNull()?.message?.content
            ?: "Сделай 2 минуты прямо сейчас!"
        Result.success(AiAdvice(text = adviceText))
    } else {
        Result.success(AiAdvice(text = "Сделай 2 минуты прямо сейчас!"))
    }
} catch (e: Exception) {
    Result.failure(e)
}
```

**ViewModel error state pattern:**
```kotlin
private val _errorMessage = MutableStateFlow<String?>(null)
val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

// In repository call:
result.onFailure { exception ->
    _errorMessage.value = "Ошибка загрузки совета: ${exception.message}"
}
```

## Logging

**Framework:** `android.util.Log`

**Patterns:**
- TAG constant defined at top of each class: `private val TAG = "ClassName"`
- Lifecycle logging in Activities/Fragments for debug tracking
- Log.d() for lifecycle callbacks, data updates
- TAG uses class name directly or via `this::class.java.simpleName`

**Example from `/home/snow/Projects/Android_Main/lab-2/app/src/main/java/com/willpower/tracker/activities/BaseActivity.kt`:**
```kotlin
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }
    // ... other lifecycle methods
}
```

## Comments

**When to Comment:**
- Class-level KDoc comments for Fragment and ViewModel classes
- Function-level KDoc for public API methods in ViewModels, Repositories
- Inline comments for complex logic (e.g., timer state management)
- TODO/FIXME comments present but not extensively used

**JSDoc/TSDoc:**
- KDoc style used for class documentation:
```kotlin
/**
 * HomeFragment displays list of challenges and AI advice.
 * Uses HomeViewModel with Flow-based reactive data from Room database.
 */
class HomeFragment : Fragment() {
```

**Function documentation:**
```kotlin
/**
 * Start focus mode countdown timer
 * @param durationMinutes Duration in minutes for focus session
 */
fun startTimer(durationMinutes: Int) {
```

## Function Design

**Size:** Functions typically 10-30 lines. Single-responsibility functions like `setupRecyclerView()`, `observeViewModel()`, `formatDuration()`

**Parameters:**
- Constructor parameters for data classes
- Lambda callbacks for adapter item interactions: `onItemClick: (Challenge) -> Unit`
- ViewModels receive `Application` context via constructor
- Fragments receive navigation arguments via `navArgs()` delegate

**Return Values:**
- Repository suspend functions return `Result<T>`
- DAO functions return `Flow<T>` or suspend single values
- ViewModel StateFlow for UI state observation
- Adapter functions return `Unit` (fire-and-forget) or `Int` for size

## Module Design

**Exports:**
- Public classes exported by default (Kotlin)
- Internal DAOs accessed via Database singleton
- ViewModels exposed via factory pattern when parameters needed

**Barrel Files:**
- No barrel files found
- Direct imports used throughout

**Package structure (from `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/`):**
```
com.willpower.tracker/
├── MainActivity.kt (single entry point)
├── adapters/          # RecyclerView adapters
├── fragments/          # UI fragments
├── models/             # Domain models (non-database)
├── viewmodel/          # ViewModels
├── repository/          # Data repositories
├── network/            # API clients and DTOs
├── storage/            # Local persistence (DataStore, File I/O)
└── database/           # Room database
    ├── entities/        # Room entities
    └── dao/            # Data Access Objects
```

## ViewBinding Conventions

**Pattern:**
- Null-safe backing property: `private var _binding: FragmentHomeBinding? = null`
- Non-null accessor: `private val binding get() = _binding!!`
- Inflate in onCreateView: `_binding = FragmentHomeBinding.inflate(inflater, container, false)`
- Nullify in onDestroyView: `_binding = null`

**Example:**
```kotlin
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(...): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

## Navigation Component Conventions

**Safe Args Pattern:**
- Receive arguments via `navArgs()` delegate: `private val args: DetailsFragmentArgs by navArgs()`
- Navigate with generated Directions: `findNavController().navigate(action)`
- Pass primitive types and strings through navigation graph

**Example:**
```kotlin
private val args: DetailsFragmentArgs by navArgs()

private fun setupToolbar() {
    binding.toolbar.title = args.challengeTitle
}

// Navigate with action
val action = HomeFragmentDirections.actionHomeToDetails(
    challengeId = challenge.id,
    challengeTitle = challenge.title
)
findNavController().navigate(action)
```

## Room Database Conventions

**Entity Pattern:**
- Use `@Entity` annotation with `tableName` parameter
- `@PrimaryKey(autoGenerate = true)` for auto-incrementing IDs
- `@ForeignKey` for relationships
- `@ColumnInfo` for custom column names when needed

**DAO Pattern:**
- `@Dao` annotation on interface
- `@Query` for custom SQL
- `@Insert(onConflict = OnConflictStrategy.REPLACE)` for upserts
- Return `Flow<T>` for observable queries
- Use `suspend` for write operations

**Example from `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/database/dao/TaskDao.kt`:**
```kotlin
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity): Long
}
```

## Material Design Implementation

**Theme:**
- Material Design 3 (Material 3) as base theme
- Custom theme: `Theme.WillPowerTracker` extends `Theme.Material3.Light.NoActionBar`
- Theme attributes used for colors: `?attr/colorPrimary`, `?attr/colorSurface`

**Components used:**
- `MaterialCardView` for card containers
- `MaterialButton` and outlined button variant
- `MaterialCheckBox` for checkboxes
- `Chip` for category tags
- `FloatingActionButton` for primary actions
- `MaterialToolbar` for app bars
- `SwipeRefreshLayout` for pull-to-refresh
- Material 3 TextAppearance styles: `@style/TextAppearance.Material3.TitleMedium`

**Layout pattern from `/home/snow/Projects/Android_Main/lab-7/app/src/main/res/layout/item_challenge.xml`:**
```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardBackgroundColor="?attr/colorSurface"
    app:cardCornerRadius="12dp"
    app:cardElevation="2dp">
    <!-- Content -->
</com.google.android.material.card.MaterialCardView>
```

## Coroutines and Flow Conventions

**Pattern:**
- Use `lifecycleScope.launch` for Fragment coroutines
- Use `viewModelScope.launch` for ViewModel coroutines
- Use `repeatOnLifecycle(Lifecycle.State.STARTED)` for Flow collection
- Use `StateFlow` for UI state, `Flow` for data streams

**Example from `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt`:**
```kotlin
viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        launch {
            viewModel.tasks.collect { tasks ->
                updateChallengesList(tasks)
            }
        }
        launch {
            viewModel.latestAdvice.collect { advice ->
                updateAdviceDisplay(advice)
            }
        }
    }
}
```

## DataStore Conventions

**Extension property pattern:**
```kotlin
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
```

**Key definition in companion object:**
```kotlin
companion object {
    val USER_NAME = stringPreferencesKey("user_name")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}
```

**Flow-based reading:**
```kotlin
val userNameFlow: Flow<String> = context.dataStore.data.map { preferences ->
    preferences[USER_NAME] ?: ""
}
```

**Suspend function for writing:**
```kotlin
suspend fun saveUserCredentials(name: String, email: String) {
    context.dataStore.edit { preferences ->
        preferences[USER_NAME] = name
        preferences[USER_EMAIL] = email
    }
}
```

---

*Convention analysis: 2025-02-14*
