package ddwu.com.mobile.finalproject

import android.app.Application
import androidx.room.Room
import ddwu.com.mobile.finalproject.data.ReviewDatabase
import ddwu.com.mobile.finalproject.data.ReviewRepository

class ReviewApplication: Application() {
    val database by lazy {
        ReviewDatabase.getDatabase(this)
    }

    val repository by lazy {
        ReviewRepository(database.reviewDao())
    }
}

