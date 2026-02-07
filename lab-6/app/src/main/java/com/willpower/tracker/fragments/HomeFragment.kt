package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.willpower.tracker.adapters.ChallengeAdapter
import com.willpower.tracker.databinding.FragmentHomeBinding
import com.willpower.tracker.models.Challenge

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChallengeAdapter
    private val challenges = Challenge.getSampleChallenges().toMutableList()

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = ChallengeAdapter(
            challenges = challenges,
            onItemClick = { challenge ->
                val action = HomeFragmentDirections.actionHomeToDetails(
                    challengeId = challenge.id,
                    challengeTitle = challenge.title
                )
                findNavController().navigate(action)
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
        
        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            Toast.makeText(requireContext(), "Добавить новый челлендж", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToSettings())
        }
        
        binding.btnReports.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToReports())
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
        _binding = null
    }
}
