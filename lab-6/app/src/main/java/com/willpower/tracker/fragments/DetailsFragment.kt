package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.willpower.tracker.R
import com.willpower.tracker.databinding.FragmentDetailsBinding
import com.willpower.tracker.models.Challenge

class DetailsFragment : Fragment() {

    private val TAG = "DetailsFragment"
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()
    private var challenge: Challenge? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        challenge = Challenge.getSampleChallenges().find { it.id == args.challengeId }
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
        displayTaskDetails()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.title = args.challengeTitle
    }

    private fun setupClickListeners() {
        // Edit button is for Lab 7, hide it in Lab 6
        binding.fabEdit.visibility = View.GONE

        binding.btnStartFocusMode.setOnClickListener {
            challenge?.let {
                val action = DetailsFragmentDirections.actionDetailsToFocusMode(
                    challengeId = it.id,
                    durationMinutes = it.durationMinutes
                )
                findNavController().navigate(action)
            }
        }

        binding.btnMarkComplete.setOnClickListener {
            Toast.makeText(requireContext(), "Задание выполнено!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun displayTaskDetails() {
        challenge?.let { task ->
            binding.tvTitle.text = task.title
            binding.tvDescription.text = task.description
            binding.tvTechnique.text = "Техника: Основана на методике Willpower"
            binding.tvDuration.text = formatDuration(task.durationMinutes)
            binding.tvDifficulty.text = "Средний"
            binding.tvCategory.text = task.category
            
            binding.btnStartFocusMode.isEnabled = true
            binding.btnMarkComplete.isEnabled = true
        } ?: run {
            binding.tvTitle.text = "Задача не найдена"
            binding.btnStartFocusMode.isEnabled = false
            binding.btnMarkComplete.isEnabled = false
        }
    }

    private fun formatDuration(minutes: Int): String {
        return when {
            minutes < 60 -> "$minutes минут"
            minutes == 60 -> "1 час"
            else -> "${minutes / 60} часов ${minutes % 60} минут"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        _binding = null
    }
}
