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
    private lateinit var challenge: Challenge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        // Find the challenge by ID
        challenge = Challenge.getSampleChallenges().find { it.id == args.challengeId }
            ?: Challenge.getSampleChallenges().first()
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
        displayChallengeDetails()
        setupClickListeners()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.title = args.challengeTitle
    }

    private fun displayChallengeDetails() {
        binding.tvTitle.text = challenge.title
        binding.tvDescription.text = challenge.description
        binding.tvTechnique.text = "Техника: Willpower Instinct"
        binding.tvDuration.text = formatDuration(challenge.durationMinutes)
        binding.tvDifficulty.text = challenge.difficulty
        binding.tvCategory.text = challenge.category
    }

    private fun setupClickListeners() {
        binding.btnStartFocusMode.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsToFocusMode(
                challengeId = challenge.id,
                durationMinutes = challenge.durationMinutes
            )
            findNavController().navigate(action)
        }

        binding.btnMarkComplete.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Отлично! ${challenge.title} выполнено!",
                Toast.LENGTH_SHORT
            ).show()
        }
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
