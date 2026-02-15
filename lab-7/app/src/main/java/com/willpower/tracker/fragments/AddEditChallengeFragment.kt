package com.willpower.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.willpower.tracker.R
import com.willpower.tracker.database.entities.TaskEntity
import com.willpower.tracker.databinding.FragmentAddEditChallengeBinding
import com.willpower.tracker.viewmodel.AddEditViewModel
import kotlinx.coroutines.launch

class AddEditChallengeFragment : Fragment() {

    private var _binding: FragmentAddEditChallengeBinding? = null
    private val binding get() = _binding!!
    
    private val args: AddEditChallengeFragmentArgs by navArgs()
    
    private val viewModel: AddEditViewModel by viewModels {
        AddEditViewModel.Factory(requireActivity().application, args.challengeId.toLong())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupDropdowns()
        setupListeners()
        observeViewModel()
    }
    
    private fun setupDropdowns() {
        val difficulties = arrayOf("Легко", "Средне", "Сложно")
        val categories = arrayOf("Осознанность", "Дисциплина", "Развитие", "Фокус", "Здоровье", "Позитив", "Продуктивность")
        
        val diffAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, difficulties)
        binding.actvDifficulty.setAdapter(diffAdapter)
        
        val catAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        binding.actvCategory.setAdapter(catAdapter)
    }
    
    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            saveTask()
        }
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.task.collect { task ->
                task?.let { populateFields(it) }
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.saveResult.collect { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Сохранено!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }
    
    private fun populateFields(task: TaskEntity) {
        binding.etTitle.setText(task.title)
        binding.etDescription.setText(task.description)
        binding.etTechnique.setText(task.techniqueName)
        binding.etDuration.setText(task.recommendedDurationMin.toString())
        binding.actvDifficulty.setText(task.difficulty, false)
        binding.actvCategory.setText(task.category, false)
        binding.btnSave.text = "Обновить"
    }
    
    private fun saveTask() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val technique = binding.etTechnique.text.toString().trim()
        val duration = binding.etDuration.text.toString().toIntOrNull() ?: 0
        val difficulty = binding.actvDifficulty.text.toString()
        val category = binding.actvCategory.text.toString()
        
        if (title.isEmpty()) {
            binding.tilTitle.error = "Введите название"
            return
        }
        
        viewModel.saveTask(title, description, technique, duration, difficulty, category)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
