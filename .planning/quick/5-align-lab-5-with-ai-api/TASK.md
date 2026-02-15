# Task: Align Lab 5 with WillPower AI API

## Objective
Replace the legacy Rick and Morty API implementation in `lab-5` with the project-standard WillPower AI API (`glm-4.7-flash`).

## Success Criteria
- [ ] `ApiService.kt` in `lab-5` uses `POST /chat/completions`
- [ ] Network models (`ChatRequest`, `ChatResponse`, `AiAdvice`) aligned with project standards
- [ ] `HomeFragment.kt` in `lab-5` updated to display AI advice instead of characters
- [ ] Legacy Rick and Morty files removed from `lab-5`
- [ ] `lab-5` builds successfully with Java 17

## Context
- **Target Lab:** `lab-5`
- **Reference Implementation:** `lab-7` (already has the correct API structure)
- **API Base URL:** `https://open.bigmodel.cn/api/paas/v4/`
- **Model:** `glm-4.7-flash`
- **Language:** Kotlin 1.8 compatibility
