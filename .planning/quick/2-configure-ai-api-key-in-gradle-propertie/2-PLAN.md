---
phase: quick
plan: 2
type: execute
wave: 1
depends_on: []
files_modified:
  - lab-5/gradle.properties
  - lab-6/gradle.properties
  - lab-7/gradle.properties
autonomous: false
user_setup:
  - service: "GLM API (open.bigmodel.cn)"
    why: "AI mentor generates motivational quotes and progress analysis"
    env_vars:
      - name: API_KEY
        source: "https://open.bigmodel.cn/usercenter/apikeys - Create new API key"
        instructions: "Sign in to BigModel, navigate to API Keys section, create a new key, and copy the value"

must_haves:
  truths:
    - "API_KEY property exists in gradle.properties for labs 5, 6, 7"
    - "BuildConfig.API_KEY is properly injected at build time"
    - "API calls to glm-4.7-flash use the configured key"
  artifacts:
    - path: "lab-5/gradle.properties"
      provides: "API_KEY property for Lab 5"
      contains: "API_KEY="
    - path: "lab-6/gradle.properties"
      provides: "API_KEY property for Lab 6"
      contains: "API_KEY="
    - path: "lab-7/gradle.properties"
      provides: "API_KEY property for Lab 7"
      contains: "API_KEY="
  key_links:
    - from: "lab-*/app/build.gradle.kts"
      to: "gradle.properties"
      via: "project.findProperty('API_KEY')"
      pattern: "buildConfigField.*API_KEY.*findProperty"
---

<objective>
Configure AI API key in gradle.properties for Labs 5-7 and test glm-4.7-flash integration

Purpose: Enable real AI API calls by adding the API_KEY property to each lab's gradle.properties file, replacing the empty string fallback currently used in BuildConfig.

Output: Working AI mentor integration with authenticated requests to open.bigmodel.cn API
</objective>

<execution_context>
@/home/snow/.claude/get-shit-done/workflows/execute-plan.md
@/home/snow/.claude/get-shit-done/templates/summary.md
</execution_context>

<context>
@.planning/STATE.md
@lab-5/app/build.gradle.kts
@lab-6/app/build.gradle.kts
@lab-7/app/build.gradle.kts
</context>

<tasks>

<task type="checkpoint:human-action">
  <name>Task 1: Obtain GLM API Key</name>
  <files>none</files>
  <action>
    User must obtain an API key from BigModel (open.bigmodel.cn):
    1. Visit https://open.bigmodel.cn/usercenter/apikeys
    2. Sign in or create account
    3. Create a new API key
    4. Copy the key value (format: sk-xxxxxxxxxxxxxxxxxxxxx)
  </action>
  <verify>User has API key available</verify>
  <done>API key string available for configuration</done>
</task>

<task type="auto">
  <name>Task 2: Add API_KEY to gradle.properties files</name>
  <files>
    lab-5/gradle.properties
    lab-6/gradle.properties
    lab-7/gradle.properties
  </files>
  <action>
    For each lab (5, 6, 7), append to the end of gradle.properties:

    ```properties
    # GLM API Configuration for AI Mentor feature
    # Obtain from: https://open.bigmodel.cn/usercenter/apikeys
    API_KEY=your-actual-api-key-here
    ```

    Replace "your-actual-api-key-here" with the actual API key value obtained in Task 1.

    IMPORTANT: Add each gradle.properties to .gitignore to prevent committing secrets:
    - Add "lab-*/gradle.properties" pattern to project-level .gitignore
    - Or use a local gradle.properties in ~/.gradle/gradle.properties
  </action>
  <verify>
    Run: `grep "API_KEY" lab-*/gradle.properties`
    Expected: API_KEY property appears in all three files
  </verify>
  <done>
    - lab-5/gradle.properties contains API_KEY property
    - lab-6/gradle.properties contains API_KEY property
    - lab-7/gradle.properties contains API_KEY property
  </done>
</task>

<task type="auto">
  <name>Task 3: Verify BuildConfig.API_KEY injection and test API call</name>
  <files>
    lab-7/app/src/main/java/com/willpower/tracker/network/ApiService.kt
  </files>
  <action>
    1. Rebuild Lab 7 (or any lab 5-7) to ensure BuildConfig picks up the new property:
       ```bash
       cd lab-7 && ./gradlew clean assembleDebug
       ```

    2. Verify the API key is injected correctly by checking BuildConfig:
       - In code where API is called, ensure it uses: `BuildConfig.API_KEY`
       - The build.gradle.kts already has: `buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY") ?: ""}\"")`

    3. Test the AI integration:
       - Install and run the app on device/emulator
       - Navigate to Home screen
       - Trigger AI advice request (should happen automatically on load)
       - Verify API call succeeds and displays AI-generated motivational quote

    4. If API call fails:
       - Check Logcat for authentication errors (401/403)
       - Verify API_KEY format (should start with "sk-")
       - Confirm API key is active at https://open.bigmodel.cn/usercenter/apikeys
  </action>
  <verify>
    1. Build succeeds without errors
    2. App runs on device/emulator
    3. AI advice displays on Home screen (not empty fallback message)
    4. Logcat shows successful API response (200 OK) from open.bigmodel.cn
  </verify>
  <done>
    - BuildConfig.API_KEY contains actual API key (not empty string)
    - AI API calls return 200 OK with valid response
    - App displays AI-generated motivational quotes/tips
  </done>
</task>

</tasks>

<verification>
1. All three gradle.properties files contain API_KEY property
2. API key is not committed to git (checked .gitignore)
3. Build completes successfully for labs 5-7
4. Runtime API calls succeed with authenticated requests
5. AI mentor feature works end-to-end
</verification>

<success_criteria>
- API_KEY configured in all three lab directories (5, 6, 7)
- BuildConfig.API_KEY properly injected at build time
- glm-4.7-flash API returns successful responses
- AI advice displays in app (verified on device/emulator)
- API key kept secure (not in version control)
</success_criteria>

<output>
After completion, create `.planning/quick/2-configure-ai-api-key-in-gradle-propertie/2-SUMMARY.md`
</output>
