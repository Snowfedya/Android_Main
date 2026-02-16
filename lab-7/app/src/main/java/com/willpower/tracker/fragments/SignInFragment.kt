package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.willpower.tracker.R
import com.willpower.tracker.databinding.FragmentSignInBinding

/**
 * SignInFragment - User authentication with Navigation Component.
 * Receives user data from SignUpFragment via Safe Args.
 * FIXED: Added Safe Args support and ViewBinding.
 */
class SignInFragment : Fragment() {

    private val TAG = "SignInFragment"
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    // FIX: Add Safe Args to receive data from SignUpFragment
    private val args: SignInFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        // FIX: Display user data from SignUpFragment
        displayUserData()
        setupClickListeners()
    }

    private fun displayUserData() {
        // FIX: Receive data from SignUpFragment via Safe Args
        args.userName?.let { name ->
            args.userEmail?.let { email ->
                binding.tvWelcome.text = "Добро пожаловать, $name!"
                binding.tvWelcome.visibility = View.VISIBLE
                binding.etEmail.setText(email)  // Pre-fill email from registration
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSignIn.setOnClickListener {
            if (validateInput()) {
                findNavController().navigate(R.id.action_signIn_to_home)
            }
        }

        binding.btnGoToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.error_invalid_email)
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (password.length < 6) {
            binding.tilPassword.error = getString(R.string.error_password_short)
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        return isValid
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        _binding = null  // FIX: Prevent ViewBinding memory leak
    }
}
