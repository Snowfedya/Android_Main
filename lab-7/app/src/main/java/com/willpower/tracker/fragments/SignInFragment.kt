package com.willpower.tracker.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.willpower.tracker.R
import com.willpower.tracker.models.User

class SignInFragment : Fragment() {

    private val TAG = "SignInFragment"

    companion object {
        private const val ARG_USER = "arg_user"

        fun newInstance(user: User? = null): SignInFragment {
            return SignInFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)
                }
            }
        }
    }

    interface SignInListener {
        fun onSignInSuccess()
        fun onSignUpClicked()
    }

    private var listener: SignInListener? = null
    private var registeredUser: User? = null

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var tvWelcome: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach() called")
        listener = context as? SignInListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        @Suppress("DEPRECATION")
        registeredUser = arguments?.getParcelable(ARG_USER)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() called")
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        initViews(view)
        setupClickListeners(view)
        displayUserData()
    }

    private fun initViews(view: View) {
        tilEmail = view.findViewById(R.id.tilEmail)
        tilPassword = view.findViewById(R.id.tilPassword)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        tvWelcome = view.findViewById(R.id.tvWelcome)
    }

    private fun setupClickListeners(view: View) {
        view.findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            if (validateInput()) {
                listener?.onSignInSuccess()
            }
        }

        view.findViewById<Button>(R.id.btnGoToSignUp).setOnClickListener {
            listener?.onSignUpClicked()
        }
    }

    private fun displayUserData() {
        registeredUser?.let { user ->
            tvWelcome.text = "Добро пожаловать, ${user.name}!\nEmail: ${user.email}"
            tvWelcome.visibility = View.VISIBLE
            etEmail.setText(user.email)
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
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach() called")
        listener = null
    }
}
