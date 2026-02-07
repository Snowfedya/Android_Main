package com.willpower.tracker.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.willpower.tracker.R
import kotlin.Int
import kotlin.String

public class OnboardFragmentDirections private constructor() {
  private data class ActionOnboardToSignIn(
    public val userName: String? = null,
    public val userEmail: String? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_onboard_to_signIn

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("userName", this.userName)
        result.putString("userEmail", this.userEmail)
        return result
      }
  }

  public companion object {
    public fun actionOnboardToSignIn(userName: String? = null, userEmail: String? = null):
        NavDirections = ActionOnboardToSignIn(userName, userEmail)
  }
}
