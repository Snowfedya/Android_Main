# Phase 1: Lab 1 - User Interface (Activities) - Research

**Researched:** 2026-02-17
**Domain:** Android UI Development (Activity-based architecture, Material Design 3, RecyclerView)
**Confidence:** HIGH

## Summary

Lab 1 is already **substantially implemented** in the `/home/snow/Projects/Android_Main/lab-1/` directory. The project contains all required Activity classes, layouts, and supporting files. However, verification is needed to ensure all acceptance criteria from LABS.md are met.

**Current State Assessment:**
- 4 Activities implemented (OnboardActivity, SignInActivity, SignUpActivity, HomeActivity)
- RecyclerView with ChallengeAdapter implemented
- Material Design 3 theme configured
- Sample data provided via Challenge.getSampleChallenges()
- Validation logic implemented in SignIn/SignUp activities
- Email validation uses android.util.Patterns.EMAIL_ADDRESS (stronger than "@ check" requirement)
- Password validation uses 6-character minimum (exceeds 4-character requirement)

**Primary recommendation:** Complete the existing implementation rather than starting from scratch. Focus on verification, testing, and ensuring all LABS.md requirements are met.

**Key Gap:** Current password validation requires 6 characters, but LABS.md specifies "minimum 4 characters" - this needs alignment.

## Standard Stack

### Core
| Library | Version | Purpose | Why Standard |
|---------|---------|---------|--------------|
| Android Gradle Plugin | 8.7.3 | Build system | Current stable version for 2026 |
| Kotlin | 2.0.21 | Language | Latest stable, modern features |
| Compile SDK | 34 | Target platform | Android 14 compatibility |
| Min SDK | 24 | Minimum platform | Covers 98%+ of active devices |
| Material Design 3 | 1.11.0 | UI Components | Google's recommended design system |

### Supporting
| Library | Version | Purpose | When to Use |
|---------|---------|---------|-------------|
| androidx.recyclerview | 1.3.2 | List display | ALL scrollable lists (never ScrollView for lists) |
| androidx.appcompat | 1.6.1 | Backward compatibility | Required for Material components |
| androidx.constraintlayout | 2.1.4 | Complex layouts | ALL screen layouts (performance over LinearLayout) |
| androidx.core:core-ktx | 1.12.0 | Kotlin extensions | Core Android utilities |

### Alternatives Considered
| Instead of | Could Use | Tradeoff |
|------------|-----------|----------|
| Activities (current) | Fragments | Lab 3 will migrate to fragments - Activities are correct for Lab 1 |
| ConstraintLayout | LinearLayout/RelativeLayout | ConstraintLayout is more performant and flexible |
| Material 3 | Material 2 | Material 3 is current standard, better dynamic color support |

**Installation:** Dependencies already present in build.gradle.kts - no additional packages needed.

## Architecture Patterns

### Recommended Project Structure (Already Implemented)
```
app/src/main/
├── java/com/willpower/tracker/
│   ├── activities/           # Activity classes (Lab 1 approach)
│   │   ├── OnboardActivity.kt
│   │   ├── SignInActivity.kt
│   │   ├── SignUpActivity.kt
│   │   └── HomeActivity.kt
│   ├── adapters/             # RecyclerView adapters
│   │   └── ChallengeAdapter.kt
│   └── models/               # Data classes
│       └── Challenge.kt
├── res/
│   ├── layout/               # Activity and item layouts
│   ├── values/               # Colors, strings, themes
│   └── drawable/             # Vector drawables, backgrounds
└── AndroidManifest.xml       # Activity declarations
```

### Pattern 1: Activity Navigation with Intent
**What:** Explicit Intent navigation between Activities
**When to use:** Lab 1 (Activity-based architecture)
**Example:**
```kotlin
// Source: Current implementation in lab-1/app/src/main/java/com/willpower/tracker/activities/OnboardActivity.kt
btnGetStarted.setOnClickListener {
    val intent = Intent(this, SignInActivity::class.java)
    startActivity(intent)
}
```

### Pattern 2: RecyclerView with Adapter
**What:** Efficient list display with view recycling
**When to use:** ALL scrollable lists
**Example:**
```kotlin
// Source: Current implementation in lab-1/app/src/main/java/com/willpower/tracker/activities/HomeActivity.kt
rvChallenges.layoutManager = LinearLayoutManager(this)
rvChallenges.adapter = ChallengeAdapter(
    challenges = challenges,
    onItemClick = { challenge -> /* handle click */ },
    onCheckChanged = { challenge, isChecked -> /* handle change */ }
)
```

### Pattern 3: TextInputLayout for Material Inputs
**What:** Material Design 3 text field wrapper with floating label
**When to use:** ALL text input fields
**Example:**
```xml
<!-- Source: Current implementation in lab-1/app/src/main/res/layout/activity_sign_in.xml -->
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/tilEmail"
    style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:hint="@string/email_hint"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/tvWelcome">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/etEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textEmailAddress" />
</com.google.android.material.textfield.TextInputLayout>
```

### Anti-Patterns to Avoid
- **ScrollView for lists:** Always use RecyclerView for any scrollable list of items
- **Inline adapter functions:** Create adapter class separately (ChallengeAdapter.kt is correct)
- **Missing inputType attributes:** Always specify inputType for keyboard behavior
- **Hardcoded strings:** Use string resources (already implemented correctly)
- **Touch targets < 48dp:** Current implementation uses 56dp buttons - correct

## Don't Hand-Roll

| Problem | Don't Build | Use Instead | Why |
|---------|-------------|-------------|-----|
| List display | ScrollView with many items | RecyclerView | Memory efficiency, view recycling, smooth scrolling |
| Form validation | Manual regex patterns | android.util.Patterns.EMAIL_ADDRESS | Well-tested, handles edge cases |
| Theme/styling | Custom color system | Material Design 3 theme | Consistency, dark mode support, dynamic color |
| Text fields | Plain EditText | TextInputLayout | Floating labels, error states, password toggle |

**Key insight:** The current implementation already follows all best practices. Do not reinvent standard components.

## Common Pitfalls

### Pitfall 1: Password Validation Mismatch
**What goes wrong:** LABS.md specifies "minimum 4 characters" but current implementation requires 6.
**Why it happens:** Security best practices recommend longer passwords, but lab requirements are specific.
**How to avoid:** Align with LABS.md requirement: change validation from `password.length < 6` to `password.length < 4`.
**Warning signs:** Tests fail due to 4-character passwords being rejected.

### Pitfall 2: Email Validation Too Strict
**What goes wrong:** Current implementation uses full regex validation vs LABS.md's "@ symbol only" requirement.
**Why it happens:** Following best practices vs following specifications.
**How to avoid:** LABS.md shows both "@ check" and proper validation are acceptable. Current implementation is fine.
**Warning signs:** User feedback indicates email validation is "too strict" (unlikely).

### Pitfall 3: Missing Finish() Calls
**What goes wrong:** Users can navigate back to onboarding/sign-in after proceeding.
**Why it happens:** Forgetting to call finish() when navigation should be one-way.
**How to avoid:** Current implementation correctly calls finish() in SignInActivity after successful sign-in.
**Warning signs:** Back button returns to previous screen unexpectedly.

### Pitfall 4: RecyclerView Item Updates
**What goes wrong:** UI doesn't update when data changes.
**Why it happens:** Forgetting to notify adapter of changes.
**How to avoid:** Use adapter.notifyDataSetChanged() or notifyItemChanged() - already implemented correctly in HomeActivity.
**Warning signs:** Checkbox state changes don't persist visually.

### Pitfall 5: Theme Inconsistency
**What goes wrong:** Some screens don't follow Material Design 3 theme.
**Why it happens:** Mixing Material 2 and 3 components or hardcoded colors.
**How to avoid:** Use theme attributes (e.g., ?attr/colorPrimary) not hardcoded colors. Current implementation is correct.
**Warning signs:** Colors look different across screens.

## Code Examples

Verified patterns from current implementation:

### Activity Navigation
```kotlin
// Source: /home/snow/Projects/Android_Main/lab-1/app/src/main/java/com/willpower/tracker/activities/SignInActivity.kt
// Navigate to HomeActivity and finish current activity
btnSignIn.setOnClickListener {
    if (validateInput()) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()  // Prevent going back to sign-in
    }
}
```

### Email Validation
```kotlin
// Source: /home/snow/Projects/Android_Main/lab-1/app/src/main/java/com/willpower/tracker/activities/SignInActivity.kt
// Proper email validation using Android's built-in pattern
if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
    tilEmail.error = getString(R.string.error_invalid_email)
    isValid = false
}
```

### RecyclerView Adapter with Callbacks
```kotlin
// Source: /home/snow/Projects/Android_Main/lab-1/app/src/main/java/com/willpower/tracker/adapters/ChallengeAdapter.kt
class ChallengeAdapter(
    private var challenges: List<Challenge>,
    private val onItemClick: (Challenge) -> Unit = {},
    private val onCheckChanged: (Challenge, Boolean) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>()
```

### Material 3 Button Styling
```xml
<!-- Source: /home/snow/Projects/Android_Main/lab-1/app/src/main/res/layout/activity_sign_in.xml -->
<com.google.android.material.button.MaterialButton
    android:id="@+id/btnSignIn"
    android:layout_width="0dp"
    android:layout_height="56dp"  <!-- Recommended touch target -->
    android:text="@string/btn_sign_in"
    android:textAppearance="@style/TextAppearance.Material3.TitleMedium"
    app:cornerRadius="28dp"  <!-- Material 3 recommended -->
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/tilPassword" />
```

## State of the Art

| Old Approach | Current Approach | When Changed | Impact |
|--------------|------------------|--------------|--------|
| Activities for all navigation | Single Activity + Fragments | Android Architecture Components | Lab 1 uses Activities (correct), Labs 3-7 migrate to Fragments |
| Material Design 2 | Material Design 3 | 2021 (Material You) | Current implementation uses Material 3 (correct) |
| findViewById everywhere | ViewBinding / DataBinding | Android Studio 3.6 | Lab 1 uses findViewById (acceptable for learning), Labs 4+ use ViewBinding |
| ScrollView for lists | RecyclerView | Since RecyclerView library | Current implementation uses RecyclerView (correct) |

**Deprecated/outdated:**
- **Activities as primary navigation:** Still valid for Lab 1, but modern apps use Navigation Component with Fragments (covered in Labs 3-4)
- **findViewBy ID everywhere:** Should migrate to ViewBinding (Lab 4), but findViewById is acceptable for learning basics
- **Plain EditText for forms:** Always wrap in TextInputLayout for Material Design (current implementation is correct)

## Open Questions

1. **Password validation length discrepancy**
   - What we know: LABS.md says "minimum 4 characters", current implementation requires 6
   - What's unclear: Should we follow LABS.md spec or maintain stronger security?
   - Recommendation: Align with LABS.md requirement (4 chars) to match acceptance criteria

2. **Email validation strictness**
   - What we know: LABS.md says "requires @ symbol", current implementation uses full regex
   - What's unclear: Is full validation acceptable or should we simplify to "@ check"?
   - Recommendation: Current implementation is acceptable - LABS.md shows both as valid

3. **Navigation completion state**
   - What we know: All navigation paths exist (Onboard→SignIn→SignUp→Home)
   - What's unclear: Are there any missing navigation flows or back-stack management issues?
   - Recommendation: Test all navigation paths on device/emulator to verify

4. **Material Design 3 completeness**
   - What we know: Theme is configured with Material 3 colors
   - What's unclear: Are all components properly themed and consistent?
   - Recommendation: Visual review of all screens for theme consistency

## Sources

### Primary (HIGH confidence)
- **Android Developers - RecyclerView Guide** - Official documentation for RecyclerView implementation
  - URL: https://developer.android.com/develop/ui/views/layout/recyclerview
  - Retrieved: 2026-02-17
  - Verified: RecyclerView pattern, ViewHolder pattern, Adapter implementation

- **Material Design 3 Components** - Official Material Design 3 specification
  - URL: https://m3.material.io/components
  - Retrieved: 2026-02-17
  - Verified: Component patterns, styling guidelines, Text Fields, Buttons, Cards

- **LABS.md** - Project-specific technical requirements
  - File: /home/snow/Projects/Android_Main/LABS.md
  - Contains acceptance criteria for Lab 1

- **Current Implementation** - Existing code in lab-1 directory
  - Path: /home/snow/Projects/Android_Main/lab-1/
  - Verified: All Activities, layouts, adapters, models exist

### Secondary (MEDIUM confidence)
- **Android Developers - Intents and Intent Filters** - Intent navigation patterns
  - URL: https://developer.android.com/guide/components/intents-filters
  - Referenced via: WebSearch result
  - Verified: Intent-based navigation is standard for Activity-to-Activity transitions

- **Material Design 3 Text Fields Guidelines** - TextInputLayout best practices
  - URL: https://m3.material.io/components/text-fields/guidelines
  - Referenced via: WebSearch result
  - Verified: OutlinedBox style, hint placement, error states

- **TextInputLayout API Reference** - Official API documentation
  - URL: https://developer.android.com/reference/com/google/android/material/textfield/TextInputLayout
  - Referenced via: WebSearch result
  - Verified: Component usage patterns

### Tertiary (LOW confidence)
- **Medium - Mastering Intents in Android** - Community article on Intent patterns
  - URL: https://medium.com/@RhoumaMaher/mastering-intents-in-android-a-comprehensive-guide-2244b4325532
  - Publication: Dec 20, 2024
  - Note: Community content, not official documentation
  - Verify with official docs before implementing advanced patterns

## Metadata

**Confidence breakdown:**
- Standard stack: HIGH - All versions verified from current build.gradle.kts and official Android documentation
- Architecture: HIGH - Current implementation follows Android best practices for Activity-based architecture
- Pitfalls: HIGH - Issues identified from direct code inspection and LABS.md comparison
- Code examples: HIGH - All examples verified from actual implementation files

**Research date:** 2026-02-17
**Valid until:** 2026-03-17 (30 days - Android UI patterns are stable)

**Current implementation status:**
- ✅ 4 Activities implemented
- ✅ RecyclerView with adapter
- ✅ Material Design 3 theme
- ✅ Sample data via Challenge.getSampleChallenges()
- ✅ Email validation (using android.util.Patterns.EMAIL_ADDRESS)
- ⚠️ Password validation (6 chars vs 4 chars requirement - needs alignment)
- ✅ Navigation flows implemented
- ✅ Touch targets meet 48dp minimum
- ✅ String resources externalized

**Next steps for planning:**
1. Verify password validation requirement alignment
2. Create comprehensive testing checklist (ADB testing)
3. Plan visual review of all screens
4. Document any missing LABS.md requirements
