package com.willpower.tracker.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.willpower.tracker.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout
    private lateinit var tilAge: TextInputLayout
    private lateinit var tilGender: TextInputLayout
    
    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var actvGender: AutoCompleteTextView
    
    private lateinit var btnRegister: Button
    private lateinit var btnGoToSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()
        setupGenderDropdown()
        setupClickListeners()
    }

    private fun initViews() {
        tilName = findViewById(R.id.tilName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)
        tilAge = findViewById(R.id.tilAge)
        tilGender = findViewById(R.id.tilGender)
        
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etAge = findViewById(R.id.etAge)
        actvGender = findViewById(R.id.actvGender)
        
        btnRegister = findViewById(R.id.btnRegister)
        btnGoToSignIn = findViewById(R.id.btnGoToSignIn)
    }

    private fun setupGenderDropdown() {
        val genders = arrayOf(
            getString(R.string.gender_male),
            getString(R.string.gender_female),
            getString(R.string.gender_other)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genders)
        actvGender.setAdapter(adapter)
    }

    private fun setupClickListeners() {
        btnRegister.setOnClickListener {
            if (validateInput()) {
                Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        btnGoToSignIn.setOnClickListener {
            finish()
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
}
