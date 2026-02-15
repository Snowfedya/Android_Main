---
phase: quick
plan: 6
type: execute
wave: 1
depends_on: []
files_modified:
  - lab-7/app/src/main/java/com/willpower/tracker/database/AppDatabase.kt
  - lab-7/app/src/main/java/com/willpower/tracker/repository/ChallengeRepository.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/dao/TaskDao.kt
  - lab-7/app/src/main/java/com/willpower/tracker/database/dao/AiAdviceDao.kt

autonomous: true

must_haves:
  truths:
    - "Lab 7 uses Room as a Single Source of Truth (SSOT)"
    - "Database pre-population logic is active in Repository init"
    - "UI updates automatically when database changes via Flow"
---
<objective>
Verify and finalize Lab 7 Room database integration for academic readiness.
</objective>

<tasks>

<task type="auto">
  <name>Task 1: Verify DAOs and Entities</name>
  <action>
    1. Check `TaskDao.kt`, `AiAdviceDao.kt`, and `CompletionDao.kt` for necessary CRUD operations (Flow return types for queries).
    2. Verify `@Entity` classes have correct primary keys and fields.
  </action>
</task>

<task type="auto">
  <name>Task 2: Verify Repository Pre-population</name>
  <action>
    1. Ensure `ChallengeRepository` init block correctly inserts sample challenges only when the database is empty.
    2. Check that `refreshAdvice()` correctly persists API results to the database.
  </action>
</task>

<task type="auto">
  <name>Task 3: Verify Schema Export</name>
  <action>
    1. Check if `lab-7/app/schemas/` exists and contains the database schema.
    2. If missing, ensure build triggers schema export.
  </action>
</task>

<task type="auto">
  <name>Task 4: Final Build Verification</name>
  <action>
    Build lab-7 using Java 17.
  </action>
</task>

</tasks>
