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
import com.willpower.tracker.adapters.CharacterAdapter
import com.willpower.tracker.databinding.FragmentHomeBinding
import com.willpower.tracker.repository.CharacterRepository
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CharacterAdapter
    private val repository = CharacterRepository()

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
    }

    private fun setupRecyclerView() {
        adapter = CharacterAdapter()
        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Загружаем 3 страницы, чтобы получить >50 персонажей (20 * 3 = 60)
                val page1 = repository.getCharacters(1)
                val page2 = repository.getCharacters(2)
                val page3 = repository.getCharacters(3)
                
                val allCharacters = page1 + page2 + page3
                adapter.updateData(allCharacters)
                
                binding.tvChallengesTitle.text = "Персонажи (${allCharacters.size})"
                
            } catch (e: Exception) {
                Log.e(TAG, "Error loading data", e)
                Toast.makeText(requireContext(), "Ошибка загрузки: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
