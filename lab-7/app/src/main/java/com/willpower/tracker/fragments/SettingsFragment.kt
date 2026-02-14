package com.willpower.tracker.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.willpower.tracker.databinding.FragmentSettingsBinding
import com.willpower.tracker.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream

/**
 * SettingsFragment manages app settings and backup/restore.
 * Uses SettingsViewModel with DataStore integration.
 */
class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // ViewModel with viewModels() delegate (no custom factory needed)
    private val viewModel: SettingsViewModel by viewModels()

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

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationEnabled(isChecked)
            showToast(if (isChecked) "Уведомления включены" else "Уведомления отключены")
        }

        binding.rgTheme.setOnCheckedChangeListener { _, checkedId ->
            val themeMode = when (checkedId) {
                binding.rbLight.id -> "light"
                binding.rbDark.id -> "dark"
                else -> "system"
            }
            viewModel.setThemeMode(themeMode)
            showToast("Тема изменена на: $themeMode")
        }

        binding.btnSetReminder.setOnClickListener {
            val hour = binding.tpReminder.hour
            val minute = binding.tpReminder.minute
            viewModel.setReminderTime(hour, minute)
            binding.tvReminderTime.text = String.format("%02d:%02d", hour, minute)
            showToast("Напоминание установлено на $hour:${String.format("%02d", minute)}")
        }

        binding.btnClearData.setOnClickListener {
            viewModel.clearAllData()
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

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Collect notification setting
                launch {
                    viewModel.notificationEnabled.collect { enabled ->
                        binding.switchNotifications.isChecked = enabled
                    }
                }

                // Collect reminder time
                launch {
                    viewModel.reminderTime.collect { (hour, minute) ->
                        binding.tvReminderTime.text = String.format("%02d:%02d", hour, minute)
                        binding.tpReminder.hour = hour
                        binding.tpReminder.minute = minute
                    }
                }

                // Collect theme mode
                launch {
                    viewModel.themeMode.collect { mode ->
                        when (mode) {
                            "light" -> binding.rgTheme.check(binding.rbLight.id)
                            "dark" -> binding.rgTheme.check(binding.rbDark.id)
                            else -> binding.rgTheme.check(binding.rbSystem.id)
                        }
                    }
                }

                // Collect backup status
                launch {
                    viewModel.backupStatus.collect { status ->
                        status?.let {
                            when (it) {
                                is SettingsViewModel.BackupStatus.Success -> {
                                    showToast(it.message)
                                    viewModel.clearBackupStatus()
                                }
                                is SettingsViewModel.BackupStatus.Error -> {
                                    showToast(it.message)
                                    viewModel.clearBackupStatus()
                                }
                            }
                        }
                    }
                }

                // Collect restore status
                launch {
                    viewModel.restoreStatus.collect { status ->
                        status?.let {
                            when (it) {
                                is SettingsViewModel.RestoreStatus.Success -> {
                                    showToast(it.message)
                                    viewModel.clearRestoreStatus()
                                }
                                is SettingsViewModel.RestoreStatus.Error -> {
                                    showToast(it.message)
                                    viewModel.clearRestoreStatus()
                                }
                            }
                        }
                    }
                }

                // Collect clearing state
                launch {
                    viewModel.isClearingData.collect { isClearing ->
                        binding.btnClearData.isEnabled = !isClearing
                    }
                }
            }
        }
    }

    private fun createBackup(fileName: String) {
        createDocumentLauncher.launch(fileName)
    }

    private fun writeBackupToFile(uri: Uri) {
        try {
            requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                viewModel.createBackupData { backupData ->
                    outputStream.write(backupData.toByteArray())
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
                    viewModel.processRestoreData(content.toString())
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
