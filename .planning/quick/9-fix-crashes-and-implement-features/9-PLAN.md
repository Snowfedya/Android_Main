---
phase: quick
plan: 9
type: execute
wave: 1
depends_on: []
files_modified:
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/DetailsFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
  - lab-7/app/src/main/java/com/willpower/tracker/viewmodel/DetailsViewModel.kt
  - lab-7/app/src/main/java/com/willpower/tracker/repository/ChallengeRepository.kt
  - lab-7/app/src/main/res/navigation/nav_graph.xml
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/AddEditChallengeFragment.kt (create)
  - lab-7/app/src/main/java/com/willpower/tracker/viewmodel/AddEditViewModel.kt (create)

autonomous: true

must_haves:
  truths:
    - "DetailsFragment loads from Room"
    - "Add/Edit flow works via Navigation Component"
    - "API demo button functions"
---
<objective>
Fix crash, implement Add/Edit flows, and add API demo.
</objective>

<tasks>

<task type="auto">
  <name>Task 1: Fix DetailsFragment Architecture</name>
  <action>
    1. Update `ChallengeRepository` to include `getTaskById(id)` logic (if missing flow).
    2. Create/Update `DetailsViewModel` to load TaskEntity by ID from Repository.
    3. Update `DetailsFragment` to observe ViewModel data instead of using `SampleChallenges`.
  </action>
</task>

<task type="auto">
  <name>Task 2: Implement Add/Edit Feature</name>
  <action>
    1. Create `AddEditChallengeFragment` layout and class.
    2. Create `AddEditViewModel` with `saveTask` and `updateTask` methods.
    3. Update `nav_graph.xml` to include `addEditChallengeFragment` destination.
    4. Connect Home FAB -> AddEdit (Add Mode).
    5. Connect Details "Edit" -> AddEdit (Edit Mode).
  </action>
</task>

<task type="auto">
  <name>Task 3: Implement API Demo</name>
  <action>
    1. Add "Analyze Tasks" button to `HomeFragment` menu or UI.
    2. Update `ApiService` to support a prompt sending task list.
    3. Show result in a Dialog/BottomSheet.
  </action>
</task>

<task type="auto">
  <name>Task 4: GitHub Push</name>
  <action>
    1. Initialize git if needed.
    2. Commit all changes.
    3. Push to remote.
  </action>
</task>

</tasks>
