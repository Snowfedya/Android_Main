package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.willpower.tracker.R
import com.willpower.tracker.database.entities.TaskEntity
import com.willpower.tracker.databinding.FragmentDetailsBinding
import com.willpower.tracker.viewmodel.DetailsViewModel
import kotlinx.coroutines.launch

/**
 * DetailsFragment displays task details and focus mode controls.
 * Uses DetailsViewModel with task loading from Room database.
 */
class DetailsFragment : Fragment() {

    private val TAG = "DetailsFragment"
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    // Create ViewModel with custom factory for taskId parameter
    private val viewModel: DetailsViewModel by lazy {
        DetailsViewModel.Factory(
            application = requireActivity().application,
            taskId = args.challengeId.toLong() // Convert Int to Long
        ).create(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with taskId: ${args.challengeId}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        setupToolbar()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.title = args.challengeTitle
    }

    private fun setupClickListeners() {
        binding.fabEdit.setOnClickListener {
            viewModel.task.value?.let { task ->
                val action = DetailsFragmentDirections.actionDetailsToAddEdit(challengeId = task.id)
                findNavController().navigate(action)
            }
        }

        binding.btnStartFocusMode.setOnClickListener {
            viewModel.task.value?.let { task ->
                startTimer(task.recommendedDurationMin)
            }
        }

        binding.btnMarkComplete.setOnClickListener {
            viewModel.markComplete()
        }
    }

    private fun startTimer(durationMinutes: Int) {
        viewModel.startTimer(durationMinutes)
        Toast.makeText(
            requireContext(),
            "Фокус-режим запущен на $durationMinutes минут",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Collect task data
                launch {
                    viewModel.task.collect { task ->
                        task?.let { displayTaskDetails(it) }
                    }
                }

                // Collect completion message
                launch {
                    viewModel.completionMessage.collect { message ->
                        message?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                            viewModel.clearCompletionMessage()
                            // Navigate back after completion
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    private fun displayTaskDetails(task: TaskEntity) {
        binding.tvTitle.text = task.title
        binding.tvDescription.text = task.description
        binding.tvTechnique.text = "Техника: ${task.techniqueName}"
        binding.tvDuration.text = formatDuration(task.recommendedDurationMin)
        binding.tvDifficulty.text = task.difficulty
        binding.tvCategory.text = task.category

        // Enable focus mode button
        binding.btnStartFocusMode.isEnabled = true
        binding.btnMarkComplete.isEnabled = true
    }

    private fun formatDuration(minutes: Int): String {
        return when {
            minutes < 60 -> "$minutes минут"
            minutes == 60 -> "1 час"
            else -> "${minutes / 60} часов ${minutes % 60} минут"
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
