# WillPower Tracker - Professional Lab Development

## What This Is

A progressive Android development learning environment with 7 labs (lab-1 through lab-7) representing evolution stages of a WillPower Tracker habit/challenge tracking app. Each lab is a complete, working state that builds upon the previous one, demonstrating professional Android development practices suitable for academic demonstration and defense.

**Project Type:** Educational Android Development (7 Labs)

## Core Value

Create 7 progressively complex Android labs that demonstrate mastery of:
1. **Activity-based UI** → Fragment-based navigation → Navigation Component → Networking → Storage → Room + MVVM
2. **Professional code quality** following Android best practices
3. **Complete ADB testing** of all UI elements and navigation flows
4. **Academic readiness** for successful defense and demonstration

## Requirements

### Validated
- ✓ Progressive lab architecture (lab-1 → lab-7) maintained

### Active
- [ ] **Lab 1:** 4 Activities with RecyclerView, Material Design 3
- [ ] **Lab 2:** BaseActivity lifecycle, Intent/Parcelable data transfer
- [ ] **Lab 3:** Single Activity with 4 Fragments, FragmentManager
- [ ] **Lab 4:** Navigation Component, Safe Args, ViewBinding, DetailsFragment
- [ ] **Lab 5:** Retrofit + glm-4.7-flash AI API, error handling
- [ ] **Lab 6:** DataStore preferences, file I/O, Settings/Reports/FocusModeFragment
- [ ] **Lab 7:** Room database, Flow, Repository pattern, MVVM ViewModels, Paging
- [ ] **Professional code quality** (clean architecture, proper error handling, Material Design 3)
- [ ] **ADB testing** of all screens, buttons, and navigation flows
- [ ] **Academic demonstration** ready (smooth UI transitions, complete features, no crashes)

### Out of Scope
- **Jetpack Compose modernization** — Focus on professional implementation of existing XML/Views architecture
- **Clean Architecture with Hilt** — Each lab uses appropriate architecture for its learning stage
- **Multi-device/cloud sync** — Single-user local application per LABS.md
- **OAuth authentication** — Educational email/password implementation only
- **Complex animations** — Material Design 3 transitions sufficient

## Context

### Educational Purpose
This is a course project for Android development with 7 labs:
- Each lab introduces new Android development concepts
- Labs represent progressive states of a complete application (WillPower Tracker)
- Designed for demonstration and defense to instructor

### Project Philosophy
- **Progressive complexity:** Later labs never use techniques from earlier labs (prevents jumping ahead)
- **Professional implementation:** Code quality expected of experienced software engineer
- **Complete functionality:** All buttons, UI elements must work (tested via ADB)
- **Academic polish:** Clean UI, smooth transitions, proper error handling

### Technical Environment
- **Language:** Kotlin 2.0.21
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Build System:** Gradle 8.9
- **IDE:** Android Studio

### Domain Model
Based on "The Willpower Instinct" methodology (Kelly McGonigal):
- **Challenge:** Habits with technique name, description, category, duration, difficulty
- **Completion:** Execution history with timer results and notes
- **AI Advice:** Motivational quotes/tips from glm-4.7-flash API
- **Focus Sessions:** Timer with fullscreen mode for dedicated work

## Constraints
- **Progressive Labs:** Each lab builds on previous ones (lab-1 → lab-2 → ... → lab-7)
- **No Forward Features:** Lab N cannot use techniques introduced in Lab N+1
- **Must Pass ADB:** All UI elements tested via Android Debug Bridge
- **Academic Timeline:** Labs completed per course schedule
- **Language:** Russian UI, English code comments and variable names

## Key Decisions

| Decision | Rationale | Outcome |
|----------|-----------|---------|
| 7-Lab Progressive Model | Matches LABS.md structure | ✓ Good |
| XML/Views Architecture | Educational focus, not Compose | ✓ Good |
| Educational Auth Only | No real backend, local storage | ✓ Good |
| Material Design 3 | Modern Android UI standard | — Pending |

---

*Last updated: 2026-02-14 after ROADMAP restructuring for lab-focused development*
