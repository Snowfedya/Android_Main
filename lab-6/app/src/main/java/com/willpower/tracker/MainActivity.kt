package com.willpower.tracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.willpower.tracker.fragments.HomeFragment
import com.willpower.tracker.fragments.OnboardFragment
import com.willpower.tracker.fragments.SignInFragment
import com.willpower.tracker.fragments.SignUpFragment
import com.willpower.tracker.models.User

class MainActivity : AppCompatActivity(), 
    OnboardFragment.OnboardListener,
    SignInFragment.SignInListener,
    SignUpFragment.SignUpListener,
    HomeFragment.HomeListener {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate() called")

        if (savedInstanceState == null) {
            navigateToOnboard()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }
    }

    fun navigateToOnboard() {
        replaceFragment(OnboardFragment(), addToBackStack = false)
    }

    fun navigateToSignIn(user: User? = null) {
        val fragment = SignInFragment.newInstance(user)
        replaceFragment(fragment)
    }

    fun navigateToSignUp() {
        replaceFragment(SignUpFragment())
    }

    fun navigateToHome() {
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        replaceFragment(HomeFragment(), addToBackStack = false)
    }

    override fun onGetStartedClicked() {
        navigateToSignIn()
    }

    override fun onSignInSuccess() {
        navigateToHome()
    }

    override fun onSignUpClicked() {
        navigateToSignUp()
    }

    override fun onSignUpSuccess(user: User) {
        navigateToSignIn(user)
    }

    override fun onBackToSignIn() {
        supportFragmentManager.popBackStack()
    }

    override fun onLogout() {
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        navigateToOnboard()
    }
}
