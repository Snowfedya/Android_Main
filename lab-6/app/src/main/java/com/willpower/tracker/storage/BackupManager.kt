package com.willpower.tracker.storage

import android.content.Context
import android.os.Environment
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data class BackupData(
    val userName: String,
    val userEmail: String,
    val totalCompleted: Int,
    val currentStreak: Int,
    val notificationEnabled: Boolean,
    val reminderHour: Int,
    val reminderMinute: Int,
    val themeMode: String,
    val backupDate: String,
    val challenges: List<ChallengeBackup>
)

@Serializable
data class ChallengeBackup(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val durationMinutes: Int,
    val isCompleted: Boolean,
    val streak: Int
)

class BackupManager(private val context: Context) {
    
    private val json = Json { 
        prettyPrint = true 
        ignoreUnknownKeys = true
    }
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
    
    fun createBackup(backupData: BackupData): Result<File> {
        return try {
            val fileName = "willpower_backup_${dateFormat.format(Date())}.json"
            
            val backupDir = if (isExternalStorageWritable()) {
                File(context.getExternalFilesDir(null), "backups").also { 
                    if (!it.exists()) it.mkdirs() 
                }
            } else {
                File(context.filesDir, "backups").also { 
                    if (!it.exists()) it.mkdirs() 
                }
            }
            
            val backupFile = File(backupDir, fileName)
            val jsonContent = json.encodeToString(backupData)
            backupFile.writeText(jsonContent)
            
            Result.success(backupFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun restoreBackup(backupFile: File): Result<BackupData> {
        return try {
            val jsonContent = backupFile.readText()
            val backupData = json.decodeFromString<BackupData>(jsonContent)
            Result.success(backupData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getAllBackups(): List<File> {
        val internalBackups = File(context.filesDir, "backups").listFiles { file ->
            file.name.startsWith("willpower_backup_") && file.name.endsWith(".json")
        }?.toList() ?: emptyList()
        
        val externalBackups = if (isExternalStorageReadable()) {
            File(context.getExternalFilesDir(null), "backups").listFiles { file ->
                file.name.startsWith("willpower_backup_") && file.name.endsWith(".json")
            }?.toList() ?: emptyList()
        } else {
            emptyList()
        }
        
        return (internalBackups + externalBackups).sortedByDescending { it.lastModified() }
    }
    
    fun deleteBackup(backupFile: File): Boolean {
        return try {
            backupFile.delete()
        } catch (e: SecurityException) {
            false
        }
    }
    
    fun getBackupInfo(backupFile: File): BackupInfo? {
        return try {
            val jsonContent = backupFile.readText()
            val backupData = json.decodeFromString<BackupData>(jsonContent)
            BackupInfo(
                fileName = backupFile.name,
                filePath = backupFile.absolutePath,
                fileSize = backupFile.length(),
                backupDate = backupData.backupDate,
                userName = backupData.userName,
                challengeCount = backupData.challenges.size,
                totalCompleted = backupData.totalCompleted
            )
        } catch (e: Exception) {
            null
        }
    }
    
    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
    
    private fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return state == Environment.MEDIA_MOUNTED || state == Environment.MEDIA_MOUNTED_READ_ONLY
    }
}

data class BackupInfo(
    val fileName: String,
    val filePath: String,
    val fileSize: Long,
    val backupDate: String,
    val userName: String,
    val challengeCount: Int,
    val totalCompleted: Int
)
