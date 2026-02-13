# Architecture Patterns

**Domain:** Android Mobile Application
**Researched:** 2025-05-22

## Recommended Architecture: Clean Architecture + MVI

The system follows a layered approach to ensure testability and separation of concerns.

### Component Boundaries

| Component | Responsibility | Communicates With |
|-----------|---------------|-------------------|
| **UI Layer (Compose)** | Rendering UI and handling user intent. | ViewModel |
| **Domain Layer** | Business logic (Use Cases). Pure Kotlin. | Repository Interface |
| **Data Layer** | Data sourcing (Local Room, Remote API). | Repository Implementation |

### Data Flow (UDF)

1. User interacts with UI (e.g., clicks "Complete Habit").
2. UI sends an **Intent** to the ViewModel.
3. ViewModel executes a **Use Case**.
4. Use Case updates the **Repository**.
5. Repository updates **Room/API** and emits a new **State**.
6. UI observes the state and re-composes.

## Patterns to Follow

### Pattern 1: Repository Pattern
Centralize data access.
```kotlin
interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    suspend fun completeHabit(id: String)
}
```

### Pattern 2: StateFlow for State Management
Ensure a single source of truth for UI state.
```kotlin
private val _uiState = MutableStateFlow(HabitUiState())
val uiState: StateFlow<HabitUiState> = _uiState.asStateFlow()
```

## Anti-Patterns to Avoid

### Anti-Pattern 1: God ViewModel
**What:** Putting all logic (validation, DB calls, string formatting) in one ViewModel.
**Why bad:** Impossible to test, violates Single Responsibility.
**Instead:** Use UseCases for logic and distinct ViewModels per feature.

### Anti-Pattern 2: Passing Context to ViewModel
**What:** Storing an Activity reference in ViewModel.
**Why bad:** Causes memory leaks on rotation.
**Instead:** Use `applicationContext` via Hilt if absolutely necessary, or better yet, handle UI-specific logic in the UI layer.

## Scalability Considerations

| Concern | At 100 habits | At 10K habit entries |
|---------|--------------|-------------------|
| Database | Standard SQLite | Needs Indexing on `date` columns. |
| UI List | Column / Column | LazyColumn (Compose) for performance. |
| AI | Single prompt | Prompt caching/batching for cost efficiency. |

## Sources

- [Guide to App Architecture (Android Developers)](https://developer.android.com/topic/architecture)
- [Clean Architecture by Robert C. Martin]
