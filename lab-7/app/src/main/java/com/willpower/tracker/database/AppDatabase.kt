package com.willpower.tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [ChallengeEntity::class, CompletionHistoryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun challengeDao(): ChallengeDao
    abstract fun completionHistoryDao(): CompletionHistoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "willpower_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    
    private class DatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.challengeDao())
                }
            }
        }
        
        private suspend fun populateDatabase(challengeDao: ChallengeDao) {
            val initialChallenges = listOf(
                ChallengeEntity(
                    title = "Утренняя медитация",
                    description = "10 минут осознанного дыхания перед началом дня",
                    category = "Осознанность",
                    durationMinutes = 10,
                    streak = 0
                ),
                ChallengeEntity(
                    title = "Холодный душ",
                    description = "Заверши душ 30-секундным холодным обливанием",
                    category = "Дисциплина",
                    durationMinutes = 5,
                    streak = 0
                ),
                ChallengeEntity(
                    title = "Чтение книги",
                    description = "Прочитай минимум 20 страниц полезной литературы",
                    category = "Развитие",
                    durationMinutes = 30,
                    streak = 0
                ),
                ChallengeEntity(
                    title = "Без соцсетей до обеда",
                    description = "Не открывай социальные сети до 12:00",
                    category = "Фокус",
                    durationMinutes = 240,
                    streak = 0
                ),
                ChallengeEntity(
                    title = "Утренняя зарядка",
                    description = "15 минут физических упражнений после пробуждения",
                    category = "Здоровье",
                    durationMinutes = 15,
                    streak = 0
                ),
                ChallengeEntity(
                    title = "Благодарность",
                    description = "Запиши 3 вещи, за которые ты благодарен сегодня",
                    category = "Позитив",
                    durationMinutes = 5,
                    streak = 0
                ),
                ChallengeEntity(
                    title = "Глубокая работа",
                    description = "1 час сфокусированной работы без отвлечений",
                    category = "Продуктивность",
                    durationMinutes = 60,
                    streak = 0
                )
            )
            
            initialChallenges.forEach { challenge ->
                challengeDao.insert(challenge)
            }
        }
    }
}
