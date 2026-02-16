# Roadmap

Project: WillPower Tracker - Professional Lab Development
Status: Active
Focus: Progressive implementation of labs 1-7 per LABS.md specifications

## Overview

This roadmap outlines the professional development of 7 Android labs representing progressive states of a WillPower Tracker habit tracking application. Each lab builds upon the previous one, introducing new Android development concepts and features. The focus is on creating production-ready, well-tested implementations suitable for academic demonstration and defense.

**Key Principles:**
- Each lab is a complete, working state (not partial implementations)
- Progressive learning: later labs don't use techniques from earlier labs
- Professional construction by experienced software engineer
- All visual elements and buttons tested via Android ADB
- Project idea refined for successful defense

## Phases

### Phase 1: Lab 1 - User Interface (Activities)
Goal: Create a functional Android app with 4 Activity screens and RecyclerView for challenge list.

**Dependencies:** None

**Requirements:**
- UI-01 (Onboarding Screen with app description)
- UI-02 (Sign In Screen with email/password validation)
- UI-03 (Sign Up Screen with user registration)
- UI-04 (Home Screen with RecyclerView challenges)
- UI-05 (Material Design 3 styling)
- UI-06 (Challenge item cards with proper layout)

**Plans:**
- [ ] 01-01-PLAN.md — Fix password validation (6→4 chars) and verify LABS.md requirements
- [ ] 01-02-PLAN.md — ADB testing verification on device/emulator

**Success Criteria:**
1. 4 Activities display correctly with transitions between screens
2. RecyclerView shows sample challenge data (from Challenge.getSampleChallenges())
3. All buttons and UI elements are tappable and responsive
4. Email validation requires @ symbol, password requires minimum 4 characters
5. Navigation flows: Onboard → SignIn → (SignUp) → Home
6. Material Design 3 theme applied consistently (colors, typography, elevation)
7. ADB testing: All screens navigable, all buttons functional

---

### Phase 2: Lab 2 - Lifecycle + Data Transfer
Goal: Introduce BaseActivity for lifecycle logging and proper data passing between Activities.

**Dependencies:** Phase 1

**Requirements:**
- LFC-01 (BaseActivity with lifecycle logging)
- LFC-02 (Data transfer via Intent extras)
- LFC-03 (Parcelable implementation for User model)
- LFC-04 (Result API for SignUp → SignIn data return)

**Success Criteria:**
1. All Activities extend BaseActivity
2. Full lifecycle visible in Logcat (onCreate, onStart, onResume, onPause, onStop, onDestroy)
3. Email and username passed from SignUp to SignIn via Intent extras
4. User implements Parcelable for efficient data transfer
5. SignUp returns result (User or null) to SignIn via setResult()
6. ADB testing: Verify data passed correctly between screens, lifecycle callbacks fire in order

---

### Phase 3: Lab 3 - Fragments
Goal: Refactor from multi-Activity to single-Activity architecture with 4 Fragments using FragmentManager.

**Dependencies:** Phase 2

**Requirements:**
- FRG-01 (Single MainActivity as fragment host)
- FRG-02 (OnboardingFragment)
- FRG-03 (SignInFragment with data from SignUp)
- FRG-04 (SignUpFragment)
- FRG-05 (HomeFragment with RecyclerView)
- FRG-06 (FragmentManager transactions)
- FRG-07 (Listener interface for fragment communication)

**Success Criteria:**
1. Only MainActivity exists as entry point
2. 4 Fragments replace previous 4 Activities
3. FragmentManager handles all transactions (add, replace, back stack)
4. SignUp data passed to SignIn via Listener interface (onGetSignedUp(User))
5. Back button works correctly (fragments removed in reverse order)
6. No Activities remain except MainActivity
7. ADB testing: All fragment transactions work, back navigation correct

---

### Phase 4: Lab 4 - Navigation Component + ViewBinding
Goal: Integrate Navigation Component with Safe Args and ViewBinding for type-safe navigation and view access.

**Dependencies:** Phase 3

**Requirements:**
- NAV-01 (Navigation Component integration)
- NAV-02 (Safe Args for type-safe arguments)
- NAV-03 (ViewBinding enabled)
- NAV-04 (DetailsFragment for challenge details)
- NAV-05 (Navigation graph with all destinations)

**Success Criteria:**
1. Navigation graph (nav_graph.xml) defines all 5 destinations
2. Safe Args generates type-safe direction classes
3. ViewBinding replaces all findViewById() calls
4. Navigation from Home to Details passes challengeId and title via Safe Args
5. Up navigation handled automatically by NavController
6. DetailsFragment shows challenge technique description and recommended duration
7. ADB testing: All navigation actions work, arguments received correctly

---

### Phase 5: Lab 5 - Networking (AI API Integration)
Goal: Integrate Retrofit for HTTP requests to AI API (glm-4.7-flash) for motivational advice.

**Dependencies:** Phase 4

**Requirements:**
- NET-01 (Retrofit 2.9.0 dependency)
- NET-02 (OkHttp logging interceptor)
- NET-03 (ApiService interface with suspend functions)
- NET-04 (ChatRequest/ChatResponse models)
- NET-05 (Repository pattern for network operations)
- NET-06 (CoroutineScope for async operations)
- NET-07 (Error handling with fallback messages)

**Success Criteria:**
1. Retrofit client configured with BASE_URL (https://open.bigmodel.cn/api/paas/v4/)
2. API key stored in gradle.properties and injected via BuildConfig
3. "Совет дня" button on HomeFragment triggers API request
4. AI advice displayed on HomeFragment after successful response
5. Network errors handled gracefully (Snackbar/Toast with fallback message)
6. OkHttp logging shows full request/response in DEBUG builds only
7. ADB testing: API request succeeds with valid key, error handling works without internet

---

### Phase 6: Lab 6 - Storage + Focus Mode
Goal: Implement DataStore for preferences, file I/O for weekly reports (.txt export), and fullscreen focus timer.

**Dependencies:** Phase 5

**Requirements:**
- STO-01 (DataStore Preferences)
- STO-02 (UserPreferencesManager with Flow)
- STO-03 (SettingsFragment with preferences UI)
- STO-04 (ReportsFragment for .txt file generation)
- STO-05 (BackupManager for export/import)
- STO-06 (External storage permissions)
- STO-07 (FocusModeFragment fullscreen timer)

**Success Criteria:**
1. User name/email persisted across app restarts via DataStore
2. SettingsFragment allows toggling notifications, setting reminder time
3. ReportsFragment generates weekly .txt report with completion summary
4. Backup creates .json file in external storage, Restore reads and inserts into Room
5. FocusModeFragment shows fullscreen timer with system UI hidden
6. Timer completion saves CompletionEntity to database (Lab 7 integration ready)
7. ADB testing: Preferences persist correctly, files exported/imported, focus mode works

---

### Phase 7: Lab 7 - Room Database + MVVM (Complete SSOT)
Goal: Implement Room database as single source of truth, Repository pattern, Flow-based ViewModels, and Paging for efficient data loading.

**Dependencies:** Phase 6

**Requirements:**
- DB-01 (Room 2.6.1 dependencies)
- DB-02 (AppDatabase with entities, DAOs, TypeConverters)
- DB-03 (TaskEntity, AiAdviceEntity, CompletionEntity)
- DB-04 (Repository pattern with Room + Network)
- DB-05 (ViewModels with StateFlow/Flow)
- DB-06 (Lifecycle-aware Flow collection)
- DB-07 (Paging 3 for large lists)
- DB-08 (Schema export and migrations)

**Success Criteria:**
1. Room database (willpower_database) created with 3 tables
2. All CRUD operations work through DAOs with Flow observation
3. Repository unifies Room data and API calls (SSOT pattern)
4. ViewModels expose StateFlow for UI observation
5. Fragments use repeatOnLifecycle(STARTED) for safe Flow collection
6. Cold start: If database empty, shows sample challenges; populates from API
7. Paging 3 implemented for efficient RecyclerView scrolling
8. Schema exported to app/schemas/ for version control
9. ADB testing: All database operations work, data persists across restarts, Flow updates UI

---

## Progress Table

| Phase | Description | Status | Progress |
|-------|-------------|--------|----------|
| 1 | Lab 1 - User Interface (Activities) | Planned | 0% |
| 2 | Lab 2 - Lifecycle + Data Transfer | Pending | 0% |
| 3 | Lab 3 - Fragments | Pending | 0% |
| 4 | Lab 4 - Navigation + ViewBinding | Pending | 0% |
| 5 | Lab 5 - Networking (AI API) | Pending | 0% |
| 6 | Lab 6 - Storage + Focus Mode | Pending | 0% |
| 7 | Lab 7 - Room + MVVM (SSOT) | Pending | 0% |
