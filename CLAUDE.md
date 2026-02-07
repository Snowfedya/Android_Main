# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a multi-lab **Android development learning environment** with an integrated **AI agent orchestration system** called "Antigravity Kit".

- **Android Labs (lab-1 through lab-7):** Kotlin-based Android projects representing **progressive states** of the WillPowerTracker app (habit/challenge tracking with AI mentor)
- **AI Agent System (.agent/):** Modular framework with 20 specialist agents and 36 domain skills for development assistance

**WillPower Tracker** - An app for fighting procrastination with:
- Habit/challenge tracking based on "The Willpower Instinct" methodology (Kelly McGonigal)
- AI mentor (glm-4.7-flash) that generates motivational quotes and progress analysis
- Focus sessions with timer + fullscreen mode
- Completion history and weekly reports (.txt export + backup/restore)

**📖 Detailed lab specifications:** See [LABS.md](LABS.md) for complete technical requirements for each lab.

---

## Android Development Commands

Navigate to any lab directory (`lab-1/`, `lab-2/`, etc.) to run these commands:

```bash
# Build commands
./gradlew build              # Full build
./gradlew assembleDebug      # Debug APK
./gradlew assembleRelease    # Release APK
./gradlew clean              # Clean build artifacts

# Testing
./gradlew test               # Unit tests
./gradlew connectedAndroidTest  # Instrumentation tests

# Device deployment
./gradlew installDebug       # Install to connected device
```

> **Note:** Each lab is a standalone project. Commands are executed within the specific lab directory. See [LABS.md](LABS.md) for lab-specific functionality and requirements.

**Build Configuration (base):**
- **Language:** Kotlin 2.2.10
- **Compile SDK:** 34
- **Min SDK:** 24
- **Namespace:** `com.willpower.tracker`
- **Dependencies:** AndroidX, Material Design, RecyclerView (each lab adds more)

---

## AI Agent System Commands

The `.agent/` directory contains validation scripts and development tools:

```bash
# Priority-based validation (use during development)
python .agent/scripts/checklist.py .

# Comprehensive verification (pre-deployment, requires URL)
python .agent/scripts/verify_all.py . --url http://localhost:3000

# Mobile-specific audit
python .agent/skills/mobile-design/scripts/mobile_audit.py <project_path>
```

**Checklist Execution Order:** Security → Lint → Schema → Tests → UX → SEO → Performance

---

## Lab Progression Model

**Each lab represents a complete, working state** of the WillPowerTracker app. Every subsequent lab builds upon the previous one, introducing new Android development concepts and features.

| Lab | Focus | Key Additions | Architecture |
|-----|-------|---------------|--------------|
| **1** | UI (Activities) | 4 Activities, RecyclerView, basic navigation | Activity-based |
| **2** | Lifecycle | BaseActivity, lifecycle logging, Intent/Parcelable | Activity-based + lifecycle |
| **3** | Fragments | Single Activity, 4 Fragments, FragmentManager | Fragment-based |
| **4** | Navigation + ViewBinding | NavController, Safe Args, DetailsFragment | Fragment + Navigation Component |
| **5** | Networking | Retrofit, AI API (glm-4.7-flash), error handling | Fragment + Network |
| **6** | Storage + Focus Mode | DataStore, file I/O, fullscreen timer, reports | Fragment + Network + Storage |
| **7** | Database + MVVM | Room, Flow, Repository, SSOT, Paging | MVVM + Room + Full stack |

**User Flow (final state):** Onboard → SignIn → Home → Details → Settings

**Domain Entities:**
- `Task/Challenge` - habits with techniques, duration, difficulty
- `Completion` - execution history with timer results
- `AiAdvice` - AI-generated motivational quotes/tips
- `User` - (educational) local authentication

**📖 See [LABS.md](LABS.md) for detailed technical specifications, acceptance criteria, and demo requirements for each lab.**

---

## High-Level Architecture

### Android Application Structure

Each lab follows the standard Android project structure with evolution across labs:

```
lab-N/
├── app/
│   └── src/main/
│       ├── java/com/willpower/tracker/
│       │   ├── activities/      # Lab 1-2: UI activities
│       │   ├── fragments/       # Lab 3-7: Fragments (single Activity)
│       │   ├── adapters/        # RecyclerView adapters
│       │   ├── models/          # Data models (Challenge, User, etc.)
│       │   ├── network/         # Lab 5-7: Retrofit + API models
│       │   ├── storage/         # Lab 6-7: DataStore, BackupManager
│       │   ├── database/        # Lab 7: Room entities + DAOs
│       │   ├── viewmodel/       # Lab 7: ViewModels (MVVM)
│       │   └── repository/      # Lab 5-7: Repository pattern
│       ├── res/                 # Android resources (layouts, drawables, colors, strings)
│       ├── navigation/          # Lab 4-7: Navigation graph
│       └── AndroidManifest.xml
├── build.gradle.kts            # App-level build configuration (evolves per lab)
└── settings.gradle.kts         # Project settings
```

**Architecture Evolution:**
- **Labs 1-2:** Activity-based with Intent navigation
- **Lab 3:** Fragment-based with FragmentManager
- **Labs 4-7:** Fragment-based with Navigation Component
- **Lab 7:** MVVM with Room + Flow + Repository

### Antigravity Kit Architecture

The AI agent system uses **progressive disclosure** - knowledge is loaded on-demand based on task context:

```
User Request → Agent Selection → Skill Loading → Execution
```

**Key Components:**

- **20 Specialist Agents:** Domain-specific AI personas (mobile-developer, frontend-specialist, backend-specialist, security-auditor, debugger, etc.)
- **36 Skills:** Modular knowledge modules loaded by agents (mobile-design, react-best-practices, api-patterns, testing-patterns, vulnerability-scanner, etc.)
- **11 Workflows:** Slash commands for common tasks (/brainstorm, /create, /debug, /deploy, /enhance, /plan, /test, /ui-ux-pro-max, etc.)

**Agent Selection Protocol:**

1. Classify request type (QUESTION, SIMPLE CODE, COMPLEX CODE, DESIGN/UI, SLASH CMD)
2. Select appropriate specialist agent
3. Load required skills from agent's frontmatter
4. Apply agent-specific rules and protocols

**For Android work:** Always use the `mobile-developer` agent with `mobile-design` skill.

---

## Key Files to Understand

- **[LABS.md](LABS.md)** - Complete technical specifications for all 7 labs (requirements, acceptance criteria, demo requirements)
- **`.agent/ARCHITECTURE.md`** - Complete Antigravity Kit system documentation
- **`.agent/rules/GEMINI.md`** - Global AI behavior rules and protocols (agent routing, Socratic gate, clean code standards)
- **`.agent/skills/mobile-design/SKILL.md`** - Mobile UI/UX patterns and best practices
- **Lab-specific `app/build.gradle.kts`** - Dependencies and build configuration per lab

---

## Important Development Protocols

1. **Read Before Writing:** Always read existing code before modifying. The project uses a "Read → Understand → Apply" protocol.

2. **Agent Routing:** Before ANY code or design work, identify the correct agent. For Android labs, use `mobile-developer`. Announce with: "🤖 Applying knowledge of `@[mobile-developer]`..."

3. **Socratic Gate:** For complex implementations, ask strategic questions before proceeding. Never assume requirements.

4. **Clean Code:** All code must follow the clean-code rules defined in `.agent/skills/clean-code/`. No over-engineering, self-documenting code, mandatory testing.

5. **Validation:** Tasks are not complete until `checklist.py` passes. Fix critical blockers (Security, Lint) first.

6. **Mobile-Specific Rules:** The `mobile-developer` agent contains specific design rules including:
   - No violet/purple colors (Purple Ban)
   - No standard template layouts
   - Deep Design Thinking protocol
   - Material Design guidelines adherence

---

## Dependencies

**Android Core:**
- androidx.core:core-ktx:1.12.0
- androidx.appcompat:appcompat:1.6.1
- com.google.android.material:material:1.11.0
- androidx.constraintlayout:constraintlayout:2.1.4
- androidx.recyclerview:recyclerview:1.3.2

**Testing:**
- junit:junit:4.13.2
- androidx.test.ext:junit:1.1.5
- androidx.test.espresso:espresso-core:3.5.1
