# Testing Patterns

**Analysis Date:** 2025-02-14

## Test Framework

**Runner:**
- JUnit 4.13.2 for unit testing
- AndroidJUnitRunner for instrumentation tests
- Config: `testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"` in `/home/snow/Projects/Android_Main/lab-7/app/build.gradle.kts`

**Assertion Library:**
- JUnit assertions (`assertThat`, `assertEquals`, `assertTrue`)
- No Truth or AssertJ dependency detected

**Run Commands:**
```bash
# From within any lab directory (lab-1/ through lab-7/):
./gradlew test               # Run all tests
./gradlew connectedAndroidTest  # Instrumentation tests (requires device/emulator)
./gradlew testDebugUnitTest      # Debug unit tests only
```

## Test File Organization

**Location:**
- No test files exist in any lab (lab-1 through lab-7)
- Expected locations (not created):
  - Unit tests: `app/src/test/java/com/willpower/tracker/`
  - Instrumentation tests: `app/src/androidTest/java/com/willpower/tracker/`

**Naming:**
- Standard Android convention (not implemented):
  - Unit tests: `[ClassName]Test.kt` (e.g., `ChallengeRepositoryTest.kt`)
  - Instrumentation tests: `[ClassName]InstrumentedTest.kt` (e.g., `HomeFragmentInstrumentedTest.kt`)

**Structure:**
```
app/src/
├── test/java/com/willpower/tracker/     # (Not created)
│   ├── repository/
│   ├── viewmodel/
│   └── models/
└── androidTest/java/com/willpower/tracker/  # (Not created)
    ├── fragments/
    └── database/
```

## Test Structure

**Suite Organization:**
No test suites exist. Expected pattern based on project structure:

```kotlin
// Expected unit test structure (not implemented)
@RunWith(JUnit4::class)
class ChallengeRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: ChallengeRepository
    private lateinit var mockDatabase: AppDatabase

    @Before
    fun setup() {
        // Initialize test doubles
    }

    @After
    fun teardown() {
        // Clean up
    }

    @Test
    fun `getAllTasks returns empty list when database is empty`() = runTest {
        // Given
        // When
        val result = repository.getAllTasks().first()
        // Then
        assertTrue(result.isEmpty())
    }
}
```

**Patterns observed in codebase:**
- No test setup patterns exist
- No teardown patterns exist
- No assertion patterns exist

## Mocking

**Framework:** None configured (no Mockito, no MockK dependency)

**Patterns:**
No mocking patterns exist. Recommended for future implementation:

```kotlin
// Recommended MockK pattern for ViewModel testing
class HomeViewModelTest {
    private lateinit var mockRepository: ChallengeRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        mockRepository = mockk()
        viewModel = HomeViewModel(ApplicationProvider.getApplicationContext()).apply {
            // Inject mock repository
        }
    }

    @Test
    fun `refreshAdvice updates loading state`() = runTest {
        // Given
        coEvery { mockRepository.refreshAdvice() } returns Result.success(mockAdvice)

        // When
        viewModel.refreshAdvice()

        // Then
        assertEquals(false, viewModel.isLoading.value)
    }
}
```

**What to Mock:**
- Repository dependencies in ViewModels
- DAO dependencies in Repositories
- Network API services
- System services (for UI tests)

**What NOT to Mock:**
- Data classes (POJOs)
- Simple utility functions
- Flow operators (test via emission verification)

## Fixtures and Factories

**Test Data:**
No test fixtures exist. Sample data pattern exists in production code:

**From `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/models/Challenge.kt`:**
```kotlin
data class Challenge(...) {
    companion object {
        fun getSampleChallenges(): List<Challenge> = listOf(
            Challenge(
                id = 1,
                title = "Утренняя медитация",
                description = "10 минут осознанного дыхания перед началом дня",
                category = "Осознанность",
                durationMinutes = 10,
                streak = 5
            ),
            // ... more sample data
        )
    }
}
```

**Location:**
- Sample data in companion objects of model classes
- Not suitable for testing (hardcoded, non-randomized)

**Recommended test fixture approach:**
```kotlin
// Recommended: Create test fixtures in test/Fixtures.kt
object TestFixtures {
    fun createChallenge(
        id: Int = 1,
        title: String = "Test Challenge",
        isCompleted: Boolean = false
    ) = Challenge(
        id = id,
        title = title,
        description = "Test description",
        category = "Test",
        durationMinutes = 30,
        isCompleted = isCompleted
    )
}
```

## Coverage

**Requirements:** None enforced (no JaCoCo configuration detected)

**View Coverage:**
No coverage reports available.

**Generate coverage:**
```bash
# From lab directory:
./gradlew createDebugCoverageReport
# Report will be at: app/build/reports/coverage/
```

## Test Types

**Unit Tests:**
- Not implemented
- Scope and approach (recommended):
  - Repository classes with mocked DAOs
  - ViewModel classes with mocked repositories
  - Utility classes and managers
  - Data class serialization/deserialization

**Integration Tests:**
- Not implemented
- Scope and approach (recommended):
  - Database DAO operations with in-memory database
  - DataStore operations
  - Repository integration with Room and API

**E2E Tests:**
- Not implemented
- Framework: Espresso 3.5.1 available but not configured
- No UI test patterns exist

**Recommended Espresso pattern:**
```kotlin
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickChallenge_navigatesToDetails() {
        // Launch scenario, navigate to HomeFragment
        activityRule.scenario.onActivity { activity ->
            activity.navController.navigate(R.id.homeFragment)
        }

        // Verify challenge item is displayed
        onView(withText("Утренняя медитация"))
            .check(matches(isDisplayed()))

        // Click on challenge
        onView(withId(R.id.rvChallenges))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ChallengeAdapter.ChallengeViewHolder>(
                0,
                click()
            ))

        // Verify navigation to DetailsFragment
        onView(withId(R.id.tvTitle))
            .check(matches(withText("Утренняя медитация")))
    }
}
```

## Common Patterns

**Async Testing:**
Not implemented. Recommended pattern for coroutines:

```kotlin
// Recommended: Use kotlinx-coroutines-test
@RunWith(JUnit4::class)
class ChallengeRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `getAllTasks returns data from database`() = runTest {
        // Given
        val expectedTask = TaskEntity(/*...*/)
        taskDao.insert(expectedTask)

        // When
        val result = repository.getAllTasks().first()

        // Then
        assertEquals(1, result.size)
        assertEquals(expectedTask.title, result[0].title)
    }
}
```

**Error Testing:**
Not implemented. Recommended pattern:

```kotlin
@Test
fun `refreshAdvice returns failure on network error`() = runTest {
    // Given
    coEvery { apiService.getAdvice(any()) } throws IOException("Network error")

    // When
    val result = repository.refreshAdvice()

    // Then
    assertTrue(result.isFailure)
    assertTrue(result.exceptionOrNull() is IOException)
}
```

**Flow Testing:**
Not implemented. Recommended pattern:

```kotlin
@Test
fun `tasksFlow emits updated data`() = runTest {
    // Given
    val task1 = createTask(id = 1, title = "Task 1")
    val task2 = createTask(id = 2, title = "Task 2")

    // When
    val job = launch {
        repository.getAllTasks().collect { tasks ->
            // Then - First emission (empty)
            if (tasks.isEmpty()) {
                taskDao.insert(task1)
            }
            // Then - Second emission (with task1)
            if (tasks.size == 1 && tasks[0].id == 1) {
                taskDao.insert(task2)
            }
            // Then - Third emission (with both)
            if (tasks.size == 2) {
                assertEquals("Task 1", tasks[0].title)
                assertEquals("Task 2", tasks[1].title)
                cancel()
            }
        }
    }
    job.join()
}
```

## Test Dependencies in Build File

**From `/home/snow/Projects/Android_Main/lab-7/app/build.gradle.kts`:**
```kotlin
dependencies {
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

**Recommended additions for comprehensive testing:**
```kotlin
// Unit testing
testImplementation("junit:junit:4.13.2")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
testImplementation("androidx.arch.core:core-testing:2.2.0")
testImplementation("io.mockk:mockk:1.13.5")
testImplementation("com.google.truth:truth:1.1.3")

// Instrumentation testing
androidTestImplementation("androidx.test.ext:junit:1.1.5")
androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
androidTestImplementation("androidx.test:runner:1.5.2")
androidTestImplementation("androidx.test:rules:1.5.0")

// Room testing
androidTestImplementation("androidx.room:room-testing:2.6.1")
```

## Room Testing Conventions

**Recommended pattern for DAO testing:**
```kotlin
@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = db.taskDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `insert and retrieve task`() = runTest {
        val task = TaskEntity(
            title = "Test Task",
            techniqueName = "Test Technique",
            description = "Test Description",
            recommendedDurationMin = 30,
            difficulty = "Easy",
            category = "Test"
        )

        taskDao.insert(task)

        val retrieved = taskDao.getTaskById(task.id)
        assertNotNull(retrieved)
        assertEquals("Test Task", retrieved?.title)
    }
}
```

## UI Testing Conventions

**Recommended Espresso patterns for this codebase:**
```kotlin
// Test navigation from HomeFragment to DetailsFragment
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigateToHome_thenToDetails() {
        // Navigate through onboarding
        onView(withId(R.id.btnGetStarted)).perform(click())

        // Sign in
        onView(withId(R.id.etEmail)).perform(typeText("test@example.com"))
        onView(withId(R.id.etPassword)).perform(typeText("password"))
        onView(withId(R.id.btnSignIn)).perform(click())

        // Wait for home
        onView(withId(R.id.rvChallenges)).check(matches(isDisplayed()))

        // Click first item
        onView(withId(R.id.rvChallenges))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ChallengeAdapter.ChallengeViewHolder>(
                0,
                click()
            ))

        // Verify details screen
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
    }
}
```

## Test Gaps Summary

**Untested areas:**
- All ViewModels (no unit tests)
- All Repositories (no unit tests)
- All DAOs (no integration tests)
- All Fragments (no UI tests)
- All Activities (no UI tests)
- All Managers (UserPreferencesManager, ReportManager, BackupManager)
- Network layer (RetrofitClient, ApiService)
- Navigation flow (onboarding → sign-in → home → details)

**Risk level: HIGH** - No automated test coverage means any changes could introduce undetected regressions.

**Priority recommendations:**
1. Add MockK dependency for unit test mocking
2. Add kotlinx-coroutines-test for async testing
3. Add Room testing dependency for DAO tests
4. Create test helpers for database initialization
5. Write tests for critical business logic (ViewModels)
6. Write UI tests for navigation flows
7. Add JaCoCo for coverage tracking

---

*Testing analysis: 2025-02-14*
