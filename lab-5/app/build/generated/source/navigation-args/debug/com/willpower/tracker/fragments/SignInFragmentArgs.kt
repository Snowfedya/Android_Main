package com.willpower.tracker.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import kotlin.String
import kotlin.jvm.JvmStatic

public data class SignInFragmentArgs(
  public val userName: String? = null,
  public val userEmail: String? = null,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("userName", this.userName)
    result.putString("userEmail", this.userEmail)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("userName", this.userName)
    result.set("userEmail", this.userEmail)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): SignInFragmentArgs {
      bundle.setClassLoader(SignInFragmentArgs::class.java.classLoader)
      val __userName : String?
      if (bundle.containsKey("userName")) {
        __userName = bundle.getString("userName")
      } else {
        __userName = null
      }
      val __userEmail : String?
      if (bundle.containsKey("userEmail")) {
        __userEmail = bundle.getString("userEmail")
      } else {
        __userEmail = null
      }
      return SignInFragmentArgs(__userName, __userEmail)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): SignInFragmentArgs {
      val __userName : String?
      if (savedStateHandle.contains("userName")) {
        __userName = savedStateHandle["userName"]
      } else {
        __userName = null
      }
      val __userEmail : String?
      if (savedStateHandle.contains("userEmail")) {
        __userEmail = savedStateHandle["userEmail"]
      } else {
        __userEmail = null
      }
      return SignInFragmentArgs(__userName, __userEmail)
    }
  }
}
