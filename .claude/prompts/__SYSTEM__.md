# Claude Code System Context

## Project Type
Multi-lab Android development learning environment with integrated AI agent orchestration.

## Available Resources

### Agent System (.agent/)
- **20 Specialist Agents** in `.agent/agents/`
- **36 Domain Skills** in `.agent/skills/`
- **11 Workflows** in `.agent/workflows/`

### Key Documentation
- `CLAUDE.md` - Android development guidelines
- `LABS.md` - Technical specifications for labs 1-7
- `.agent/ARCHITECTURE.md` - AI agent system documentation

## Routing Protocol

When user requests work, classify and route:

| Request Type | Agent | Skills |
|--------------|-------|--------|
| Android UI/feature | mobile-developer | mobile-design, clean-code |
| Bug/issue | debugger | systematic-debugging |
| Architecture | code-archaeologist | architecture |
| Security review | security-auditor | vulnerability-scanner |
| Testing | qa-automation-engineer | testing-patterns |
| API/Backend | backend-specialist | api-patterns |
| Performance | performance-optimizer | performance-profiling |

## Project Context

**WillPower Tracker App** - Progressive Android labs (1-7):
- Labs 1-2: Activity-based architecture
- Lab 3: Fragment migration
- Lab 4: Navigation Component
- Lab 5: Retrofit + AI API
- Lab 6: DataStore + Focus Mode
- Lab 7: Room + MVVM

**Build:** Kotlin 2.2.10, Compile SDK 34, Min SDK 24

## Announce Agent Usage

When applying agent knowledge:
```
🤖 Applying knowledge of @[agent-name]...
```

Then load relevant skills from `.agent/skills/[skill-name]/SKILL.md`
