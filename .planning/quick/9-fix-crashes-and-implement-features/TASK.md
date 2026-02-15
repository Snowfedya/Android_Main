# Task: Fix Crashes, Implement Add/Edit, and API Demo in Lab 7

## Objective
Fix the critical crash in `DetailsFragment` (caused by reading sample data instead of Room DB), implement the missing "Add/Edit Challenge" functionality, and add a feature to demonstrate the AI API capabilities explicitly.

## Issues to Address
1.  **Crash on Item Click:** `DetailsFragment` tries to find the task in `Challenge.getSampleChallenges()` using an ID from the Database. IDs don't match or task isn't in static list.
    *   *Fix:* Connect `DetailsFragment` to `DetailsViewModel` -> `ChallengeRepository` -> `TaskDao`.
2.  **Add Button (FAB):** Currently shows a Toast.
    *   *Fix:* Create `AddEditChallengeFragment`.
3.  **Edit Functionality:** Missing entirely.
    *   *Fix:* Add "Edit" button in `DetailsFragment` -> navigate to `AddEditChallengeFragment`.
4.  **API Demo:** "Refresh" swipe exists, but user wants explicit demo.
    *   *Fix:* Add "AI Analysis" button to generate a summary of current tasks.

## Success Criteria
- [ ] Clicking a task in Home opens Details without crash (loads from DB).
- [ ] FAB on Home opens `AddEditChallengeFragment` (Mode: Add).
- [ ] Saving a new task updates the RecyclerView.
- [ ] "Edit" button on Details opens `AddEditChallengeFragment` (Mode: Edit).
- [ ] Saving edits updates the task in DB and UI.
- [ ] "AI Analysis" button triggers a request to `glm-4.7-flash` and shows result.
- [ ] Changes pushed to GitHub (if repo configured).

## Context
- **Target:** `lab-7`
- **Architecture:** MVVM + Room + Navigation Component
