# Codebase Concerns

**Analysis Date:** 2026-02-14

## Tech Debt

### Lab 5: Wrong API Implementation
**Issue:** Lab 5 uses Rick and Morty API (`https://rickandmortyapi.com/api/`) instead of the specified GLM AI API (`https://open.bigmodel.cn/api/paas/v4/`)
- Files: `/home/snow/Projects/Android_Main/lab-5/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt`, `/home/snow/Projects/Android_Main/lab-5/app/src/main/java/com/willpower/tracker/network/ApiService.kt`
- Impact: Lab 5 does not meet requirements (LABS.md specifies AI advice, not character data)
- Fix approach: Update RetrofitClient baseUrl to BuildConfig.API_BASE_URL and implement ChatCompletion API endpoint

### Lab 7: ViewModelFactory Pattern Not Used Consistently
**Issue:** HomeViewModel uses default factory but DetailsViewModel implements custom Factory pattern
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/viewmodel/HomeViewModel.kt`, `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/viewmodel/DetailsViewModel.kt`
- Impact: Inconsistent patterns, potential confusion for developers
- Fix approach: Use custom factory pattern consistently or migrate to Hilt/Dagger dependency injection

### Lab 7: Challenge Model Duplication
**Issue:** `Challenge` model exists in both `models/` package and is recreated from `TaskEntity`
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/models/Challenge.kt`, `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt` (lines 151-166)
- Impact: Data duplication, potential inconsistencies, unnecessary mapping code
- Fix approach: Use domain models with mapper pattern or use entities directly with ViewModels

### Lab 6-7: Incomplete Backup Data Structure
**Issue:** SettingsViewModel creates backup data but does not include challenge/completion data
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/viewmodel/SettingsViewModel.kt` (lines 84-106)
- Impact: Backup only exports settings, not user data (challenges, completions, AI advice)
- Fix approach: Integrate with Room database to export all user data in backup

## Known Bugs

### Typo in AppDatabase Migration Strategy
**Symptoms:** Database uses destructive migration which loses all data on version change
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/database/AppDatabase.kt` (line 34)
- Trigger: Any database schema version change
- Workaround: None currently implemented
- Fix approach: Implement proper Migration objects or use Room AutoMigration

### Timer Implementation Issues in FocusModeFragment
**Symptoms:** Timer calculation has typo (1000L instead of 1000L for millisecond conversion)
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/FocusModeFragment.kt` (line 40)
- Trigger: User starts focus mode session
- Workaround: Timer still works but may have off-by-one-second issues
- Fix approach: Correct to `60 * 1000L` for proper minute-to-millisecond conversion

## Security Considerations

### API Key Exposed in gradle.properties
**Risk:** API key hardcoded and committed to version control
- Files: `/home/snow/Projects/Android_Main/lab-7/gradle.properties` (line 21)
- Current mitigation: None
- Recommendations:
  1. Remove API key from gradle.properties, add to `.gitignore`
  2. Use local `gradle.properties` or `local.properties` for API key
  3. Document API key acquisition in separate file (not in repo)

### API Key Passed Through BuildConfig
**Risk:** API key embedded in APK, can be extracted through decompilation
- Files: All labs 5-7 `app/build.gradle.kts`
- Current mitigation: None
- Recommendations:
  1. Use backend proxy server for API calls
  2. Implement certificate pinning
  3. Add tamper detection for release builds

### Network Logging in Debug Builds
**Risk:** Full request/response logging may expose sensitive data in logcat
- Files: `/home/snow/Projects/Android_Main/lab-5/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt`, `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt`
- Current mitigation: Logging only enabled in DEBUG builds
- Recommendations: Sanitize logging headers/fields even in debug builds

### File Access Without Runtime Permission Checks
**Risk:** Android 11+ scoped storage requires proper permission handling
- Files: `/home/snow/Projects/Android_Main/lab-6/app/src/main/AndroidManifest.xml`
- Current mitigation: Uses FileProvider (good) but may need updated permissions for Android 13+
- Recommendations: Implement ActivityResultContracts for modern permission handling

## Performance Bottlenecks

### CoroutineScope in Repository Init
**Problem:** ChallengeRepository launches coroutine in init block for data population
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/repository/ChallengeRepository.kt` (lines 22-38)
- Cause: CoroutineScope(Dispatchers.IO).launch in init
- Impact: Database query runs on every repository instantiation, may cause lag
- Improvement path: Move to lazy initialization or explicit seeding function

### Inefficient Flow Collection in HomeFragment
**Problem:** Multiple coroutine launches for each Flow in same scope
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt` (lines 116-147)
- Cause: Separate `launch {}` blocks for each Flow
- Impact: Multiple concurrent coroutines, potential thread overhead
- Improvement path: Use `zip` or `combine` operators to collect related Flows together

### Database Query on Every Task Request
**Problem:** DetailsViewModel uses Flow wrapper around suspend function
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/viewmodel/DetailsViewModel.kt` (lines 36-38, 46-48)
- Cause: Doubly-implemented task loading (Flow + init block)
- Impact: Two database queries for same data on ViewModel creation
- Improvement path: Remove Flow wrapper, use StateFlow directly from DAO

## Fragile Areas

### String-Based Backup Parsing
**Files:** `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/viewmodel/SettingsViewModel.kt` (lines 111-146)
**Why fragile:** Parses freeform text with `contains()`, `substringAfter()`, `substringBefore()`
- Breaks if: Format changes slightly, special characters in fields
- Safe modification: Use JSON serialization with kotlinx.serialization
- Test coverage: No tests for backup/restore functionality

### ChallengeAdapter State Management
**Files:** `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt`
**Why fragile:** Modifies challenge list in-place without proper notification
- Breaks if: Rapid state changes, concurrent modifications
- Safe modification: Use ListAdapter with DiffUtil callback
- Test coverage: No adapter tests

### Navigation Argument Type Mismatch
**Files:** `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt` (line 37)
**Why fragile:** Converts Int to Long for ViewModel, then back to Int
- Breaks if: Task IDs exceed Int range (unlikely with auto-increment)
- Safe modification: Use consistent ID type throughout app (Long recommended for Room)
- Test coverage: No navigation argument tests

## Scaling Limits

### Current capacity: Single-user local data only
**Limit:** No data synchronization, conflicts with multi-device usage
- Scaling path: Implement cloud sync with user accounts
- Database: Room suitable for 10K-100K records, Paging implemented for large lists

### AI Advice Storage Unbounded
**Current capacity:** AiAdvice table grows indefinitely
- Limit: No pagination or cleanup strategy for advice history
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/database/dao/AiAdviceDao.kt`
- Scaling path: Implement retention policy (e.g., keep last 90 days)

## Dependencies at Risk

### Retrofit 2.9.0 (Released 2020)
**Risk:** Security vulnerabilities in old HTTP client
**Impact:** Potential security issues, missing new features
**Migration plan:** Upgrade to 2.11.0+ (latest stable)

### OkHttp 4.12.0
**Risk:** Minor - version is reasonably current
**Impact:** Low risk, but should monitor for updates
**Migration plan:** No urgent action needed

## Missing Critical Features

### Completion Tracking
**Problem:** HomeFragment marks tasks complete but does not persist to database
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt` (lines 75-84)
- Blocks: Streak tracking, weekly reports, progress visualization
- Status: Completion tracking exists in DetailsFragment but not in list view

### Focus Mode Integration
**Problem:** FocusModeFragment does not create CompletionEntity on session completion
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/FocusModeFragment.kt` (lines 100-113)
- Blocks: Accurate time tracking, session history
- Status: Session completes but no database record created

### Reports Fragment Integration
**Problem:** ReportsFragment exists but does not integrate with Room database
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/ReportsFragment.kt`
- Blocks: Weekly reports, data export, progress analysis
- Status: Fragment exists but displays static/placeholder data

### Task Creation Flow
**Problem:** FAB button shows toast instead of opening creation screen
- Files: `/home/snow/Projects/Android_Main/lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt` (line 92-94)
- Blocks: Custom challenge creation, user personalization
- Status: No UI for creating custom tasks

## Test Coverage Gaps

### Unit Tests
**What's not tested:**
- Files: No test files found in any lab
- All ViewModels, Repositories, Database DAOs, and Managers
- Risk: Business logic bugs, edge cases in data handling
- Priority: High (database operations, repository logic, network error handling)

### Integration Tests
**What's not tested:**
- Files: No instrumentation tests found
- Fragment navigation, database migrations, API integration
- Risk: Broken navigation flows, data loss on schema changes
- Priority: Medium (navigation flows are critical)

### UI Tests
**What's not tested:**
- Files: No Espresso tests found
- User flows (onboarding, task completion, focus mode)
- Risk: UI regressions, usability issues
- Priority: Low (manual testing catches major issues)

---

*Concerns audit: 2026-02-14*
