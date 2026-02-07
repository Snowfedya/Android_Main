package com.willpower.tracker.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.willpower.tracker.R

public class SignInFragmentDirections private constructor() {
  public companion object {
    public fun actionSignInToSignUp(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signIn_to_signUp)

    public fun actionSignInToHome(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signIn_to_home)
  }
}
