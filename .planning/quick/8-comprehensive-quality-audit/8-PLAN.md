---
phase: quick
plan: 8
type: execute
wave: 1
depends_on: []
files_modified: []

autonomous: true

must_haves:
  truths:
    - "Login successful"
    - "All 7 labs verified for specific criteria"
    - "Visual and functional audit passed"
---
<objective>
Execute a systematic, deep audit of all 7 labs to ensure absolute readiness for demonstration.
</objective>

<tasks>

<task type="auto">
  <name>Lab 1 Audit</name>
  <action>
    Build, install, launch. Verify 4 activities, RecyclerView data, and MD3 buttons.
  </action>
</task>

<task type="auto">
  <name>Lab 2 Audit</name>
  <action>
    Verify lifecycle logs. Test SignUp data return to SignIn via Intent extras.
  </action>
</task>

<task type="auto">
  <name>Lab 3 Audit</name>
  <action>
    Verify Fragment-based navigation and FragmentManager logs.
  </action>
</task>

<task type="auto">
  <name>Lab 4 Audit</name>
  <action>
    Test NavComponent flow. Click item -> Details. Verify Safe Args in logs.
  </name>
</task>

<task type="auto">
  <name>Lab 5 Audit</name>
  <action>
    Verify AI advice fetching. Check Retrofit logs.
  </action>
</task>

<task type="auto">
  <name>Lab 6 Audit</name>
  <action>
    Test Settings persistence. Generate .txt report. Test Focus Mode timer.
  </action>
</task>

<task type="auto">
  <name>Lab 7 Audit</name>
  <action>
    Full flow audit: Login -> Home -> Details -> Focus. Verify Room persistence and Flow updates.
  </action>
</task>

</tasks>
