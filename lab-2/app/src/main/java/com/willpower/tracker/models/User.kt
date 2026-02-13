package com.willpower.tracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val email: String,
    val password: String,
    val age: Int = 0,
    val gender: String = ""
) : Parcelable
