package com.willpower.tracker.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.willpower.tracker.R
import kotlin.Int
import kotlin.String

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeToDetails(
    public val challengeId: Int,
    public val challengeTitle: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_home_to_details

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("challengeId", this.challengeId)
        result.putString("challengeTitle", this.challengeTitle)
        return result
      }
  }

  public companion object {
    public fun actionHomeToDetails(challengeId: Int, challengeTitle: String): NavDirections =
        ActionHomeToDetails(challengeId, challengeTitle)
  }
}
