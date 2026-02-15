---
phase: quick
plan: 7
type: execute
wave: 1
depends_on: []
files_modified: []

autonomous: true

must_haves:
  truths:
    - "App installs and launches via ADB"
    - "Logcat confirms expected behavior"
---
<objective>
Verify Lab 7 functionality on a live device/emulator using ADB commands.
</objective>

<tasks>

<task type="auto">
  <name>Task 1: Device Connectivity Check</name>
  <action>
    Run `adb devices` to ensure a target is available.
  </action>
</task>

<task type="auto">
  <name>Task 2: Build and Install Lab 7</name>
  <action>
    1. Build debug APK for lab-7.
    2. Install APK using `adb install -r`.
  </action>
</task>

<task type="auto">
  <name>Task 3: Launch and Logcat Monitoring</name>
  <action>
    1. Launch the app's main entry point.
    2. Capture and analyze logcat for key lifecycle and business logic events.
  </action>
</task>

<task type="auto">
  <name>Task 4: UI Hierarchy Verification</name>
  <action>
    Dump UI hierarchy and check for the existence of `rvChallenges` and `tvAdviceText` (or `tvQuote`).
  </action>
</task>

</tasks>
