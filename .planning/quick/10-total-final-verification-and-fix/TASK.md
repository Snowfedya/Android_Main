# Task: Total Final Verification and Auto-Fix (Labs 1-7)

## Objective
Provide an exhaustive, automated and manual verification of all 7 labs via ADB. Every feature described in the `lab/*.pdf` files must be confirmed working on the device.

## Verification Checklist

### Lab 1: UI & Activities
- [ ] Build & Install.
- [ ] 4 Activities navigable (Onboard -> SignIn -> SignUp -> Home).
- [ ] RecyclerView shows 5+ sample items.
- [ ] MD3 Components verified (MaterialButton, MaterialCardView).

### Lab 2: Lifecycle & Intent
- [ ] "Life Cycle" logs visible in Logcat for ALL activities.
- [ ] SignUp returns User Parcelable/Strings to SignIn.
- [ ] SignIn displays "Welcome, [Name]" from result.

### Lab 3: Fragments
- [ ] Single Activity (MainActivity).
- [ ] 4 Fragments navigable via FragmentManager.
- [ ] Fragment transactions saved in backstack.

### Lab 4: Navigation Component & ViewBinding
- [ ] NavController handles all flows.
- [ ] Safe Args verified (Challenge ID passed to Details).
- [ ] ViewBinding used in all fragments (no findViewById).

### Lab 5: Networking
- [ ] GLM-4.7-Flash API integration working.
- [ ] "Daily Advice" fetched and displayed.
- [ ] No Rick & Morty residues.

### Lab 6: Storage & Focus Mode
- [ ] DataStore persistence verified (Notification setting).
- [ ] .txt Report generated in /Documents.
- [ ] JSON Backup/Restore functional.
- [ ] Focus Mode timer (fullscreen) verified.

### Lab 7: Room & MVVM
- [ ] Room DB pre-population.
- [ ] Add Challenge (FAB) -> Save to Room.
- [ ] Edit Challenge -> Update Room.
- [ ] AI Analysis demo functional.
- [ ] No crashes on item click.

## Success Criteria
- [ ] 7/7 Labs verified without issues.
- [ ] Any detected bugs fixed immediately.
- [ ] GitHub repository updated with final "Defense Ready" state.
