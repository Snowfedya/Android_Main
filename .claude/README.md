# Claude Code + Antigravity Kit

**Unified AI System for Android Development**

This directory integrates Claude Code's capabilities with the Antigravity Kit agent orchestration system.

## Structure

```
.claude/
├── settings.json          # MCP servers + permissions
├── INTEGRATION.md         # System architecture overview
├── agents/INDEX.md        # Bridge to .agent/agents/
├── skills/INDEX.md        # Bridge to .agent/skills/
├── workflows/INDEX.md     # Bridge to .agent/workflows/
└── prompts/__SYSTEM__.md  # Routing protocol for Claude
```

## Quick Start

### For Android Development
```
🤖 Applying knowledge of @[mobile-developer]...
```
Loads: mobile-design, clean-code, testing-patterns skills

### For Debugging
```
🤖 Applying knowledge of @[debugger]...
```
Loads: systematic-debugging, code-review-checklist skills

### For Architecture Decisions
```
🤖 Applying knowledge of @[code-archaeologist]...
```
Loads: architecture, api-patterns, clean-code skills

## Available Resources

| Resource | Location | Count |
|----------|----------|-------|
| Agents | `.agent/agents/` | 20 specialists |
| Skills | `.agent/skills/` | 36 modules |
| Workflows | `.agent/workflows/` | 11 slash commands |

## Key Files

- `CLAUDE.md` - Project-specific Android guidelines
- `LABS.md` - Technical specifications for labs 1-7
- `.agent/ARCHITECTURE.md` - Full Antigravity Kit documentation
- `.agent/rules/GEMINI.md` - Global AI behavior rules

## MCP Servers

Configured in `.claude/settings.json`:
- **context7** - Context memory (requires API key)
- **shadcn** - UI component generation

Update `YOUR_API_KEY` in settings.json to enable context7.
