# Task: Full Testing and Debugging via Android ADB

## Objective
Perform a complete verification cycle on a real device/emulator using ADB. This includes verifying connectivity, installing the most advanced lab (Lab 7), checking for crashes, and validating the lifecycle logging (Lab 2 requirement) and network/database operations (Lab 5-7).

## Success Criteria
- [ ] ADB device is detected and reachable.
- [ ] Lab 7 APK is successfully installed.
- [ ] HomeActivity/MainActivity launches without crashes.
- [ ] Logcat shows "Life Cycle" tags as required in Lab 2.
- [ ] Logcat shows successful database pre-population and network requests (Lab 5-7).
- [ ] UI dump verifies presence of key elements (Toolbar, RecyclerView).

## Context
- **Target Lab:** `lab-7` (highest complexity)
- **Log Tags to Monitor:** `Life Cycle`, `HomeFragment`, `ChallengeRepository`, `RetrofitClient`
