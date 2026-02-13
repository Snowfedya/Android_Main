package com.willpower.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.willpower.tracker.adapters.CharacterAdapter
import com.willpower.tracker.database.AppDatabase
import com.willpower.tracker.databinding.FragmentHomeBinding
import com.willpower.tracker.repository.CharacterRepository
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var adapter: CharacterAdapter
    private lateinit var repository: CharacterRepository

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
        
        val database = AppDatabase.getDatabase(requireContext())
        repository = CharacterRepository(database)
        
        setupRecyclerView()
        observeData()
        
        // Cold Start
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                repository.coldStart()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Ошибка сети: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.swipeRefresh.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    repository.refreshCharacters()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Ошибка обновления: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CharacterAdapter()
        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repository.allCharacters.collect { characters ->
                adapter.updateData(characters)
                binding.tvChallengesTitle.text = "Персонажи (${characters.size})"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
