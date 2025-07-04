package ddwu.com.mobile.finalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Singleton 패턴 적용
@Database(entities = [Review::class], version=1)
abstract class ReviewDatabase : RoomDatabase() {
    abstract fun reviewDao() : ReviewDAO

    companion object {
        @Volatile
        private var INSTANCE: ReviewDatabase? = null

        fun getDatabase(context: Context): ReviewDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReviewDatabase::class.java,
                    "review_table"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}




