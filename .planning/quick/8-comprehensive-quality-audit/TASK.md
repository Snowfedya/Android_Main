# Task: Comprehensive Quality Audit (Labs 1-7)

## Objective
Systematically verify every functional requirement, UI element, and architectural decision across all 7 labs on a real device/emulator.

## Audit Checklist

### Lab 1: UI & Basic Navigation
- [ ] 4 Activities exist and launch.
- [ ] Onboard → SignIn → Home flow works.
- [ ] RecyclerView on Home displays data correctly.
- [ ] MD3 styling (colors, shapes) is consistent.

### Lab 2: Lifecycle & Data Transfer
- [ ] Lifecycle logs (onCreate to onDestroy) are present in Logcat for all screens.
- [ ] SignUp → SignIn data return (username/email) is displayed on SignIn screen.
- [ ] Parcelable User model is used.

### Lab 3: Fragments
- [ ] MainActivity is the only Activity.
- [ ] Navigation via FragmentManager works (no crashes).
- [ ] Fragment transactions are correct (add to backstack).

### Lab 4: Navigation Component
- [ ] NavController handles all transitions.
- [ ] Safe Args passes challengeId to DetailsFragment.
- [ ] DetailsFragment UI displays technique name and duration.

### Lab 5: Networking (WillPower AI)
- [ ] "Daily Advice" card displays AI response from glm-4.7-flash.
- [ ] Retrofit headers (API Key) and JSON serialization work.
- [ ] Error handling (offline mode) works gracefully.

### Lab 6: Storage & Focus Mode
- [ ] Settings persist across app restarts (DataStore).
- [ ] Weekly report (.txt) is generated in external storage.
- [ ] Focus Mode (Fullscreen timer) launches and completes.
- [ ] Backup/Restore logic works.

### Lab 7: Room & SSOT
- [ ] Data is loaded from Room (Cold Start).
- [ ] Flow updates UI automatically when DB changes.
- [ ] SSOT architecture: Network updates DB -> UI observes DB.

## Success Criteria
- [ ] Every lab builds, installs, and passes its specific functional check.
- [ ] Successful authorization (reached Home screen).
- [ ] Visual screenshots (UI dumps) confirm MD3 compliance.
- [ ] No crashes during the entire user flow.
