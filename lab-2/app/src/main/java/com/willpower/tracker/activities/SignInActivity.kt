package com.willpower.tracker.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.willpower.tracker.R
import com.willpower.tracker.models.User

class SignInActivity : BaseActivity() {

    companion object {
        const val REQUEST_SIGN_UP = 1001
        const val EXTRA_USER_NAME = "extra_user_name"
        const val EXTRA_USER_EMAIL = "extra_user_email"
        const val EXTRA_USER_OBJECT = "extra_user_object"
    }

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnSignIn: Button
    private lateinit var btnGoToSignUp: Button
    private lateinit var tvWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnGoToSignUp = findViewById(R.id.btnGoToSignUp)
        tvWelcome = findViewById(R.id.tvWelcome)
    }

    private fun setupClickListeners() {
        btnSignIn.setOnClickListener {
            if (validateInput()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            @Suppress("DEPRECATION")
            startActivityForResult(intent, REQUEST_SIGN_UP)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == REQUEST_SIGN_UP && resultCode == RESULT_OK && data != null) {
            val userName = data.getStringExtra(EXTRA_USER_NAME)
            val userEmail = data.getStringExtra(EXTRA_USER_EMAIL)
            
            @Suppress("DEPRECATION")
            val user = data.getParcelableExtra<User>(EXTRA_USER_OBJECT)
            
            if (user != null) {
                tvWelcome.text = "Добро пожаловать, ${user.name}!\nEmail: ${user.email}"
                tvWelcome.visibility = View.VISIBLE
                etEmail.setText(user.email)
            } else if (userName != null && userEmail != null) {
                tvWelcome.text = "Добро пожаловать, $userName!\nEmail: $userEmail"
                tvWelcome.visibility = View.VISIBLE
                etEmail.setText(userEmail)
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

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

        return isValid
    }
}
