package com.willpower.tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.willpower.tracker.database.dao.AiAdviceDao
import com.willpower.tracker.database.dao.CompletionDao
import com.willpower.tracker.database.dao.TaskDao
import com.willpower.tracker.database.entities.AiAdviceEntity
import com.willpower.tracker.database.entities.CompletionEntity
import com.willpower.tracker.database.entities.TaskEntity

@Database(
    entities = [AiAdviceEntity::class, TaskEntity::class, CompletionEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun aiAdviceDao(): AiAdviceDao
    abstract fun taskDao(): TaskDao
    abstract fun completionDao(): CompletionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "willpower_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
