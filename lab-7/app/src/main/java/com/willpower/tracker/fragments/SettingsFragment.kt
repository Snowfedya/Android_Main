package com.willpower.tracker.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.willpower.tracker.databinding.FragmentSettingsBinding
import com.willpower.tracker.storage.UserPreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

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
    }

    private val createDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        uri?.let { writeBackupToFile(it) }
    }

    private val openDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { readBackupFromFile(it) }
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

        binding.btnBackup.setOnClickListener {
            val fileName = binding.etBackupName.text.toString().trim()
            if (fileName.isEmpty()) {
                showToast("Введите имя файла")
                return@setOnClickListener
            }
            createBackup(fileName)
        }

        binding.btnRestore.setOnClickListener {
            openDocumentLauncher.launch(arrayOf("text/plain"))
        }
    }

    private fun createBackup(fileName: String) {
        createDocumentLauncher.launch(fileName)
    }

    private fun writeBackupToFile(uri: Uri) {
        try {
            requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                // Здесь мы собираем данные для бэкапа.
                // В реальном приложении нужно брать данные из БД или всех преференсов.
                // Для лабораторной возьмем текущие настройки.
                viewLifecycleOwner.lifecycleScope.launch {
                    val userName = preferencesManager.userNameFlow.first()
                    val userEmail = preferencesManager.userEmailFlow.first()
                    val theme = preferencesManager.themeModeFlow.first()

                    val backupData = """
                        Backup Date: ${java.util.Date()}
                        User: $userName ($userEmail)
                        Theme: $theme
                        Dark Mode: ${binding.switchDarkMode.isChecked}
                        Sound: ${binding.switchSound.isChecked}
                    """.trimIndent()

                    outputStream.write(backupData.toByteArray())
                    showToast("Бэкап успешно создан")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error writing backup", e)
            showToast("Ошибка создания бэкапа: ${e.message}")
        }
    }

    private fun readBackupFromFile(uri: Uri) {
        try {
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    val content = StringBuilder()
                    var line: String? = reader.readLine()
                    while (line != null) {
                        content.append(line).append("\n")
                        line = reader.readLine()
                    }
                    // В лабораторной нужно просто восстановить или показать данные.
                    // Мы покажем содержимое в Toast или Dialog.
                    // В реальной жизни тут был бы парсинг.
                    showToast("Файл прочитан:\n$content")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error reading backup", e)
            showToast("Ошибка чтения бэкапа: ${e.message}")
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
