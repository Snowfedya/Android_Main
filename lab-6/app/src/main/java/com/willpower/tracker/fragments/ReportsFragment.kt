package com.willpower.tracker.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.willpower.tracker.databinding.FragmentReportsBinding
import com.willpower.tracker.models.Challenge
import com.willpower.tracker.storage.BackupData
import com.willpower.tracker.storage.BackupManager
import com.willpower.tracker.storage.ChallengeBackup
import com.willpower.tracker.storage.ReportManager
import com.willpower.tracker.storage.UserPreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportsFragment : Fragment() {

    private val TAG = "ReportsFragment"
    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var reportManager: ReportManager
    private lateinit var backupManager: BackupManager
    private lateinit var preferencesManager: UserPreferencesManager
    private val challenges = Challenge.getSampleChallenges()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        
        reportManager = ReportManager(requireContext())
        backupManager = BackupManager(requireContext())
        preferencesManager = UserPreferencesManager(requireContext())
        
        setupClickListeners()
        loadReportsList()
    }
    
    private fun setupClickListeners() {
        binding.btnGenerateReport.setOnClickListener {
            generateReport()
        }
        
        binding.btnCreateBackup.setOnClickListener {
            createBackup()
        }
        
        binding.btnRestoreBackup.setOnClickListener {
            restoreLatestBackup()
        }
    }
    
    private fun generateReport() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userName = preferencesManager.userNameFlow.first().ifEmpty { "Пользователь" }
            val totalCompleted = preferencesManager.totalCompletedFlow.first()
            val currentStreak = preferencesManager.currentStreakFlow.first()
            
            val result = reportManager.generateProgressReport(
                challenges = challenges,
                userName = userName,
                totalCompleted = totalCompleted,
                currentStreak = currentStreak
            )
            
            result.onSuccess { file ->
                showToast("Отчет создан: ${file.name}")
                loadReportsList()
                shareFile(file)
            }.onFailure { error ->
                showToast("Ошибка создания отчета: ${error.message}")
            }
        }
    }
    
    private fun createBackup() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userName = preferencesManager.userNameFlow.first()
            val userEmail = preferencesManager.userEmailFlow.first()
            val totalCompleted = preferencesManager.totalCompletedFlow.first()
            val currentStreak = preferencesManager.currentStreakFlow.first()
            val notificationEnabled = preferencesManager.notificationEnabledFlow.first()
            val (hour, minute) = preferencesManager.reminderTimeFlow.first()
            val themeMode = preferencesManager.themeModeFlow.first()
            
            val challengeBackups = challenges.map { challenge ->
                ChallengeBackup(
                    id = challenge.id,
                    title = challenge.title,
                    description = challenge.description,
                    category = challenge.category,
                    durationMinutes = challenge.durationMinutes,
                    isCompleted = challenge.isCompleted,
                    streak = challenge.streak
                )
            }
            
            val backupData = BackupData(
                userName = userName,
                userEmail = userEmail,
                totalCompleted = totalCompleted,
                currentStreak = currentStreak,
                notificationEnabled = notificationEnabled,
                reminderHour = hour,
                reminderMinute = minute,
                themeMode = themeMode,
                backupDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
                challenges = challengeBackups
            )
            
            val result = backupManager.createBackup(backupData)
            
            result.onSuccess { file ->
                showToast("Резервная копия создана: ${file.name}")
                loadReportsList()
            }.onFailure { error ->
                showToast("Ошибка создания резервной копии: ${error.message}")
            }
        }
    }
    
    private fun restoreLatestBackup() {
        val backups = backupManager.getAllBackups()
        if (backups.isEmpty()) {
            showToast("Нет доступных резервных копий")
            return
        }
        
        val latestBackup = backups.first()
        val result = backupManager.restoreBackup(latestBackup)
        
        result.onSuccess { backupData ->
            viewLifecycleOwner.lifecycleScope.launch {
                preferencesManager.saveUserCredentials(backupData.userName, backupData.userEmail)
                preferencesManager.setNotificationEnabled(backupData.notificationEnabled)
                preferencesManager.setReminderTime(backupData.reminderHour, backupData.reminderMinute)
                preferencesManager.setThemeMode(backupData.themeMode)
                preferencesManager.updateStreak(backupData.currentStreak)
                
                showToast("Данные восстановлены из: ${latestBackup.name}")
            }
        }.onFailure { error ->
            showToast("Ошибка восстановления: ${error.message}")
        }
    }
    
    private fun loadReportsList() {
        val reports = reportManager.getAllReports()
        val backups = backupManager.getAllBackups()
        
        val sb = StringBuilder()
        sb.appendLine("Отчеты (${reports.size}):")
        reports.take(5).forEach { file ->
            sb.appendLine("  - ${file.name}")
        }
        sb.appendLine()
        sb.appendLine("Резервные копии (${backups.size}):")
        backups.take(5).forEach { file ->
            sb.appendLine("  - ${file.name}")
        }
        
        binding.tvFilesList.text = sb.toString()
    }
    
    private fun shareFile(file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            )
            
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            startActivity(Intent.createChooser(shareIntent, "Поделиться отчетом"))
        } catch (e: Exception) {
            Log.e(TAG, "Error sharing file", e)
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
