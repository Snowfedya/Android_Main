---
phase: quick
plan: 4
type: execute
wave: 1
depends_on: []
files_modified:
  - lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt
  - lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt

autonomous: true

must_haves:
  truths:
    - "ChallengeAdapter extends ListAdapter with DiffUtil for efficient updates"
    - "RecyclerView updates are animated automatically when data changes"
    - "HomeFragment uses submitList() instead of updateChallenges()"
  artifacts:
    - path: "lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt"
      provides: "ListAdapter with DiffUtil for efficient RecyclerView updates"
      contains: "class ChallengeAdapter.*ListAdapter"
    - path: "lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeDiffCallback.kt"
      provides: "DiffUtil.ItemCallback for comparing Challenge items"
      contains: "class ChallengeDiffCallback.*DiffUtil.ItemCallback"
  key_links:
    - from: "HomeFragment"
      to: "ChallengeAdapter"
      via: "submitList() call instead of updateChallenges()"
      pattern: "adapter\\.submitList\\("
    - from: "ChallengeAdapter"
      to: "AsyncListDiffer"
      via: "ListAdapter internal implementation"
      pattern: "extends ListAdapter"
---
<objective>
Convert ChallengeAdapter from RecyclerView.Adapter to ListAdapter with DiffUtil for efficient RecyclerView updates.

Purpose: Improve RecyclerView performance and add automatic item change animations by using ListAdapter with DiffUtil instead of manual notifyDataSetChanged().

Output:
- ChallengeAdapter extends ListAdapter with DiffUtil.ItemCallback
- HomeFragment uses submitList() for efficient list updates
- Automatic diff-based animations when list data changes
</objective>

<execution_context>
@/home/snow/.claude/get-shit-done/workflows/execute-plan.md
@/home/snow/.claude/get-shit-done/templates/summary.md
</execution_context>

<context>
@.planning/STATE.md
@lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt
@lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
@lab-7/app/src/main/java/com/willpower/tracker/models/Challenge.kt
</context>

<tasks>

<task type="auto">
  <name>Task 1: Convert ChallengeAdapter to ListAdapter with DiffUtil</name>
  <files>
    lab-7/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt
    lab-7/app/src/main/java/com/willpower/tracker/fragments/HomeFragment.kt
  </files>
  <action>
    Convert ChallengeAdapter from RecyclerView.Adapter to ListAdapter:

    **1. Create DiffUtil.ItemCallback for Challenge model:**
    - Add at top of ChallengeAdapter.kt (outside class) or as nested object
    - Extend DiffUtil.ItemCallback<Challenge>
    - Implement areItemsTheSame(old, new): compare Challenge.id
    - Implement areContentsTheSame(old, new): compare all relevant fields (title, description, isCompleted, category, streak, duration)
    - Use lazy comparison for expensive fields (description)

    **2. Update ChallengeAdapter class declaration:**
    - Change from: RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>
    - Change to: ListAdapter<Challenge, ChallengeAdapter.ChallengeViewHolder>(ChallengeDiffCallback())
    - Remove challenges field (ListAdapter manages the list internally)
    - Remove updateChallenges() method (use built-in submitList())

    **3. Update constructor:**
    - Remove challenges parameter (ListAdapter manages list)
    - Keep onItemClick and onCheckChanged lambdas

    **4. Update onBindViewHolder:**
    - Change: val challenge = challenges[position]
    - Change to: val challenge = getItem(position) (ListAdapter method)

    **5. Update HomeFragment.kt:**
    - Remove adapter.updateChallenges() call in updateChallengesList()
    - Change to: adapter.submitList(challenges)
    - Remove emptyList() from adapter constructor initialization
    - Initialize adapter without challenges parameter: ChallengeAdapter(onItemClick = {...}, onCheckChanged = {...})

    **6. Keep other functionality intact:**
    - ViewHolder class remains unchanged
    - onCreateViewHolder remains unchanged
    - Click listeners and CheckBox behavior unchanged
    - formatDuration() helper remains unchanged
  </action>
  <verify>
    Compile check: "./gradlew assembleDebug" in lab-7 directory succeeds
    ChallengeAdapter now extends ListAdapter
    HomeFragment uses submitList() instead of updateChallenges()
  </verify>
  <done>
    ChallengeAdapter extends ListAdapter with:
    - ChallengeDiffCallback implementing areItemsTheSame() and areContentsTheSame()
    - ListAdapter managing internal list automatically
    - HomeFragment using submitList() for efficient updates
    - Automatic item animations when data changes
  </done>
</task>

</tasks>

<verification>
Overall verification checks:
- [ ] ChallengeAdapter extends ListAdapter<Challenge, ChallengeViewHolder>
- [ ] ChallengeDiffCallback compares items by id
- [ ] ChallengeDiffCallback compares contents by relevant fields
- [ ] onBindViewHolder uses getItem(position) instead of challenges[position]
- [ ] HomeFragment uses adapter.submitList() instead of updateChallenges()
- [ ] App compiles without errors: ./gradlew assembleDebug
- [ ] RecyclerView updates animate automatically when data changes
</verification>

<success_criteria>
Measurable completion criteria:
1. ChallengeAdapter converted to extend ListAdapter with DiffUtil.ItemCallback
2. HomeFragment uses submitList() for list updates instead of updateChallenges()
3. List animations work automatically when data changes from ViewModel
4. App builds and runs without crashes
5. All existing functionality preserved (clicks, checkbox, navigation)
</success_criteria>

<output>
After completion, create `.planning/quick/4-test-quick-task/4-SUMMARY.md`
</output>
