package com.willpower.tracker.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DetailsFragmentArgs(
  public val challengeId: Int,
  public val challengeTitle: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("challengeId", this.challengeId)
    result.putString("challengeTitle", this.challengeTitle)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("challengeId", this.challengeId)
    result.set("challengeTitle", this.challengeTitle)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DetailsFragmentArgs {
      bundle.setClassLoader(DetailsFragmentArgs::class.java.classLoader)
      val __challengeId : Int
      if (bundle.containsKey("challengeId")) {
        __challengeId = bundle.getInt("challengeId")
      } else {
        throw IllegalArgumentException("Required argument \"challengeId\" is missing and does not have an android:defaultValue")
      }
      val __challengeTitle : String?
      if (bundle.containsKey("challengeTitle")) {
        __challengeTitle = bundle.getString("challengeTitle")
        if (__challengeTitle == null) {
          throw IllegalArgumentException("Argument \"challengeTitle\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"challengeTitle\" is missing and does not have an android:defaultValue")
      }
      return DetailsFragmentArgs(__challengeId, __challengeTitle)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DetailsFragmentArgs {
      val __challengeId : Int?
      if (savedStateHandle.contains("challengeId")) {
        __challengeId = savedStateHandle["challengeId"]
        if (__challengeId == null) {
          throw IllegalArgumentException("Argument \"challengeId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"challengeId\" is missing and does not have an android:defaultValue")
      }
      val __challengeTitle : String?
      if (savedStateHandle.contains("challengeTitle")) {
        __challengeTitle = savedStateHandle["challengeTitle"]
        if (__challengeTitle == null) {
          throw IllegalArgumentException("Argument \"challengeTitle\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"challengeTitle\" is missing and does not have an android:defaultValue")
      }
      return DetailsFragmentArgs(__challengeId, __challengeTitle)
    }
  }
}
