---
phase: quick
plan: 1
type: execute
wave: 1
depends_on: []
files_modified:
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/ReportsFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/FocusModeFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/network/ApiService.kt
  - lab-7/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt
  - lab-7/app/src/main/java/com/willpower/tracker/network/models/AiAdvice.kt
  - lab-7/app/src/main/java/com/willpower/tracker/network/models/ChatRequest.kt
  - lab-7/app/src/main/java/com/willpower/tracker/network/models/ChatResponse.kt
  - lab-7/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt
  - lab-7/app/src/main/java/com/willpower/tracker/storage/UserPreferencesManager.kt
  - lab-7/app/src/main/java/com/willpower/tracker/storage/ReportManager.kt
  - lab-7/app/src/main/java/com/willpower/tracker/storage/BackupManager.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/AppDatabase.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/entities/AiAdviceEntity.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/entities/TaskEntity.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/entities/CompletionEntity.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/dao/AiAdviceDao.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/dao/TaskDao.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/dao/CompletionDao.kt
  - lab-7/app/src/main/res/layout/fragment_details.xml
  - lab-7/app/src/main/res/layout/fragment_settings.xml
  - lab-7/app/src/main/res/layout/fragment_reports.xml
  - lab-7/app/src/main/res/layout/fragment_focus_mode.xml
  - lab-7/app/src/main/res/navigation/nav_graph.xml
  - lab-7/app/src/main/res/values/strings.xml
autonomous: false

must_haves:
  truths:
    - "Lab 7 has DetailsFragment with Safe Args navigation from Home"
    - "Lab 7 has SettingsFragment with DataStore preferences"
    - "Lab 7 has ReportsFragment with .txt export functionality"
    - "Lab 7 has FocusModeFragment with fullscreen timer"
    - "Lab 7 has AI API integration (Retrofit + glm-4.7-flash)"
    - "Lab 7 has Room database with entities and DAOs"
    - "Lab 7 navigation graph includes all 6 fragments"
  artifacts:
    - path: "lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt"
      provides: "Challenge details screen with timer"
      min_lines: 80
    - path: "lab-7/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt"
      provides: "Settings with DataStore storage"
      min_lines: 100
    - path: "lab-7/app/src/main/java/com/willpower/tracker/fragments/ReportsFragment.kt"
      provides: "Weekly report generation and export"
      min_lines: 60
    - path: "lab-7/app/src/main/java/com/willpower/tracker/fragments/FocusModeFragment.kt"
      provides: "Fullscreen focus session with timer"
      min_lines: 80
    - path: "lab-7/app/src/main/java/com/willpower/tracker/network/ApiService.kt"
      provides: "Retrofit interface for glm-4.7-flash"
      contains: "@POST"
    - path: "lab-7/app/src/main/java/com/willpower/tracker/database/AppDatabase.kt"
      provides: "Room database configuration"
      contains: "@Database"
    - path: "lab-7/app/src/main/res/layout/fragment_details.xml"
      provides: "Details screen UI"
      min_lines: 40
  key_links:
    - from: "lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt"
      to: "lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt"
      via: "Safe Args navigation"
      pattern: "actionHomeToDetails"
    - from: "lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt"
      to: "lab-7/app/src/main/java/com/willpower/tracker/network/ApiService.kt"
      via: "Repository pattern"
      pattern: "AdviceRepository"
    - from: "lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt"
      to: "lab-7/app/src/main/java/com/willpower/tracker/fragments/FocusModeFragment.kt"
      via: "Navigation"
      pattern: "actionDetailsToFocusMode"
    - from: "lab-7/app/src/main/java/com/willpower/tracker/network/ApiService.kt"
      to: "lab-7/app/src/main/java/com/willpower/tracker/database/dao/AiAdviceDao.kt"
      via: "Repository for SSOT"
      pattern: "adviceDao.insert"
---

<objective>
Implement all missing Lab 6-7 features in lab-7 directory to complete the WillPower Tracker app.

Purpose: Transform lab-7 from a basic navigation skeleton to a fully-featured habit tracking app with:
- DetailsFragment for viewing and starting challenges
- SettingsFragment with DataStore preferences
- ReportsFragment for weekly report export
- FocusModeFragment for fullscreen focus sessions
- AI API integration using Retrofit
- Room database for local storage (SSOT)

Output: Working lab-7 app with all features from LABS.md specification implemented.
</objective>

<execution_context>
@/home/snow/.claude/get-shit-done/workflows/execute-plan.md
@/home/snow/.claude/get-shit-done/templates/summary.md
</execution_context>

<context>
@lab-6/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt
@lab-6/app/src/main/java/com/willpower/tracker/storage/UserPreferencesManager.kt
@lab-6/app/src/main/java/com/willpower/tracker/storage/ReportManager.kt
@lab-6/app/src/main/java/com/willpower/tracker/storage/BackupManager.kt
@lab-6/app/src/main/res/navigation/nav_graph.xml
@lab-6/app/src/main/res/layout/fragment_settings.xml
@lab-6/app/src/main/res/layout/fragment_reports.xml
@LABS.md
</context>

<tasks>

<task type="auto">
  <name>Task 1: Copy Lab 6 fragments and storage to Lab 7</name>
  <files>
    lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt
    lab-7/app/src/main/java/com/willpower/tracker/fragments/SettingsFragment.kt
    lab-7/app/src/main/java/com/willpower/tracker/fragments/ReportsFragment.kt
    lab-7/app/src/main/java/com/willpower/tracker/fragments/FocusModeFragment.kt
    lab-7/app/src/main/java/com/willpower/tracker/storage/UserPreferencesManager.kt
    lab-7/app/src/main/java/com/willpower/tracker/storage/ReportManager.kt
    lab-7/app/src/main/java/com/willpower/tracker/storage/BackupManager.kt
    lab-7/app/src/main/res/layout/fragment_details.xml
    lab-7/app/src/main/res/layout/fragment_settings.xml
    lab-7/app/src/main/res/layout/fragment_reports.xml
    lab-7/app/src/main/res/layout/fragment_focus_mode.xml
    lab-7/app/src/main/res/navigation/nav_graph.xml
    lab-7/app/src/main/res/values/strings.xml
  </files>
  <action>
    Copy the following files from lab-6 to lab-7 (preserving package structure):

    1. **Fragments** (from lab-6/app/src/main/java/com/willpower/tracker/fragments/):
       - Create DetailsFragment.kt with:
         * Safe Args for challengeId and challengeTitle
         * Display challenge details (title, technique, description, duration, difficulty)
         * "Start Focus Mode" button → navigate to FocusModeFragment
         * "Mark Complete" button → show Toast completion message
         * Back button in toolbar

       - Copy SettingsFragment.kt (already complete in lab-6)

       - Copy ReportsFragment.kt (already complete in lab-6)

       - Create FocusModeFragment.kt with:
         * Fullscreen layout (hide system UI)
         * Countdown timer (25/45/60 min based on user preference)
         * "End Session" button
         * Keep screen on flag
         * Completion save on session end

    2. **Storage** (from lab-6/app/src/main/java/com/willpower/tracker/storage/):
       - Copy UserPreferencesManager.kt (DataStore for notifications, reminder time, theme, user data)
       - Copy ReportManager.kt (generate weekly .txt reports)
       - Copy BackupManager.kt (file export/restore logic)

    3. **Layouts** (from lab-6/app/src/main/res/layout/):
       - Create fragment_details.xml with:
         * Toolbar with back button
         * Challenge title, technique, description TextViews
         * Duration, difficulty, category badges
         * "Start Focus Mode" and "Mark Complete" buttons
         * Material Design 3 card styling

       - Copy fragment_settings.xml, fragment_reports.xml

       - Create fragment_focus_mode.xml with:
         * Fullscreen ConstraintLayout
         * Large countdown timer TextView
         * Challenge title TextView
         * "End Session" MaterialButton
         * System UI hiding

    4. **Update Navigation** (lab-7/app/src/main/res/navigation/nav_graph.xml):
       Add to existing graph:
       ```xml
       <fragment android:id="@+id/detailsFragment" ...>
           <argument android:name="challengeId" app:argType="integer" />
           <argument android:name="challengeTitle" app:argType="string" />
           <action android:id="@+id/action_details_to_focusMode" app:destination="@id/focusModeFragment" />
       </fragment>
       <fragment android:id="@+id/settingsFragment" ... />
       <fragment android:id="@+id/reportsFragment" ... />
       <fragment android:id="@+id/focusModeFragment">
           <argument android:name="challengeId" app:argType="integer" />
           <argument android:name="durationMinutes" app:argType="integer" />
       </fragment>
       ```

    5. **Update HomeFragment** (lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt):
       Replace placeholder buttons with navigation:
       ```kotlin
       binding.btnSettings.setOnClickListener {
           findNavController().navigate(R.id.action_home_to_settings)
       }
       binding.btnReports.setOnClickListener {
           findNavController().navigate(R.id.action_home_to_reports)
       }
       ```

    Use lab-6 implementations as reference but adapt to lab-7's package structure and requirements.
  </action>
  <verify>
    ./gradlew :lab-7:assembleDebug
  </verify>
  <done>
    All fragments compile successfully, navigation graph validates, APK builds without errors.
  </done>
</task>

<task type="auto">
  <name>Task 2: Implement AI API integration (Retrofit + glm-4.7-flash)</name>
  <files>
    lab-7/app/src/main/java/com/willpower/tracker/network/ApiService.kt
    lab-7/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt
    lab-7/app/src/main/java/com/willpower/tracker/network/models/AiAdvice.kt
    lab-7/app/src/main/java/com/willpower/tracker/network/models/ChatRequest.kt
    lab-7/app/src/main/java/com/willpower/tracker/network/models/ChatResponse.kt
    lab-7/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt
  </files>
  <action>
    Create AI API integration for glm-4.7-flash:

    1. **Create network directory structure**:
       mkdir -p lab-7/app/src/main/java/com/willpower/tracker/network/models
       mkdir -p lab-7/app/src/main/java/com/willpower/tracker/repository

    2. **Create data models** (network/models/):
       - ChatRequest.kt:
         ```kotlin
         @Serializable
         data class ChatRequest(
             val model: String = "glm-4.7-flash",
             val messages: List<ChatMessage>
         )
         @Serializable
         data class ChatMessage(
             val role: String,  // "user" or "assistant"
             val content: String
         )
         ```

       - ChatResponse.kt:
         ```kotlin
         @Serializable
         data class ChatResponse(
             val id: String,
             val choices: List<Choice>,
             val created: Long
         )
         @Serializable
         data class Choice(
             val index: Int,
             val message: ChatMessage,
             val finish_reason: String
         )
         ```

       - AiAdvice.kt:
         ```kotlin
         @Serializable
         data class AiAdvice(
             val id: String = UUID.randomUUID().toString(),
             val text: String,
             val tags: List<String> = emptyList(),
             val mood: String = "motivational",
             val source: String = "glm-4.7-flash",
             val dateTime: Long = System.currentTimeMillis()
         )
         ```

    3. **Create Retrofit client** (network/RetrofitClient.kt):
       ```kotlin
       object RetrofitClient {
           private const val BASE_URL = BuildConfig.API_BASE_URL

           private val okHttpClient = OkHttpClient.Builder()
               .addInterceptor(HttpLoggingInterceptor().apply {
                   level = HttpLoggingInterceptor.Level.BODY
               })
               .addInterceptor { chain ->
                   val request = chain.request().newBuilder()
                       .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                       .addHeader("Content-Type", "application/json")
                       .build()
                   chain.proceed(request)
               }
               .build()

           private val json = Json {
               ignoreUnknownKeys = true
               coerceInputValues = true
           }

           val retrofit: Retrofit = Retrofit.Builder()
               .baseUrl(BASE_URL)
               .client(okHttpClient)
               .addConverterFactory(KotlinxSerializationConverterFactory.create(json))
               .build()

           val apiService: ApiService = retrofit.create(ApiService::class.java)
       }
       ```

    4. **Create API service** (network/ApiService.kt):
       ```kotlin
       interface ApiService {
           @POST("chat/completions")
           suspend fun getAdvice(
               @Body request: ChatRequest
           ): Response<ChatResponse>
       }
       ```

    5. **Create Repository** (repository/AdviceRepository.kt):
       ```kotlin
       class AdviceRepository(private val context: Context) {
           private val apiService = RetrofitClient.apiService
           private val prompts = listOf(
               "Give me a short motivational quote about willpower and procrastination in Russian.",
               "Provide a brief tip for building better habits and focus.",
               "Share an encouraging message for someone struggling with productivity."
           )

           suspend fun getRandomAdvice(): Result<AiAdvice> = try {
               val prompt = prompts.random()
               val request = ChatRequest(
                   messages = listOf(ChatMessage(role = "user", content = prompt))
               )
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
       }
       ```

    All network code follows Retrofit 2.9.0 + Kotlin Serialization 1.6.2 patterns as specified in build.gradle.kts.
  </action>
  <verify>
    ./gradlew :lab-7:compileDebugKotlin
  </verify>
  <done>
    API service, models, and repository compile without errors. BuildConfig fields are accessible.
  </done>
</task>

<task type="auto">
  <name>Task 3: Create Room database with entities and DAOs</name>
  <files>
    lab-7/app/src/main/java/com/willpower/tracker/database/AppDatabase.kt
    lab-7/app/src/main/java/com/willpower/tracker/database/entities/AiAdviceEntity.kt
    lab-7/app/src/main/java/com/willpower/tracker/database/entities/TaskEntity.kt
    lab-7/app/src/main/java/com/willpower/tracker/database/entities/CompletionEntity.kt
    lab-7/app/src/main/java/com/willpower/tracker/database/dao/AiAdviceDao.kt
    lab-7/app/src/main/java/com/willpower/tracker/database/dao/TaskDao.kt
    lab-7/app/src/main/java/com/willpower/tracker/database/dao/CompletionDao.kt
    lab-7/app/src/main/java/com/willpower/tracker/repository/ChallengeRepository.kt
  </files>
  <action>
    Create Room database for Lab 7 SSOT (Single Source of Truth):

    1. **Create database directories**:
       mkdir -p lab-7/app/src/main/java/com/willpower/tracker/database/entities
       mkdir -p lab-7/app/src/main/java/com/willpower/tracker/database/dao
       mkdir -p lab-7/app/src/main/java/com/willpower/tracker/repository

    2. **Create Entities** (database/entities/):

       - AiAdviceEntity.kt:
         ```kotlin
         @Entity(tableName = "ai_advice")
         data class AiAdviceEntity(
             @PrimaryKey val id: String,
             val text: String,
             val tags: String,  // JSON array as string
             val mood: String,
             val source: String,
             val dateTime: Long
         )
         ```

       - TaskEntity.kt (maps to Challenge):
         ```kotlin
         @Entity(tableName = "tasks")
         data class TaskEntity(
             @PrimaryKey(autoGenerate = true) val id: Int = 0,
             val title: String,
             val techniqueName: String,
             val description: String,
             val recommendedDurationMin: Int,
             val difficulty: String,
             val category: String,
             val isCustom: Boolean = false,
             val createdAt: Long = System.currentTimeMillis()
         )
         ```

       - CompletionEntity.kt:
         ```kotlin
         @Entity(tableName = "completions",
             foreignKeys = [ForeignKey(
                 entity = TaskEntity::class,
                 parentColumns = ["id"],
                 childColumns = ["taskId"],
                 onDelete = ForeignKey.CASCADE
             )]
         data class CompletionEntity(
             @PrimaryKey(autoGenerate = true) val id: Int = 0,
             val taskId: Int,
             val dateTime: Long,
             val durationMin: Int,
             val result: String,  // "completed" or "interrupted"
             val note: String = ""
         )
         ```

    3. **Create DAOs** (database/dao/):

       - AiAdviceDao.kt:
         ```kotlin
         @Dao
         interface AiAdviceDao {
             @Query("SELECT * FROM ai_advice ORDER BY dateTime DESC LIMIT 1")
             fun getLatestAdvice(): Flow<AiAdviceEntity?>

             @Insert(onConflict = OnConflictStrategy.REPLACE)
             suspend fun insert(advice: AiAdviceEntity)

             @Query("SELECT * FROM ai_advice ORDER BY dateTime DESC")
             fun getAllAdvices(): Flow<List<AiAdviceEntity>>
         }
         ```

       - TaskDao.kt:
         ```kotlin
         @Dao
         interface TaskDao {
             @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
             fun getAllTasks(): Flow<List<TaskEntity>>

             @Query("SELECT * FROM tasks WHERE id = :taskId")
             suspend fun getTaskById(taskId: Int): TaskEntity?

             @Insert(onConflict = OnConflictStrategy.REPLACE)
             suspend fun insert(task: TaskEntity): Long

             @Insert(onConflict = OnConflictStrategy.REPLACE)
             suspend fun insertAll(tasks: List<TaskEntity>)

             @Delete
             suspend fun delete(task: TaskEntity)
         }
         ```

       - CompletionDao.kt:
         ```kotlin
         @Dao
         interface CompletionDao {
             @Query("SELECT * FROM completions ORDER BY dateTime DESC")
             fun getAllCompletions(): Flow<List<CompletionEntity>>

             @Query("SELECT * FROM completions WHERE taskId = :taskId")
             fun getCompletionsForTask(taskId: Int): Flow<List<CompletionEntity>>

             @Insert
             suspend fun insert(completion: CompletionEntity): Long

             @Query("SELECT SUM(durationMin) FROM completions WHERE dateTime >= :startDate")
             suspend fun getTotalFocusTime(startDate: Long): Int?
         }
         ```

    4. **Create Database** (database/AppDatabase.kt):
       ```kotlin
       @Database(
           entities = [AiAdviceEntity::class, TaskEntity::class, CompletionEntity::class],
           version = 1,
           exportSchema = true
       )
       abstract class AppDatabase : RoomDatabase() {
           abstract fun aiAdviceDao(): AiAdviceDao
           abstract fun taskDao(): TaskDao
           abstract fun completionDao(): CompletionDao

           companion object {
               @Volatile private var INSTANCE: AppDatabase? = null

               fun getDatabase(context: Context): AppDatabase =
                   INSTANCE ?: synchronized(this) {
                       Room.databaseBuilder(
                           context.applicationContext,
                           AppDatabase::class.java,
                           "willpower_database"
                       )
                           .fallbackToDestructiveMigration()
                           .build()
                           .also { INSTANCE = it }
                   }
           }
       }
       ```

    5. **Create Repository** (repository/ChallengeRepository.kt):
       ```kotlin
       class ChallengeRepository(context: Context) {
           private val db = AppDatabase.getDatabase(context)
           private val taskDao = db.taskDao()
           private val adviceDao = db.aiAdviceDao()
           private val adviceRepo = AdviceRepository(context)

           init {
               // Pre-populate with sample challenges if empty
               CoroutineScope(Dispatchers.IO).launch {
                   if (taskDao.getAllTasks().first().isEmpty()) {
                       val sampleTasks = Challenge.getSampleChallenges().map { challenge ->
                           TaskEntity(
                               title = challenge.title,
                               techniqueName = challenge.technique,
                               description = challenge.description,
                               recommendedDurationMin = challenge.duration,
                               difficulty = challenge.difficulty,
                               category = challenge.category
                           )
                       }
                       taskDao.insertAll(sampleTasks)
                   }
               }
           }

           fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

           fun getLatestAdvice(): Flow<AiAdviceEntity?> = adviceDao.getLatestAdvice()

           suspend fun refreshAdvice(): Result<AiAdviceEntity> {
               val result = adviceRepo.getRandomAdvice()
               return result.fold(
                   onSuccess = { advice ->
                       val entity = AiAdviceEntity(
                           id = advice.id,
                           text = advice.text,
                           tags = advice.tags.joinToString(","),
                           mood = advice.mood,
                           source = advice.source,
                           dateTime = advice.dateTime
                       )
                       adviceDao.insert(entity)
                       Result.success(entity)
                   },
                   onFailure = { e -> Result.failure(e) }
               )
           }
       }
       ```

    Room configuration uses KSP compiler plugin with schema location specified in build.gradle.kts.
  </action>
  <verify>
    ./gradlew :lab-7:compileDebugKotlin
    ./gradlew :lab-7:assembleDebug
  </verify>
  <done>
    Room database compiles, generates schema files in app/schemas/, APK builds successfully.
  </done>
</task>

<task type="checkpoint:human-verify" gate="blocking">
  <what-built>
    Complete Lab 7 implementation with:
    - 6 fragments: Onboard, SignIn, SignUp, Home, Details, Settings, Reports, FocusMode
    - AI API integration (Retrofit + glm-4.7-flash)
    - Room database with 3 entities and 3 DAOs
    - DataStore preferences for settings
    - Navigation graph with Safe Args
  </what-built>
  <how-to-verify>
    1. Build the APK: `cd lab-7 && ./gradlew assembleDebug`
    2. Install on device/emulator: `adb install lab-7/app/build/outputs/apk/debug/app-debug.apk`
    3. Test the full flow:
       - Onboard → SignIn → Home
       - Click a challenge → DetailsFragment opens
       - Click "Start Focus Mode" → FocusModeFragment opens (fullscreen timer)
       - Go back → Home → Settings → test DataStore preferences
       - Go to Reports → generate weekly report
    4. Check Logcat for:
       - API key from BuildConfig
       - Network requests to glm-4.7-flash
       - Room database operations
    5. Verify app doesn't crash on any screen
  </how-to-verify>
  <resume-signal>Type "approved" if all features work correctly, or describe issues to fix.</resume-signal>
</task>

</tasks>

<verification>
- APK builds without compilation errors
- All fragments are accessible through navigation
- Settings persist after app restart (DataStore working)
- Room database stores and retrieves data
- AI API integration compiles (requires API_KEY in gradle.properties for runtime)
</verification>

<success_criteria>
- Lab 7 has all 6 fragments implemented
- Network layer (Retrofit + models + repository) compiles
- Room database (entities + DAOs + AppDatabase) compiles
- Storage managers copied from lab-6 work in lab-7
- Navigation graph includes all destinations with proper arguments
- APK builds successfully: `lab-7/app/build/outputs/apk/debug/app-debug.apk`
</success_criteria>

<output>
After completion, create `.planning/quick/1-implement-all-missing-lab-6-7-features-r/1-SUMMARY.md`
</output>
