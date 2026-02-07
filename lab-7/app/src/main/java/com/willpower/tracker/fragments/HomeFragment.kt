package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.willpower.tracker.adapters.ChallengeEntityAdapter
import com.willpower.tracker.database.ChallengeEntity
import com.willpower.tracker.databinding.FragmentHomeBinding
import com.willpower.tracker.viewmodel.ChallengeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ChallengeViewModel by viewModels()
    private lateinit var adapter: ChallengeEntityAdapter

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
        observeViewModel()
        setupClickListeners()
        
        viewModel.performColdStart()
    }

    private fun setupRecyclerView() {
        adapter = ChallengeEntityAdapter(
            onItemClick = { challenge ->
                val action = HomeFragmentDirections.actionHomeToDetails(
                    challengeId = challenge.id,
                    challengeTitle = challenge.title
                )
                findNavController().navigate(action)
            },
            onCheckChanged = { challenge, isChecked ->
                if (isChecked) {
                    viewModel.completeChallenge(challenge.id)
                    showToast("Отлично! ${challenge.title} выполнено!")
                } else {
                    viewModel.uncompleteChallenge(challenge.id)
                    showToast("${challenge.title} отмечено как невыполненное")
                }
            }
        )
        
        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.allChallenges.collectLatest { challenges ->
                        adapter.submitList(challenges)
                        updateStats(challenges)
                    }
                }
                
                launch {
                    viewModel.isLoading.collectLatest { isLoading ->
                        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                    }
                }
                
                launch {
                    viewModel.todayCompletions.collectLatest { count ->
                        binding.tvTodayCompleted.text = "Сегодня выполнено: $count"
                    }
                }
            }
        }
    }
    
    private fun updateStats(challenges: List<ChallengeEntity>) {
        val total = challenges.size
        val completed = challenges.count { it.isCompleted }
        binding.tvStats.text = "Прогресс: $completed/$total"
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            showAddChallengeDialog()
        }
    }
    
    private fun showAddChallengeDialog() {
        val newChallenge = ChallengeEntity(
            title = "Новый челлендж",
            description = "Описание нового челленджа",
            category = "Развитие",
            durationMinutes = 15,
            streak = 0
        )
        viewModel.addChallenge(newChallenge)
        showToast("Добавлен новый челлендж")
    }
    
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
