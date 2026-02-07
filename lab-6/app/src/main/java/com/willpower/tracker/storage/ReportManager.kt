package com.willpower.tracker.storage

import android.content.Context
import android.os.Environment
import com.willpower.tracker.models.Challenge
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportManager(private val context: Context) {
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val fileDateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.getDefault())
    
    fun generateProgressReport(
        challenges: List<Challenge>,
        userName: String,
        totalCompleted: Int,
        currentStreak: Int
    ): Result<File> {
        return try {
            val reportContent = buildReport(challenges, userName, totalCompleted, currentStreak)
            val fileName = "willpower_report_${fileDateFormat.format(Date())}.txt"
            
            val file = if (isExternalStorageWritable()) {
                val documentsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                File(documentsDir, fileName)
            } else {
                File(context.filesDir, fileName)
            }
            
            FileOutputStream(file).use { fos ->
                fos.write(reportContent.toByteArray())
            }
            
            Result.success(file)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
    
    private fun buildReport(
        challenges: List<Challenge>,
        userName: String,
        totalCompleted: Int,
        currentStreak: Int
    ): String {
        val completedChallenges = challenges.filter { it.isCompleted }
        val pendingChallenges = challenges.filter { !it.isCompleted }
        
        return buildString {
            appendLine("╔════════════════════════════════════════════════════════════╗")
            appendLine("║           WILLPOWER TRACKER - ОТЧЕТ О ПРОГРЕССЕ            ║")
            appendLine("╚════════════════════════════════════════════════════════════╝")
            appendLine()
            appendLine("Дата генерации: ${dateFormat.format(Date())}")
            appendLine("Пользователь: $userName")
            appendLine()
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine("                       СТАТИСТИКА")
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine()
            appendLine("  Всего выполнено челленджей: $totalCompleted")
            appendLine("  Текущий streak: $currentStreak дней")
            appendLine("  Сегодня выполнено: ${completedChallenges.size} из ${challenges.size}")
            appendLine()
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine("                  ВЫПОЛНЕННЫЕ ЧЕЛЛЕНДЖИ")
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine()
            
            if (completedChallenges.isEmpty()) {
                appendLine("  Пока нет выполненных челленджей на сегодня")
            } else {
                completedChallenges.forEachIndexed { index, challenge ->
                    appendLine("  ${index + 1}. ${challenge.title}")
                    appendLine("     Категория: ${challenge.category}")
                    appendLine("     Длительность: ${challenge.durationMinutes} минут")
                    appendLine("     Streak: ${challenge.streak} дней")
                    appendLine()
                }
            }
            
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine("                   ОЖИДАЮЩИЕ ЧЕЛЛЕНДЖИ")
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine()
            
            if (pendingChallenges.isEmpty()) {
                appendLine("  Все челленджи на сегодня выполнены! Отличная работа!")
            } else {
                pendingChallenges.forEachIndexed { index, challenge ->
                    appendLine("  ${index + 1}. ${challenge.title}")
                    appendLine("     Категория: ${challenge.category}")
                    appendLine("     Длительность: ${challenge.durationMinutes} минут")
                    appendLine()
                }
            }
            
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine()
            appendLine("\"Сила воли — это мышца. Чем больше вы её используете,")
            appendLine(" тем сильнее она становится.\" — Келли МакГонигал")
            appendLine()
            appendLine("═══════════════════════════════════════════════════════════════")
            appendLine("              WillPower Tracker © 2024")
            appendLine("═══════════════════════════════════════════════════════════════")
        }
    }
    
    fun getAllReports(): List<File> {
        val internalReports = context.filesDir.listFiles { file ->
            file.name.startsWith("willpower_report_") && file.name.endsWith(".txt")
        }?.toList() ?: emptyList()
        
        val externalReports = if (isExternalStorageReadable()) {
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.listFiles { file ->
                file.name.startsWith("willpower_report_") && file.name.endsWith(".txt")
            }?.toList() ?: emptyList()
        } else {
            emptyList()
        }
        
        return (internalReports + externalReports).sortedByDescending { it.lastModified() }
    }
    
    fun readReport(file: File): String? {
        return try {
            file.readText()
        } catch (e: IOException) {
            null
        }
    }
    
    fun deleteReport(file: File): Boolean {
        return try {
            file.delete()
        } catch (e: SecurityException) {
            false
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
