# Roadmap

Project: Willpower Tracker
Status: Planning
Modernization: XML/Views (Labs 1-7) → Jetpack Compose + Clean Architecture

## Overview
This roadmap outlines the transformation of the Willpower Tracker from a legacy lab-based project into a modern, AI-enhanced habit tracking application. The phases are structured to build a solid foundation first, followed by core features, visual polish, and finally the AI integration.

## Phases

### Phase 1: Foundation & Architecture
Goal: Establish a modern project structure with Clean Architecture and Dependency Injection.

**Dependencies:** None

**Requirements:**
- SETUP-01 (Kotlin/Compose Init)
- SETUP-02 (Clean Architecture Layers)
- SETUP-03 (Hilt DI)
- SETUP-04 (Design System)
- DATA-02 (Repository Pattern)
- DATA-03 (Flow/Reactive)

**Success Criteria:**
1. Project compiles with Hilt and Kotlin 2.x.
2. Directory structure clearly separates Data, Domain, and UI layers.
3. A basic "Hello World" screen displays using the project's custom Compose theme.
4. Repository interfaces are defined and ready for implementation.

---

### Phase 2: Core Habit Engine & Persistence
Goal: Deliver the functional core of the app allowing habit management and data persistence.

**Dependencies:** Phase 1

**Requirements:**
- HABIT-01 (Create Habit)
- HABIT-02 (Edit Habit)
- HABIT-03 (Delete Habit)
- HABIT-04 (Check-in)
- HABIT-05 (Undo Check-in)
- DATA-01 (Room DB)
- DATA-04 (DataStore Prefs)
- UI-04 (Add/Edit Screen)

**Success Criteria:**
1. User can successfully create a habit and see it persist after app restart.
2. User can mark a habit as complete for the day and undo it.
3. Database migrations are handled (or schema is stable).
4. Data is stored in Room and exposed via Flow to the UI layer.

---

### Phase 3: Dashboard & Visualization
Goal: Create a compelling user interface for tracking daily progress and long-term trends.

**Dependencies:** Phase 2

**Requirements:**
- UI-01 (Home Dashboard)
- UI-02 (Habit List)
- UI-03 (Detail View)
- PROGRESS-01 (Streaks)
- PROGRESS-02 (Charts/Heatmap)
- PROGRESS-03 (Gamification/Level)

**Success Criteria:**
1. Home screen displays a summary of today's completion status.
2. User can view habit-specific history and streak counts.
3. Visual charts accurately reflect the completion data in the database.
4. UI transitions and animations provide a smooth "modern" feel.

---

### Phase 4: AI Coaching (Gemini Integration)
Goal: Transform the app into an active companion using AI-driven insights.

**Dependencies:** Phase 3

**Requirements:**
- AI-01 (Gemini SDK Setup)
- AI-02 (Weekly Report)
- AI-03 (AI Coach Messages)
- AI-04 (Offline Handling)

**Success Criteria:**
1. App successfully communicates with Gemini API.
2. User receives a personalized report based on their actual habit data.
3. AI Coach provides relevant motivational messages that change based on user performance.
4. App remains fully functional (minus AI features) when offline or API limit is reached.

---

### Phase 5: Retention & Polish
Goal: Ensure long-term user engagement and final quality assurance.

**Dependencies:** Phase 4

**Requirements:**
- NOTIF-01 (Daily Reminders)
- NOTIF-02 (Custom Schedules)

**Success Criteria:**
1. User receives notifications at the scheduled times.
2. Notifications are deep-linked to the relevant habit or dashboard.
3. Final UX pass: error states, empty states, and accessibility check.
4. Performance profiles show smooth 60fps scrolling on target devices.

## Progress Table

| Phase | Description | Status | Progress |
|-------|-------------|--------|----------|
| 1 | Foundation & Architecture | Pending | 0% |
| 2 | Core Habit Engine | Pending | 0% |
| 3 | Dashboard & Visualization | Pending | 0% |
| 4 | AI Coaching (Gemini) | Pending | 0% |
| 5 | Retention & Polish | Pending | 0% |
