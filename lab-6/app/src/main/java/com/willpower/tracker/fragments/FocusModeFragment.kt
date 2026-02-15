package com.willpower.tracker.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.willpower.tracker.R
import com.willpower.tracker.databinding.FragmentFocusModeBinding
import com.willpower.tracker.models.Challenge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FocusModeFragment : Fragment() {

    private val TAG = "FocusModeFragment"
    private var _binding: FragmentFocusModeBinding? = null
    private val binding get() = _binding!!

    private val args: FocusModeFragmentArgs by navArgs()
    private lateinit var challenge: Challenge
    private var countDownTimer: CountDownTimer? = null
    private var timeRemainingMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        challenge = Challenge.getSampleChallenges().find { it.id == args.challengeId }
            ?: Challenge.getSampleChallenges().first()

        // Convert minutes to milliseconds
        timeRemainingMillis = args.durationMinutes * 60 * 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentFocusModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        setupFullscreenMode()
        displayChallengeInfo()
        startTimer()
        setupClickListeners()
    }

    private fun setupFullscreenMode() {
        // Hide system UI
        @Suppress("DEPRECATION")
        val decorView = requireActivity().window.decorView
        @Suppress("DEPRECATION")
        val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        @Suppress("DEPRECATION")
        decorView.systemUiVisibility = uiOptions

        // Keep screen on
        binding.root.keepScreenOn = true
    }

    private fun displayChallengeInfo() {
        binding.tvChallengeTitle.text = challenge.title
        binding.tvTimer.text = formatTime(timeRemainingMillis)
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeRemainingMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingMillis = millisUntilFinished
                binding.tvTimer.text = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00"
                onSessionCompleted()
            }
        }.start()
    }

    private fun setupClickListeners() {
        binding.btnEndSession.setOnClickListener {
            endSession()
        }
    }

    private fun onSessionCompleted() {
        Toast.makeText(
            requireContext(),
            "Сессия завершена! ${challenge.title} выполнено!",
            Toast.LENGTH_LONG
        ).show()

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            requireActivity().runOnUiThread {
                findNavController().navigateUp()
            }
        }
    }

    private fun endSession() {
        countDownTimer?.cancel()
        findNavController().navigateUp()
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
        setupFullscreenMode()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
        countDownTimer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        countDownTimer?.cancel()
        _binding = null
    }
}
