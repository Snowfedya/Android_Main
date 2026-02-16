# Architecture Progression - WillPower Tracker Labs 1-7

**Project:** WillPower Tracker - Progressive Android Development
**Total Architecture Transitions:** 6
**Final Architecture:** MVVM + Room + Flow + Paging
**Last Updated:** 2026-02-17

---

## Executive Summary

The WillPower Tracker project demonstrates a complete architectural evolution path from basic Activity-based UI to enterprise-grade MVVM architecture with reactive data streams. Each lab introduces specific architectural patterns while maintaining functionality, allowing students to understand not just individual concepts but how modern Android architectures evolve.

**Architecture Journey:** Activity-based -> Fragment-based -> Navigation Component -> Network Layer -> Storage Layer -> MVVM + Database

---

## Architecture Evolution Overview

### Visual Architecture Diagram

```
LAB 1: Activity-Based UI
┌─────────────────────────────────────────────────┐
│  Activity (4 screens)                            │
│  ├── OnboardActivity                            │
│  ├── SignInActivity                             │
│  ├── SignUpActivity                             │
│  └── HomeActivity                               │
│       └── RecyclerView                          │
├─────────────────────────────────────────────────┤
│  Manual Intent Navigation                       │
│  findViewById UI Access                         │
│  In-Memory Data Only                            │
└─────────────────────────────────────────────────┘

LAB 2: Activity + Lifecycle
┌─────────────────────────────────────────────────┐
│  BaseActivity (Lifecycle Logging)               │
│       └── All Activities Extend Base            │
├─────────────────────────────────────────────────┤
│  Parcelable Data Transfer                       │
│  Result API (SignUp -> SignIn)                  │
│  Still Multi-Activity Architecture              │
└─────────────────────────────────────────────────┘

LAB 3: Fragment-Based UI
┌─────────────────────────────────────────────────┐
│  Single Activity Host                           │
│       └── Fragment Container                    │
├─────────────────────────────────────────────────┤
│  Fragments (4 screens)                          │
│  ├── OnboardFragment                            │
│  ├── SignInFragment                             │
│  ├── SignUpFragment                             │
│  └── HomeFragment                               │
│       └── RecyclerView                          │
├─────────────────────────────────────────────────┤
│  FragmentManager Transactions                   │
│  Manual Back Stack Management                   │
└─────────────────────────────────────────────────┘

LAB 4: Navigation Component
┌─────────────────────────────────────────────────┐
│  Single Activity + NavGraph                     │
│       └── Navigation Component                  │
├─────────────────────────────────────────────────┤
│  Fragments (5 screens - +Details)               │
│  ├── OnboardFragment                            │
│  ├── SignInFragment                             │
│  ├── SignUpFragment                             │
│  ├── HomeFragment                               │
│  └── DetailsFragment (NEW)                      │
├─────────────────────────────────────────────────┤
│  Safe Args (Type-Safe Navigation)               │
│  ViewBinding (Type-Safe UI Access)              │
│  Declarative Navigation Graph                   │
└─────────────────────────────────────────────────┘

LAB 5: Networking Layer
┌─────────────────────────────────────────────────┐
│  UI Layer (Fragments + NavComponent)            │
├─────────────────────────────────────────────────┤
│  Repository Pattern                             │
│       └── AdviceRepository                      │
├─────────────────────────────────────────────────┤
│  Network Layer (NEW)                            │
│  ├── Retrofit + OkHttp                          │
│  ├── API Service Interface                      │
│  └── Kotlinx Serialization                      │
├─────────────────────────────────────────────────┤
│  External API                                   │
│  └── glm-4.7-flash (AI Mentor)                  │
├─────────────────────────────────────────────────┤
│  Coroutines for Async Operations                │
│  ViewModel + LiveData                           │
└─────────────────────────────────────────────────┘

LAB 6: Storage Layer
┌─────────────────────────────────────────────────┐
│  UI Layer (8 Fragments)                         │
│  ├── OnboardFragment                            │
│  ├── SignInFragment                             │
│  ├── SignUpFragment                             │
│  ├── HomeFragment                               │
│  ├── DetailsFragment                            │
│  ├── FocusModeFragment (NEW - Timer)            │
│  ├── ReportsFragment (NEW - Stats)              │
│  └── SettingsFragment (NEW - Preferences)       │
├─────────────────────────────────────────────────┤
│  Storage Layer (NEW)                            │
│  ├── DataStore Preferences                      │
│  ├── File I/O (Reports .txt)                    │
│  └── BackupManager (JSON Export/Import)         │
├─────────────────────────────────────────────────┤
│  Network Layer                                  │
│  └── AI API Integration                         │
├─────────────────────────────────────────────────┤
│  Coroutines + ViewModel + LiveData              │
└─────────────────────────────────────────────────┘

LAB 7: MVVM + Room Database
┌─────────────────────────────────────────────────┐
│  UI Layer (9 Fragments)                         │
│  ├── All previous fragments                     │
│  └── AddEditChallengeFragment (NEW - CRUD)      │
├─────────────────────────────────────────────────┤
│  ViewModel Layer (NEW)                          │
│  ├── HomeViewModel                              │
│  ├── DetailsViewModel                           │
│  ├── AddEditViewModel                           │
│  └── SettingsViewModel                          │
├─────────────────────────────────────────────────┤
│  Repository Layer (Enhanced)                    │
│  ├── ChallengeRepository (SSOT)                 │
│  └── AdviceRepository (AI)                      │
├─────────────────────────────────────────────────┤
│  Database Layer (NEW)                           │
│  ├── Room Database                              │
│  ├── DAOs (Task, Completion, AiAdvice)          │
│  └── Entities (3 tables)                        │
├─────────────────────────────────────────────────┤
│  Network Layer                                  │
│  └── AI API Integration                         │
├─────────────────────────────────────────────────┤
│  Storage Layer                                  │
│  └── DataStore + File I/O                       │
├─────────────────────────────────────────────────┤
│  Reactive Programming                           │
│  ├── Flow (Data Streams)                        │
│  ├── Paging 3 (Efficient Lists)                 │
│  └── Lifecycle-Aware Collection                 │
└─────────────────────────────────────────────────┘
```

---

## Detailed Architecture Transitions

### Transition 1: Lab 1 -> Lab 2
**Adding Lifecycle Awareness and Data Transfer**

**What Changed:**
- Introduced `BaseActivity` abstract class
- All Activities now extend BaseActivity for lifecycle logging
- Added `@Parcelize` plugin for efficient data transfer
- Implemented `ActivityResultLauncher` for modern result handling
- Created `User` model with Parcelable support

**Before (Lab 1):**
```kotlin
class HomeActivity : AppCompatActivity() {
    // No lifecycle logging
    // Manual Intent data passing with primitives
}
```

**After (Lab 2):**
```kotlin
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }
    // ... other lifecycle methods with logging
}

class HomeActivity : BaseActivity() {
    // Automatic lifecycle logging inherited
}

// Parcelable data model
@Parcelize
data class User(
    val email: String,
    val password: String
) : Parcelable

// Modern Result API
private val signUpResult =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val user = result.data?.getParcelableExtra("user", User::class.java)
        }
    }
```

**Design Decision:** Why BaseActivity?
- Centralized lifecycle logging for debugging
- DRY principle - avoid repeating logging in every Activity
- Foundation for future common functionality (theming, permissions)

**Design Decision:** Why Parcelable?
- More efficient than Serializable (Android-optimized)
- Type-safe data transfer between components
- Required for complex objects in Intents

---

### Transition 2: Lab 2 -> Lab 3
**Multi-Activity to Single-Activity Architecture**

**What Changed:**
- Converted all Activities to Fragments
- Introduced `MainActivity` as single host
- Replaced Intent navigation with FragmentManager transactions
- Added manual back stack management

**Before (Lab 2):**
```kotlin
// Multiple Activities
class OnboardActivity : BaseActivity() {
    private fun navigateToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}
```

**After (Lab 3):**
```kotlin
// Single Activity with Fragments
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardFragment())
                .commit()
        }
    }
}

class OnboardFragment : Fragment() {
    private fun navigateToSignIn() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignInFragment())
            .addToBackStack(null)
            .commit()
    }
}
```

**Design Decision:** Why Single-Activity?
- Modern Android best practice (since 2018)
- Easier state management
- Better performance (fewer Activity launches)
- Foundation for Navigation Component
- Simplifies data sharing between screens

**Design Decision:** Why Fragments?
- Reusable UI components
- Modular screen design
- Easier testing and maintenance
- Supports tablet layouts (multi-pane)

---

### Transition 3: Lab 3 -> Lab 4
**FragmentManager to Navigation Component**

**What Changed:**
- Replaced FragmentManager transactions with Navigation Component
- Added Safe Args for compile-time type safety
- Introduced ViewBinding to replace findViewById
- Added DetailsFragment for challenge details
- Created navigation graph XML

**Before (Lab 3):**
```kotlin
// Manual FragmentManager transactions
parentFragmentManager.beginTransaction()
    .replace(R.id.fragment_container, SignInFragment())
    .addToBackStack(null)
    .commit()

// Manual argument passing
val bundle = Bundle()
bundle.putLong("challengeId", challengeId)
fragment.arguments = bundle

// findViewById in Fragment
val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
tvTitle.text = challenge.title
```

**After (Lab 4):**
```kotlin
// Navigation Component with Safe Args
val action = HomeFragmentDirections
    .actionHomeFragmentToDetailsFragment(challengeId)
findNavController().navigate(action)

// Type-safe argument receiving
private val args: DetailsFragmentArgs by navArgs()
val challengeId = args.challengeId

// ViewBinding (Type-safe UI access)
private var _binding: FragmentDetailsBinding? = null
private val binding get() = _binding!!

override fun onCreateView(...): View {
    _binding = FragmentDetailsBinding.inflate(inflater, container, false)
    return binding.root
}

override fun onViewCreated(...) {
    binding.tvTitle.text = challenge.title
}
```

**Navigation Graph (res/navigation/nav_graph.xml):**
```xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:startDestination="@id/onboardFragment">

    <fragment
        android:id="@+id/homeFragment"
        android:name="com.willpower.tracker.fragments.HomeFragment">
        <action
            android:id="@+id/action_homeFragment_to_detailsFragment"
            app:destination="@id/detailsFragment" />
    </fragment>

    <fragment
        android:id="@+id/detailsFragment"
        android:name="com.willpower.tracker.fragments.DetailsFragment">
        <argument
            android:name="challengeId"
            app:argType="long" />
    </fragment>
</navigation>
```

**Design Decision:** Why Navigation Component?
- Type-safe navigation (compile-time checking)
- Visual navigation graph editor
- Automatic back stack management
- Deep linking support
- Simplifies complex navigation flows
- Handles Up button automatically

**Design Decision:** Why ViewBinding?
- Null-safe (vs findViewById which returns nullable)
- Type-safe (compile-time verification)
- Faster than findViewById
- Cleaner code (no casting)
- Memory efficient (vs ButterKnife)

---

### Transition 4: Lab 4 -> Lab 5
**Static UI to Dynamic Network Data**

**What Changed:**
- Added Retrofit + OkHttp for networking
- Integrated glm-4.7-flash AI API
- Implemented Repository pattern
- Added Kotlinx Serialization for JSON
- Introduced coroutines for async operations
- Created ViewModel and LiveData for lifecycle-aware data

**Before (Lab 4):**
```kotlin
// Static data only
class DetailsFragment : Fragment() {
    private fun displayChallenge(challengeId: Long) {
        val challenge = Challenge.getSampleChallenges()
            .find { it.id == challengeId }
        // Display static challenge data
    }
}
```

**After (Lab 5):**
```kotlin
// Network layer
interface ApiService {
    @POST("chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): ChatResponse
}

object RetrofitClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(KotlinxSerializationConverterFactory())
            .build()
            .create(ApiService::class.java)
    }
}

// Repository pattern
class AdviceRepository(private val api: ApiService) {
    suspend fun getAdvice(challenge: Challenge): Result<AiAdvice> {
        return try {
            val request = ChatRequest(
                model = "glm-4.7-flash",
                messages = listOf(
                    Message(role = "user", content = generatePrompt(challenge))
                )
            )
            val response = api.getChatCompletion(
                "Bearer ${BuildConfig.API_KEY}",
                request
            )
            Result.success(AiAdvice(response.choices.first().message.content))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ViewModel
class DetailsViewModel : ViewModel() {
    private val repository = AdviceRepository(RetrofitClient.api)
    private val _advice = MutableLiveData<AiAdvice>()
    val advice: LiveData<AiAdvice> = _advice

    fun loadAdvice(challenge: Challenge) {
        viewModelScope.launch {
            val result = repository.getAdvice(challenge)
            result.onSuccess { _advice.value = it }
        }
    }
}

// Fragment with ViewModel
class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(...) {
        viewModel.advice.observe(viewLifecycleOwner) { advice ->
            binding.tvAiAdvice.text = advice.quote
        }

        viewModel.loadAdvice(challenge)
    }
}
```

**Design Decision:** Why Repository Pattern?
- Separation of concerns (UI vs data)
- Single source of truth (SSOT)
- Easier testing (mock repository)
- Flexibility (swap data sources)
- Caching layer possibility

**Design Decision:** Why Coroutines?
- Structured concurrency
- Exception propagation
- Lifecycle-aware (viewModelScope)
- Cleaner than callbacks/RxJava
- Native Kotlin support

**Design Decision:** Why Retrofit?
- Type-safe API definitions
- Automatic serialization/deserialization
- Built-in OkHttp integration
- Support for coroutines (suspend functions)
- Easy testing (mock web server)

---

### Transition 5: Lab 5 -> Lab 6
**Volatile Data to Persistent Storage**

**What Changed:**
- Added DataStore Preferences for user settings
- Implemented File I/O for .txt report generation
- Created BackupManager for JSON export/import
- Added FocusModeFragment (fullscreen timer)
- Added ReportsFragment (progress visualization)
- Added SettingsFragment (user preferences)

**Before (Lab 5):**
```kotlin
// All data lost on app close
class HomeFragment : Fragment() {
    private val challenges = mutableListOf<Challenge>()
    // Data only exists in memory
}
```

**After (Lab 6):**
```kotlin
// DataStore for persistent preferences
class UserPreferencesManager(context: Context) {
    private val Context.dataStore by preferencesDataStore("user_preferences")

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications")
    }

    val darkMode: Flow<Boolean> = dataStore.data.map { it[DARK_MODE_KEY] ?: false }

    suspend fun saveDarkMode(enabled: Boolean) {
        dataStore.edit { it[DARK_MODE_KEY] = enabled }
    }
}

// File I/O for reports
class ReportManager(private val context: Context) {
    fun generateWeeklyReport(completions: List<Completion>): File {
        val fileName = "willpower_report_${System.currentTimeMillis()}.txt"
        val file = File(context.getExternalFilesDir(null), fileName)

        val reportContent = buildString {
            appendLine("WillPower Tracker - Weekly Report")
            appendLine("Generated: ${Date()}")
            appendLine()
            completions.forEach { completion ->
                appendLine("${completion.date}: ${completion.title}")
                appendLine("Duration: ${completion.duration} minutes")
                appendLine()
            }
        }

        file.writeText(reportContent)
        return file
    }
}

// BackupManager for JSON export/import
class BackupManager(private val context: Context) {
    private val json = Json { prettyPrint = true }

    fun exportData(challenges: List<Challenge>): File {
        val fileName = "willpower_backup_${System.currentTimeMillis()}.json"
        val file = File(context.getExternalFilesDir(null), fileName)
        val jsonString = json.encodeToString(challenges)
        file.writeText(jsonString)
        return file
    }

    fun importData(file: File): List<Challenge> {
        val jsonString = file.readText()
        return json.decodeFromString<List<Challenge>>(jsonString)
    }
}

// FocusModeFragment with timer
class FocusModeFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private val timerDuration = 25 * 60 * 1000L // 25 minutes

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                showCompletionDialog()
            }
        }.start()
    }
}
```

**Design Decision:** Why DataStore?
- Replacement for SharedPreferences (deprecated)
- Coroutines-based (async by default)
- Type-safe (no class cast exceptions)
- Transactional data consistency
- Supports both key-value and proto data

**Design Decision:** Why File I/O for Reports?
- Simple text format (user-readable)
- Easy sharing (email, messaging)
- No external dependencies
- Universal compatibility
- Archive-friendly

---

### Transition 6: Lab 6 -> Lab 7
**Fragment-Only to MVVM with Room Database**

**What Changed:**
- Introduced Room database with 3 tables
- Implemented Flow for reactive data streams
- Added Paging 3 for efficient list loading
- Created dedicated ViewModels for each screen
- Implemented Repository pattern with SSOT
- Added AddEditChallengeFragment for CRUD operations

**Before (Lab 6):**
```kotlin
// Direct data access in Fragment
class HomeFragment : Fragment() {
    private val challenges = mutableListOf<Challenge>()

    private fun loadChallenges() {
        // Data loaded from API or stored in memory
        challenges.addAll(getSampleChallenges())
        adapter.notifyDataSetChanged()
    }

    private fun addChallenge(challenge: Challenge) {
        challenges.add(challenge)
        adapter.notifyDataSetChanged()
    }
}
```

**After (Lab 7):**
```kotlin
// Room Database
@Database(
    entities = [TaskEntity::class, CompletionEntity::class, AiAdviceEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun completionDao(): CompletionDao
    abstract fun aiAdviceDao(): AiAdviceDao
}

// DAO
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity?
}

// Repository (Single Source of Truth)
class ChallengeRepository(
    private val database: AppDatabase,
    private val api: ApiService
) {
    fun getAllChallenges(): Flow<List<TaskEntity>> =
        database.taskDao().getAllTasks()

    suspend fun insertChallenge(challenge: TaskEntity) =
        database.taskDao().insertTask(challenge)

    suspend fun updateChallenge(challenge: TaskEntity) =
        database.taskDao().updateTask(challenge)

    suspend fun deleteChallenge(challenge: TaskEntity) =
        database.taskDao().deleteTask(challenge)

    suspend fun getChallengeById(id: Long) =
        database.taskDao().getTaskById(id)

    suspend fun refreshFromNetwork(): Result<List<TaskEntity>> {
        // Fetch from API and update database
        return try {
            val response = api.getChallenges()
            val entities = response.map { it.toEntity() }
            entities.forEach { database.taskDao().insertTask(it) }
            Result.success(entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ViewModel
class HomeViewModel(
    private val repository: ChallengeRepository
) : ViewModel() {
    val challenges: Flow<List<TaskEntity>> = repository.getAllChallenges()

    fun deleteChallenge(challenge: TaskEntity) {
        viewModelScope.launch {
            repository.deleteChallenge(challenge)
        }
    }
}

// Fragment with reactive data collection
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ChallengeAdapter()

    override fun onViewCreated(...) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.challenges.collectLatest { challenges ->
                adapter.submitList(challenges)
            }
        }
    }
}

// Paging 3 for efficient large lists
class HomeViewModel(
    private val repository: ChallengeRepository
) : ViewModel() {
    val challenges: Flow<PagingData<TaskEntity>> =
        repository.getAllChallengesPaged()
            .cachedIn(viewModelScope)
}

class ChallengeAdapter : PagingDataAdapter<TaskEntity, ChallengeViewHolder>(
    TaskDiffCallback()
) {
    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }
}
```

**Database Entity Example:**
```kotlin
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val durationMinutes: Int,
    val difficulty: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)
```

**Design Decision:** Why Room?
- Type-safe SQL queries (compile-time verification)
- LiveData/Flow support out of the box
- Migration support (schema versioning)
- Efficient query execution
- SQLite abstraction (no raw SQL needed)
- Paging 3 integration

**Design Decision:** Why Flow?
- Reactive programming model
- Multiple operators (map, filter, collect)
- Lifecycle-aware collection (lifecycleScope)
- Coroutines integration
- Backpressure handling
- Cleaner than LiveData for complex streams

**Design Decision:** Why Paging 3?
- Efficient memory usage (load on demand)
- Built-in caching
- RecyclerView integration
- Supports infinite lists
- Handles loading states
- Retry/error handling built-in

**Design Decision:** Why MVVM?
- Separation of concerns (UI vs logic)
- Testable (ViewModels are framework-agnostic)
- Lifecycle-aware (survives configuration changes)
- Reusable ViewModels
- Data binding ready
- Industry standard for Android

---

## Technology Stack Evolution

### Core Technologies by Lab

| Technology | Lab 1 | Lab 2 | Lab 3 | Lab 4 | Lab 5 | Lab 6 | Lab 7 |
|------------|-------|-------|-------|-------|-------|-------|-------|
| **Architecture** | Activity | Activity + Base | Fragment | Fragment + Nav | Fragment + Repo | Fragment + Storage | MVVM |
| **Navigation** | Intent | Intent | FragmentManager | Navigation Comp | Navigation Comp | Navigation Comp | Navigation Comp |
| **UI Binding** | findViewById | findViewById | findViewById | ViewBinding | ViewBinding | ViewBinding | ViewBinding |
| **Data Transfer** | Intent Primitives | Parcelable | Arguments | Safe Args | Safe Args | Safe Args | Safe Args |
| **Async** | Main Thread | Main Thread | Main Thread | Coroutines | Coroutines | Coroutines | Coroutines |
| **Lifecycle** | Activity | BaseActivity | Fragment | Fragment | Fragment | Fragment | Fragment |
| **Data Source** | In-Memory | In-Memory | In-Memory | In-Memory + API | API + Cache | API + Storage | Database (SSOT) |
| **State Mgmt** | Manual | Manual | Manual | LiveData | LiveData | LiveData | Flow |
| **Persistence** | None | None | None | None | None | DataStore + Files | Room + DataStore |
| **Networking** | None | None | None | None | Retrofit | Retrofit | Retrofit |
| **DI** | Manual | Manual | Manual | Manual | Manual | Manual | Manual |

---

## Design Patterns Introduced

| Pattern | Lab | Purpose |
|---------|-----|---------|
| **Template Method** | Lab 2 | BaseActivity for lifecycle logging |
| **Repository** | Lab 5 | Data layer abstraction |
| **Observer** | Lab 5 | LiveData for reactive UI |
| **MVVM** | Lab 7 | Separation of concerns |
| **DAO** | Lab 7 | Database access abstraction |
| **Singleton** | Lab 7 | Database instance |
| **Factory** | Lab 7 | ViewModelProvider |
| **Strategy** | All | Different serialization strategies |
| **Adapter** | All | RecyclerView adapters |
| **Builder** | Lab 5 | Retrofit, Room builders |

---

## Key Architectural Decisions

### 1. Progressive Complexity
**Decision:** Each lab builds upon the previous one without breaking existing functionality.

**Rationale:**
- Students can run any lab and see a working app
- Git history shows evolution clearly
- Allows comparison between approaches
- Demonstrates that "old" ways still work

### 2. Real-World AI Integration
**Decision:** Integrate actual AI API (glm-4.7-flash) instead of mocking.

**Rationale:**
- Students learn real networking patterns
- Error handling is authentic
- Experience with API keys and rate limits
- Production-like experience

### 3. Material Design 3 Throughout
**Decision:** Maintain MD3 compliance across all labs.

**Rationale:**
- Modern UI standards
- Consistent user experience
- Professional appearance
- Accessibility built-in

### 4. Single Source of Truth (SSOT)
**Decision:** Room database as SSOT in Lab 7.

**Rationale:**
- Prevents data inconsistency
- Simplifies state management
- Enables offline-first architecture
- Easier debugging

### 5. Coroutines over RxJava
**Decision:** Use Kotlin coroutines for async operations.

**Rationale:**
- Native Kotlin support
- Simpler learning curve
- Structured concurrency
- Better integration with Flow

---

## Performance Considerations

### Lab 7 Optimizations

1. **Paging 3:**
   - Loads data on-demand
   - Reduces memory footprint
   - Handles large datasets efficiently

2. **Flow Caching:**
   ```kotlin
   val challenges: Flow<PagingData<TaskEntity>> =
       repository.getAllChallengesPaged()
           .cachedIn(viewModelScope)
   ```

3. **Database Indexing:**
   ```kotlin
   @Entity(tableName = "tasks", indices = [
       Index(value = ["category"]),
       Index(value = ["createdAt"])
   ])
   ```

4. **ViewBinding Null Safety:**
   ```kotlin
   private var _binding: FragmentHomeBinding? = null
   private val binding get() = _binding!!

   override fun onDestroyView() {
       super.onDestroyView()
       _binding = null // Prevents memory leaks
   }
   ```

---

## Testing Strategy Evolution

| Lab | Unit Tests | Instrumentation Tests | UI Tests |
|-----|------------|----------------------|----------|
| 1 | Basic JUnit | Basic Espresso | Manual |
| 2 | Basic JUnit | Basic Espresso | Manual |
| 3 | Fragment Tests | Fragment Espresso | Manual |
| 4 | Navigation Tests | Navigation Espresso | Manual |
| 5 | Repository Mocks | API MockWebServer | Manual |
| 6 | DataStore Tests | File I/O Tests | Manual |
| 7 | ViewModel Tests | DAO Tests | Paging Tests |

---

## Security Considerations

### API Key Management (Labs 5-7)

**Current Implementation:**
```kotlin
// In build.gradle.kts
buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")
```

**Recommendation for Production:**
- Use local.properties (gitignored)
- Or environment variables
- Or secure storage (Android Keystore)
- Never commit API keys to version control

---

## Conclusion

The WillPower Tracker project demonstrates a complete architectural evolution from basic Activity-based UI to enterprise-grade MVVM architecture. Each lab introduces specific patterns while maintaining functionality, allowing students to understand not just individual concepts but how modern Android architectures evolve.

**Key Takeaways:**

1. **Progressive Enhancement:** Each lab builds naturally on the previous one
2. **Real-World Patterns:** All patterns are industry-standard approaches
3. **Production Ready:** Lab 7 could be deployed as-is with minimal changes
4. **Educational Value:** Students see the "why" behind architectural decisions
5. **Complete Journey:** From beginner to expert in 7 comprehensive labs

**Project Status:** Complete and ready for academic demonstration or production deployment.

---

**Document Version:** 1.0
**Last Updated:** 2026-02-17
**Total Lines:** 600+
