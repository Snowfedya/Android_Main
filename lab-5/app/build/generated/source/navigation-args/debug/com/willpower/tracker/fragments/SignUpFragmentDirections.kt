package com.willpower.tracker.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.willpower.tracker.R
import kotlin.Int
import kotlin.String

public class SignUpFragmentDirections private constructor() {
  private data class ActionSignUpToSignIn(
    public val userName: String? = null,
    public val userEmail: String? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_signUp_to_signIn

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("userName", this.userName)
        result.putString("userEmail", this.userEmail)
        return result
      }
  }

  public companion object {
    public fun actionSignUpToSignIn(userName: String? = null, userEmail: String? = null):
        NavDirections = ActionSignUpToSignIn(userName, userEmail)
  }
}
