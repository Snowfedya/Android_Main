package com.willpower.tracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.willpower.tracker.databinding.FragmentSettingsBinding
import com.willpower.tracker.storage.UserPreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var preferencesManager: UserPreferencesManager
    private lateinit var sharedPreferences: SharedPreferences
    
    companion object {
        private const val PREFS_NAME = "willpower_shared_prefs"
        private const val KEY_DARK_MODE = "dark_mode_enabled"
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_VIBRATION_ENABLED = "vibration_enabled"
        private const val KEY_LANGUAGE = "language"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        
        preferencesManager = UserPreferencesManager(requireContext())
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        loadSettings()
        setupListeners()
    }
    
    private fun loadSettings() {
        viewLifecycleOwner.lifecycleScope.launch {
            val notificationEnabled = preferencesManager.notificationEnabledFlow.first()
            val (hour, minute) = preferencesManager.reminderTimeFlow.first()
            val themeMode = preferencesManager.themeModeFlow.first()
            
            binding.switchNotifications.isChecked = notificationEnabled
            binding.tvReminderTime.text = String.format("%02d:%02d", hour, minute)
            
            when (themeMode) {
                "light" -> binding.rgTheme.check(binding.rbLight.id)
                "dark" -> binding.rgTheme.check(binding.rbDark.id)
                else -> binding.rgTheme.check(binding.rbSystem.id)
            }
        }
        
        binding.switchDarkMode.isChecked = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        binding.switchSound.isChecked = sharedPreferences.getBoolean(KEY_SOUND_ENABLED, true)
        binding.switchVibration.isChecked = sharedPreferences.getBoolean(KEY_VIBRATION_ENABLED, true)
    }
    
    private fun setupListeners() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                preferencesManager.setNotificationEnabled(isChecked)
                showToast(if (isChecked) "Уведомления включены" else "Уведомления отключены")
            }
        }
        
        binding.rgTheme.setOnCheckedChangeListener { _, checkedId ->
            val themeMode = when (checkedId) {
                binding.rbLight.id -> "light"
                binding.rbDark.id -> "dark"
                else -> "system"
            }
            viewLifecycleOwner.lifecycleScope.launch {
                preferencesManager.setThemeMode(themeMode)
                showToast("Тема изменена на: $themeMode")
            }
        }
        
        binding.btnSetReminder.setOnClickListener {
            val hour = binding.tpReminder.hour
            val minute = binding.tpReminder.minute
            viewLifecycleOwner.lifecycleScope.launch {
                preferencesManager.setReminderTime(hour, minute)
                binding.tvReminderTime.text = String.format("%02d:%02d", hour, minute)
                showToast("Напоминание установлено на $hour:${String.format("%02d", minute)}")
            }
        }
        
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()
        }
        
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(KEY_SOUND_ENABLED, isChecked).apply()
        }
        
        binding.switchVibration.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(KEY_VIBRATION_ENABLED, isChecked).apply()
        }
        
        binding.btnClearData.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                preferencesManager.clearAll()
                sharedPreferences.edit().clear().apply()
                showToast("Все данные очищены")
                loadSettings()
            }
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        _binding = null
    }
}
