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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.willpower.tracker.R
import com.willpower.tracker.adapters.ChallengeAdapter
import com.willpower.tracker.database.entities.AiAdviceEntity
import com.willpower.tracker.database.entities.TaskEntity
import com.willpower.tracker.databinding.FragmentHomeBinding
import com.willpower.tracker.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

/**
 * HomeFragment displays list of challenges and AI advice.
 * Uses HomeViewModel with Flow-based reactive data from Room database.
 */
class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel with viewModels() delegate (no custom factory needed)
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: ChallengeAdapter

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
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ChallengeAdapter(
            onItemClick = { challenge ->
                val action = HomeFragmentDirections.actionHomeToDetails(
                    challengeId = challenge.id, // Keep as Int for navigation
                    challengeTitle = challenge.title
                )
                findNavController().navigate(action)
            },
            onCheckChanged = { challenge, isChecked ->
                // Handle completion status change
                // In a full implementation, this would update the database
                val message = if (isChecked) {
                    "Отлично! ${challenge.title} выполнено!"
                } else {
                    "${challenge.title} отмечено как невыполненное"
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddEdit(challengeId = -1)
            findNavController().navigate(action)
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }

        binding.btnReports.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_reports)
        }
        
        binding.btnAiAnalysis.setOnClickListener {
            try {
                viewModel.analyzeTasks()
            } catch (e: Exception) {
                Log.e(TAG, "Error triggering analysis", e)
                showError("Ошибка: ${e.message}")
            }
        }

        // Swipe to refresh
        (binding.swipeRefresh as? SwipeRefreshLayout)?.setOnRefreshListener {
            viewModel.refreshAdvice()
        }
    }

    private fun observeViewModel() {
        // Collect Flow data in lifecycle-aware manner
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Collect tasks from Room database
                launch(kotlinx.coroutines.Dispatchers.Main) {
                    viewModel.tasksFlow.collect { tasks ->
                        Log.d(TAG, "Tasks updated: ${tasks.size} items")
                        updateChallengesList(tasks)
                    }
                }

                // Collect latest advice from Room database
                launch(kotlinx.coroutines.Dispatchers.Main) {
                    viewModel.latestAdvice.collect { advice ->
                        Log.d(TAG, "Advice updated: ${advice?.text?.take(50)}...")
                        updateAdviceDisplay(advice)
                    }
                }
                
                // Collect analysis result
                launch(kotlinx.coroutines.Dispatchers.Main) {
                    viewModel.analysisResult.collect { result ->
                        result?.let {
                            Log.d(TAG, "Showing analysis dialog")
                            showAnalysisDialog(it)
                            viewModel.clearAnalysis()
                        }
                    }
                }

                // Collect loading state
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        (binding.swipeRefresh as? SwipeRefreshLayout)?.isRefreshing = isLoading
                    }
                }

                // Collect error messages
                launch {
                    viewModel.errorMessage.collect { error ->
                        error?.let {
                            showError(it)
                            viewModel.clearError()
                        }
                    }
                }
            }
        }
    }

    private fun updateChallengesList(tasks: List<TaskEntity>) {
        // Convert TaskEntity to Challenge model for adapter
        val challenges = tasks.map { entity ->
            com.willpower.tracker.models.Challenge(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                technique = entity.techniqueName,
                durationMinutes = entity.recommendedDurationMin,
                difficulty = entity.difficulty,
                category = entity.category,
                isCompleted = false // Could be enhanced with completion tracking
            )
        }
        adapter.submitList(challenges)
    }

    private fun updateAdviceDisplay(advice: AiAdviceEntity?) {
        val adviceText = if (advice != null) {
            advice.text
        } else {
            "Потяните вниз, чтобы получить совет"
        }
        binding.tvAdviceText.text = adviceText
    }
    
    private fun showAnalysisDialog(analysis: String) {
        if (!isAdded || isDetached) return
        
        try {
            com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Анализ задач от AI")
                .setMessage(analysis)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        } catch (e: Exception) {
            Log.e(TAG, "Error showing analysis dialog", e)
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("OK") {}
            .show()
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
