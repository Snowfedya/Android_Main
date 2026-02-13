# Research Summary: Willpower Tracker (Android)

**Domain:** AI-Enhanced Habit Tracking / Personal Growth
**Researched:** 2025-05-22
**Overall confidence:** HIGH

## Executive Summary

The project is an evolution of a series of lab assignments (Lab 1-7) into a production-ready **Willpower Tracker**. The current codebase uses traditional XML Views and a standard Room/Retrofit stack. To align with 2025 best practices, the new project should transition to **Jetpack Compose** for UI and adopt a more rigorous **Clean Architecture (MVI/MVVM)**.

Key innovations include the integration of **Generative AI (Gemini SDK)** to provide coaching and insights, transforming a passive checklist into an active personal growth companion. The ecosystem for Android in 2025 strongly favors Kotlin Multiplatform (KMP) readiness, although for this project, a native-first approach with Compose is recommended for velocity.

## Key Findings

**Stack:** Kotlin, Jetpack Compose, Hilt, Room, Retrofit, Gemini SDK.
**Architecture:** Layered Clean Architecture (Data, Domain, UI) with Unidirectional Data Flow.
**Critical pitfall:** Room migration failures and Coroutine lifecycle leaks are the top technical risks.

## Implications for Roadmap

Based on research, suggested phase structure:

1. **Foundations & Modernization** - Replace XML with Compose and set up Hilt/Room.
   - Addresses: Habit creation, Local storage.
   - Avoids: Legacy UI debt.

2. **Core Habit Engine** - Implement business logic and progress tracking.
   - Addresses: Streaks, visualization.

3. **AI Enhancement** - Integrate Gemini for insights and "AI Coaching".
   - Addresses: Differentiation.
   - Avoids: Simple "check-mark" app fatigue.

4. **Polishing & Social** - Add notifications, badges, and community challenges.

**Phase ordering rationale:**
- Architecture must come first to prevent rewrites.
- The AI layer depends on a solid history of habit data, so it follows the core engine.

**Research flags for phases:**
- Phase 3: Requires research into Gemini prompt engineering for habit psychology.
- Phase 4: Requires research into Firebase/WebSockets for real-time social features.

## Confidence Assessment

| Area | Confidence | Notes |
|------|------------|-------|
| Stack | HIGH | Based on 2025 industry standards. |
| Features | HIGH | Common patterns in top-tier habit apps. |
| Architecture | HIGH | Google-recommended patterns. |
| Pitfalls | MEDIUM | Standard technical debt items, but domain-specific AI risks are newer. |

## Gaps to Address

- **AI Token Cost**: Need to evaluate the cost implications of frequent Gemini API calls.
- **Offline AI**: Investigate if ML Kit can handle some basic insights offline to save battery and cost.
- **Specific Lab Requirements**: Verify if there are any pedagogical requirements from the original lab course that must be preserved.
