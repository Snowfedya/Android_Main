package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.willpower.tracker.R
import com.willpower.tracker.adapters.ChallengeAdapter
import com.willpower.tracker.databinding.FragmentHomeBinding
import com.willpower.tracker.models.Challenge
import com.willpower.tracker.repository.AdviceRepository
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChallengeAdapter
    private val adviceRepository = AdviceRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadData()
        loadAdvice()
    }

    private fun setupRecyclerView() {
        adapter = ChallengeAdapter(Challenge.getSampleChallenges())
        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter
    }

    private fun loadData() {
        // Lab 5 focused on networking, but we keep local challenges display
        binding.tvChallengesTitle.text = getString(R.string.challenges_title, Challenge.getSampleChallenges().size)
    }

    private fun loadAdvice() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                binding.tvQuote.text = "Загрузка совета..."
                binding.tvQuoteAuthor.visibility = View.GONE
                
                val result = adviceRepository.getRandomAdvice()
                result.fold(
                    onSuccess = { advice ->
                        binding.tvQuote.text = advice.text
                        binding.tvQuoteAuthor.text = "— WillPower AI"
                        binding.tvQuoteAuthor.visibility = View.VISIBLE
                    },
                    onFailure = { e ->
                        Log.e(TAG, "Error loading advice", e)
                        binding.tvQuote.text = "Сделай 2 минуты прямо сейчас!"
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error loading advice", e)
                binding.tvQuote.text = "Сделай 2 минуты прямо сейчас!"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
