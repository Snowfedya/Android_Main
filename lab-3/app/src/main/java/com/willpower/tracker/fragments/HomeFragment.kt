package com.willpower.tracker.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.willpower.tracker.R
import com.willpower.tracker.adapters.ChallengeAdapter
import com.willpower.tracker.models.Challenge

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    interface HomeListener {
        fun onLogout()
    }

    private var listener: HomeListener? = null
    private lateinit var adapter: ChallengeAdapter
    private val challenges = Challenge.getSampleChallenges().toMutableList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach() called")
        listener = context as? HomeListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() called")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        setupRecyclerView(view)
        setupClickListeners(view)
    }

    private fun setupRecyclerView(view: View) {
        val rvChallenges = view.findViewById<RecyclerView>(R.id.rvChallenges)
        
        adapter = ChallengeAdapter(
            challenges = challenges,
            onItemClick = { challenge ->
                Toast.makeText(
                    requireContext(),
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
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        )
        
        rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        rvChallenges.adapter = adapter
    }

    private fun setupClickListeners(view: View) {
        view.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            Toast.makeText(requireContext(), "Добавить новый челлендж", Toast.LENGTH_SHORT).show()
        }
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
