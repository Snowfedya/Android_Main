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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.willpower.tracker.R
import com.willpower.tracker.models.User

class SignUpFragment : Fragment() {

    private val TAG = "SignUpFragment"

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout
    private lateinit var tilAge: TextInputLayout
    
    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var actvGender: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() called")
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        initViews(view)
        setupGenderDropdown()
        setupClickListeners()
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.tilName)
        tilEmail = view.findViewById(R.id.tilEmail)
        tilPassword = view.findViewById(R.id.tilPassword)
        tilConfirmPassword = view.findViewById(R.id.tilConfirmPassword)
        tilAge = view.findViewById(R.id.tilAge)
        
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        etAge = view.findViewById(R.id.etAge)
        actvGender = view.findViewById(R.id.actvGender)
    }

    private fun setupGenderDropdown() {
        val genders = arrayOf(
            getString(R.string.gender_male),
            getString(R.string.gender_female),
            getString(R.string.gender_other)
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        actvGender.setAdapter(adapter)
    }

    private fun setupClickListeners() {
        view?.findViewById<Button>(R.id.btnRegister)?.setOnClickListener {
            if (validateInput()) {
                Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        view?.findViewById<Button>(R.id.btnGoToSignIn)?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()
        val age = etAge.text.toString().trim()

        if (name.isEmpty()) {
            tilName.error = getString(R.string.error_empty_field)
            isValid = false
        } else {
            tilName.error = null
        }

        if (email.isEmpty()) {
            tilEmail.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = getString(R.string.error_invalid_email)
            isValid = false
        } else {
            tilEmail.error = null
        }

        if (password.isEmpty()) {
            tilPassword.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (password.length < 6) {
            tilPassword.error = getString(R.string.error_password_short)
            isValid = false
        } else {
            tilPassword.error = null
        }

        if (confirmPassword.isEmpty()) {
            tilConfirmPassword.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (confirmPassword != password) {
            tilConfirmPassword.error = getString(R.string.error_passwords_not_match)
            isValid = false
        } else {
            tilConfirmPassword.error = null
        }

        if (age.isEmpty()) {
            tilAge.error = getString(R.string.error_empty_field)
            isValid = false
        } else {
            tilAge.error = null
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
    }
}
