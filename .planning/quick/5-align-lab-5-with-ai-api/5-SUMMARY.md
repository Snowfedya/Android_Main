---
phase: quick
plan: 5
subsystem: network
tags: [kotlin, retrofit, networking, ai-api]

# Dependency graph
requires: []
provides:
  - Lab 5 aligned with WillPower AI API theme
  - AdviceRepository for fetching motivational advice
affects: [lab-5]

# Tech tracking
tech-stack:
  modified: [Retrofit, ApiService, HomeFragment]
  removed: [Rick and Morty API legacy code]

key-files:
  created: 
    - lab-5/app/src/main/java/com/willpower/tracker/network/models/ChatRequest.kt
    - lab-5/app/src/main/java/com/willpower/tracker/network/models/ChatResponse.kt
    - lab-5/app/src/main/java/com/willpower/tracker/network/models/AiAdvice.kt
    - lab-5/app/src/main/java/com/willpower/tracker/repository/AdviceRepository.kt
  modified:
    - lab-5/app/src/main/java/com/willpower/tracker/network/ApiService.kt
    - lab-5/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt
    - lab-5/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
    - lab-5/app/src/main/res/values/strings.xml
    - lab-5/gradle/wrapper/gradle-wrapper.properties
  removed:
    - lab-5/app/src/main/java/com/willpower/tracker/network/models/Character.kt
    - lab-5/app/src/main/java/com/willpower/tracker/repository/CharacterRepository.kt
    - lab-5/app/src/main/java/com/willpower/tracker/adapters/CharacterAdapter.kt

# Metrics
duration: 10min
completed: 2026-02-14
---

# Phase quick: Plan 5 Summary

**Aligned Lab 5 with the WillPower AI API theme by replacing legacy Rick and Morty code.**

## Accomplishments
- Replaced Rick and Morty API with `glm-4.7-flash` AI API in `lab-5`.
- Implemented `AdviceRepository` to fetch motivational quotes.
- Updated `HomeFragment` to display AI advice in the UI.
- Updated `RetrofitClient` with proper `Authorization` headers and `BASE_URL`.
- Upgraded Gradle wrapper to 8.9 to match plugin requirements.
- Verified build with Java 17.
