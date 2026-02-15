---
phase: quick
plan: 5
type: execute
wave: 1
depends_on: []
files_modified:
  - lab-5/app/src/main/java/com/willpower/tracker/network/ApiService.kt
  - lab-5/app/src/main/java/com/willpower/tracker/network/models/ChatRequest.kt
  - lab-5/app/src/main/java/com/willpower/tracker/network/models/ChatResponse.kt
  - lab-5/app/src/main/java/com/willpower/tracker/network/models/AiAdvice.kt
  - lab-5/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt
  - lab-5/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
  - lab-5/app/src/main/java/com/willpower/tracker/network/models/Character.kt (delete)
  - lab-5/app/src/main/java/com/willpower/tracker/network/models/CharacterResponse.kt (delete)

autonomous: true

must_haves:
  truths:
    - "Lab 5 ApiService uses glm-4.7-flash API"
    - "HomeFragment displays AI advice instead of Rick and Morty characters"
  artifacts:
    - path: "lab-5/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt"
      provides: "Repository for fetching AI advice"
---
<objective>
Align Lab 5 with the WillPower AI API theme by replacing legacy Rick and Morty code.
</objective>

<tasks>

<task type="auto">
  <name>Task 1: Update Network Layer</name>
  <action>
    1. Read lab-7 network models and ApiService.
    2. Create lab-5/app/src/main/java/com/willpower/tracker/network/models/ChatRequest.kt
    3. Create lab-5/app/src/main/java/com/willpower/tracker/network/models/ChatResponse.kt
    4. Create lab-5/app/src/main/java/com/willpower/tracker/network/models/AiAdvice.kt
    5. Update lab-5/app/src/main/java/com/willpower/tracker/network/ApiService.kt to use the new models and endpoint.
    6. Delete lab-5 legacy models (Character.kt, etc).
  </action>
</task>

<task type="auto">
  <name>Task 2: Implement Repository</name>
  <action>
    Create lab-5/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt based on lab-7 implementation but simplified (no Room).
  </action>
</task>

<task type="auto">
  <name>Task 3: Update UI</name>
  <action>
    Update lab-5/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt to use AdviceRepository and display the advice text in the UI.
  </action>
</task>

<task type="auto">
  <name>Task 4: Verification</name>
  <action>
    Build lab-5 using Java 17.
  </action>
</task>

</tasks>
