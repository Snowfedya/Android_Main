package com.willpower.tracker.activities

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.willpower.tracker.R
import com.willpower.tracker.adapters.ChallengeAdapter
import com.willpower.tracker.models.Challenge

class HomeActivity : BaseActivity() {

    private lateinit var rvChallenges: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var adapter: ChallengeAdapter
    
    private val challenges = Challenge.getSampleChallenges().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initViews() {
        rvChallenges = findViewById(R.id.rvChallenges)
        fabAdd = findViewById(R.id.fabAdd)
    }

    private fun setupRecyclerView() {
        adapter = ChallengeAdapter(
            challenges = challenges,
            onItemClick = { challenge ->
                Toast.makeText(
                    this,
                    "Открыть: ${challenge.title}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onCheckChanged = { challenge, isChecked ->
                val index = challenges.indexOfFirst { it.id == challenge.id }
                if (index != -1) {
                    challenges[index] = challenge.copy(isCompleted = isChecked)
                    adapter.notifyItemChanged(index)
                    
                    val message = if (isChecked) {
                        "Отлично! ${challenge.title} выполнено!"
                    } else {
                        "${challenge.title} отмечено как невыполненное"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        )
        
        rvChallenges.layoutManager = LinearLayoutManager(this)
        rvChallenges.adapter = adapter
    }

    private fun setupClickListeners() {
        fabAdd.setOnClickListener {
            Toast.makeText(this, "Добавить новый челлендж", Toast.LENGTH_SHORT).show()
        }
    }
}
