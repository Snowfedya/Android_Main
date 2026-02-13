# Domain Pitfalls

**Domain:** Android Development & Habit Tracking
**Researched:** 2025-05-22

## Critical Pitfalls

### Pitfall 1: Room Schema Mismatch
**What goes wrong:** Changing a data class without updating the database version or migration path.
**Why it happens:** Developer adds a field (e.g., `isArchived`) but forgets `@Database(version = ...)`.
**Consequences:** App crashes on startup (`IllegalStateException`).
**Prevention:** Use Room Migration tests; always increment version; use `KSP` for early error detection.

### Pitfall 2: Context Leaks in Coroutines
**What goes wrong:** Launching a coroutine that captures `this` (Activity/Fragment) and runs longer than the UI.
**Why it happens:** Using `GlobalScope` or a custom scope not tied to the lifecycle.
**Consequences:** Memory leaks, background crashes.
**Prevention:** Use `viewModelScope` and `lifecycleScope`.

## Moderate Pitfalls

### Pitfall 1: Notification Fatigue
**What goes wrong:** Sending too many reminders.
**Prevention:** Allow user-customizable schedules; use AI to determine when reminders are actually effective.

### Pitfall 2: Over-reliance on AI
**What goes wrong:** AI (Gemini) being slow or unavailable, breaking the core habit flow.
**Prevention:** Ensure the app is "Offline-First". AI should be an enhancement, not a requirement for habit tracking.

## Phase-Specific Warnings

| Phase Topic | Likely Pitfall | Mitigation |
|-------------|---------------|------------|
| Data Migration | Data Loss | Never use `fallbackToDestructiveMigration` in prod. |
| UI/Compose | Recomposition Hell | Use `Stable` and `Immutable` annotations; avoid heavy work in `@Composable`. |
| AI Integration | API Rate Limits | Implement local caching of AI insights. |

## Sources

- [Android Performance Pitfalls](https://developer.android.com/topic/performance/vitals)
- [Room Database Best Practices](https://medium.com/androiddevelopers/room-time-27b3867f1402)
