# Task: Verify and Complete Lab 7 Room Integration

## Objective
Ensure Lab 7 has a fully functional Room database integration, including entity definitions, DAO operations, pre-population logic, and reactive UI updates via Flow.

## Success Criteria
- [ ] `AppDatabase.kt` is correctly configured with all entities.
- [ ] `ChallengeRepository.kt` correctly initializes sample data if the database is empty.
- [ ] `HomeFragment.kt` and `HomeViewModel.kt` use Flow to observe database changes.
- [ ] Database schema is exported (if `exportSchema = true` is set).
- [ ] `lab-7` builds successfully with Java 17.
- [ ] (Optional/Bonus) Basic unit test for a DAO.

## Context
- **Target Lab:** `lab-7`
- **Database Name:** `willpower_database`
- **Entities:** `TaskEntity`, `AiAdviceEntity`, `CompletionEntity`
