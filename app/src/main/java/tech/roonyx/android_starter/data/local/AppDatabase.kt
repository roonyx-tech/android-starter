package tech.roonyx.android_starter.data.local

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.roonyx.android_starter.data.dao.EmptyDao
import tech.roonyx.android_starter.domain.model.entity.EmptyEntity

/**
 * TODO Optional
 */
@Database(
    entities = [EmptyEntity::class],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEmptyDao(): EmptyDao

    companion object {
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "app_db"

        fun create(context: Context): AppDatabase = Room.databaseBuilder(
            if (context is Application) context else context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}