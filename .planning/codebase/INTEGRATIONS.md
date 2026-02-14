# External Integrations

**Analysis Date:** 2025-02-14

## APIs & External Services

**AI/LLM Services:**
- **GLM-4.7-Flash API** (Zhipu AI / BigModel.cn) - AI-generated motivational quotes and advice
  - SDK/Client: Retrofit 2.9.0 with OkHttp 4.12.0
  - Auth: Bearer token via `BuildConfig.API_KEY`
  - Base URL: `https://open.bigmodel.cn/api/paas/v4/`
  - Endpoint: `POST /chat/completions`
  - Model: `glm-4.7-flash`
  - Implementation: `/lab-5/app/src/main/java/com/willpower/tracker/network/` through `/lab-7/`
  - API Key Source: https://open.bigmodel.cn/usercenter/apikeys
  - Timeout: 30s connect, 60s read, 30s write

## Data Storage

**Local Database:**
- **Room Database** (Lab 7)
  - Connection: `willpower_database` (local SQLite)
  - Client: Room 2.6.1 with KTX
  - Entities: `TaskEntity`, `AiAdviceEntity`, `CompletionEntity`
  - DAOs: `TaskDao`, `AiAdviceDao`, `CompletionDao`
  - Schema location: `/app/schemas/com.willpower.tracker.database.AppDatabase/`
  - Implementation: `/lab-7/app/src/main/java/com/willpower/tracker/database/`

**Key-Value Storage:**
- **DataStore Preferences** (Labs 6-7)
  - Connection: `user_preferences` protobuf file
  - Client: `androidx.datastore:datastore-preferences:1.0.0`
  - Keys stored: `user_name`, `user_email`, `is_logged_in`, `notification_enabled`, `reminder_hour`, `reminder_minute`, `theme_mode`, `total_completed`, `current_streak`
  - Implementation: `/lab-6/app/src/main/java/com/willpower/tracker/storage/UserPreferencesManager.kt` through `/lab-7/`

**File Storage:**
- **Local filesystem only** (internal + external app directories)
  - Reports: `willpower_report_*.txt` files
  - Backups: `willpower_backup_*.json` files
  - Locations: `context.filesDir/` or `context.getExternalFilesDir(null)/`
  - Implementation: `/lab-6/app/src/main/java/com/willpower/tracker/storage/` through `/lab-7/`

**Caching:**
- **None** - No external caching service

## Authentication & Identity

**Auth Provider:**
- **Custom/Local** (Educational implementation only)
  - Implementation: Local email/password storage via DataStore
  - No real authentication server
  - Flow: Onboard → SignIn → Home
  - Credentials stored in `UserPreferencesManager`
  - Files: `/lab-7/app/src/main/java/com/willpower/tracker/fragments/SignInFragment.kt`, `SignUpFragment.kt`
  - Logout clears `is_logged_in` flag only

## Monitoring & Observability

**Error Tracking:**
- **None** - No crash reporting service integrated

**Logs:**
- **OkHttp Logging Interceptor** (Labs 5-7)
  - Debug builds: Full HTTP logging (BODY level)
  - Release builds: No logging
  - Implementation: `/lab-5/app/src/main/java/com/willpower/tracker/network/RetrofitClient.kt` through `/lab-7/`

## CI/CD & Deployment

**Hosting:**
- **None** - Local development builds only

**CI Pipeline:**
- **None** - No GitHub Actions or other CI configured

## Environment Configuration

**Required env vars:**
- `API_KEY` (gradle.properties) - GLM API key for AI features
- `API_BASE_URL` (buildConfigField) - `https://open.bigmodel.cn/api/paas/v4/`

**Secrets location:**
- `/lab-N/gradle.properties` - Local property file (not committed)
- Injected via `BuildConfig.API_KEY` at build time
- Current value in lab-7: `51f7eb3de3cc417fbe635694e05402d1.BMvzhYn36sygKdP8`

**Additional gradle.properties settings:**
- `org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8`
- `android.useAndroidX=true`
- `kotlin.code.style=official`
- `org.gradle.java.home=/usr/lib/jvm/java-17-microsoft-openjdk`

## Webhooks & Callbacks

**Incoming:**
- **None** - No webhook endpoints

**Outgoing:**
- **None** - No outbound webhooks

## Data Serialization

**JSON Serialization:**
- **Kotlinx Serialization** 1.6.2
  - Plugin: `org.jetbrains.kotlin.plugin.serialization`
  - Format: JSON
  - Used for: API requests/responses, backup/restore
  - Implementation: Chat/Request/Response models in `/lab-5/app/src/main/java/com/willpower/tracker/network/models/` through `/lab-7/`

**Parcelable:**
- **Kotlin Parcelize** plugin
  - Used for: Fragment arguments (Safe Args)
  - Files with `@Parcelize` annotation

## UI Framework Integration

**Material Design:**
- **Material Components for Android** 1.11.0
  - Theme: `Theme.WillPowerTracker`
  - Components: Cards, Buttons, TextInputLayout, Navigation Views

**ViewBinding:**
- **Android ViewBinding** (Labs 4-7)
  - Enabled: `buildFeatures { viewBinding = true }`
  - Type-safe view binding for all fragments

**Navigation Component:**
- **Android Navigation** 2.7.6 (Labs 4-7)
  - Safe Args for type-safe navigation
  - Navigation graph: `/app/src/main/navigation/nav_graph.xml`
  - Plugin: `androidx.navigation.safeargs.kotlin`

## Room Database Schema

**Schema Export:**
- Enabled: `exportSchema = true`
- Location: `/app/schemas/com.willpower.tracker.database.AppDatabase/`
- Version: 1
- Schema file: `1.json`
- Migration strategy: `fallbackToDestructiveMigration()`

## Room + Paging Integration

**Paging 3 with Room:**
- `androidx.paging:paging-runtime-ktx:3.2.1`
- `androidx.room:room-paging:2.6.1`
- Used for: Efficient list loading (Lab 7)

## KSP (Kotlin Symbol Processing)

**KSP Version:** 2.0.21-1.0.28 (Lab 7)

**Processed Annotations:**
- Room annotations (`@Entity`, `@Dao`, `@Database`, `@Query`, etc.)
- Generates: Database implementation, DAO implementations

---

*Integration audit: 2025-02-14*
