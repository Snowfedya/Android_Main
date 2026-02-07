package com.willpower.tracker.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.willpower.tracker.R

class OnboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        
        btnGetStarted.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
