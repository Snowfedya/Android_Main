package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.willpower.tracker.R
import com.willpower.tracker.databinding.FragmentSignUpBinding

/**
 * SignUpFragment - User registration with Navigation Component.
 * Uses Safe Args to pass user data back to SignInFragment.
 * FIXED: Removed dead callback interface, now uses pure Navigation Component.
 */
class SignUpFragment : Fragment() {

    private val TAG = "SignUpFragment"
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        setupGenderDropdown()
        setupClickListeners()
    }

    private fun setupGenderDropdown() {
        val genders = arrayOf(
            getString(R.string.gender_male),
            getString(R.string.gender_female),
            getString(R.string.gender_other)
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        binding.actvGender.setAdapter(adapter)
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            if (validateInput()) {
                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()

                Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show()

                // FIX: Use Navigation Component with Safe Args (Lab 4 pattern)
                // Pass user data directly to SignInFragment
                val action = SignUpFragmentDirections.actionSignUpToSignIn(
                    userName = name,
                    userEmail = email
                )
                findNavController().navigate(action)
            }
        }

        binding.btnGoToSignIn.setOnClickListener {
            // FIX: Use Navigation Component to go back to SignIn
            findNavController().popBackStack()
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        val age = binding.etAge.text.toString().trim()

        if (name.isEmpty()) {
            binding.tilName.error = getString(R.string.error_empty_field)
            isValid = false
        } else {
            binding.tilName.error = null
        }

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

        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (confirmPassword != password) {
            binding.tilConfirmPassword.error = getString(R.string.error_passwords_not_match)
            isValid = false
        } else {
            binding.tilConfirmPassword.error = null
        }

        if (age.isEmpty()) {
            binding.tilAge.error = getString(R.string.error_empty_field)
            isValid = false
        } else {
            binding.tilAge.error = null
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        _binding = null  // FIX: Prevent ViewBinding memory leak
    }
}
