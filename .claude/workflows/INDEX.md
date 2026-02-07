# Workflow Index (Bridge to .agent/)

## Available Workflows

All workflows are slash commands in `.agent/workflows/`:

| Workflow | File | Purpose |
|----------|------|---------|
| /brainstorm | `.agent/workflows/brainstorm.md` | Ideation, creative solutions |
| /create | `.agent/workflows/create.md` | Feature implementation |
| /debug | `.agent/workflows/debug.md` | Systematic debugging |
| /deploy | `.agent/workflows/deploy.md` | Deployment procedures |
| /enhance | `.agent/workflows/enhance.md` | Code improvement |
| /orchestrate | `.agent/workflows/orchestrate.md` | Multi-agent coordination |
| /plan | `.agent/workflows/plan.md` | Implementation planning |
| /preview | `.agent/workflows/preview.md` | Change preview |
| /status | `.agent/workflows/status.md` | Project status check |
| /test | `.agent/workflows/test.md` | Test generation |
| /ui-ux-pro-max | `.agent/workflows/ui-ux-pro-max.md` | Design review |

## Usage

Invoke workflows with slash commands:
```
/plan: Implement feature X
/debug: Fix bug in component Y
/ui-ux-pro-max: Review screen Z design
```

Each workflow loads appropriate agents and skills from `.agent/`.
