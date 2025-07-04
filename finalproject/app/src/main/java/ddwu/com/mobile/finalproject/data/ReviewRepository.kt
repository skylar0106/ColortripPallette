package ddwu.com.mobile.finalproject.data

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ReviewRepository(private val reviewDao: ReviewDAO) {
    val allReviews : Flow<List<Review>> = reviewDao.getAllReviews()

    suspend fun addReview(review: Review) {
        reviewDao.insertFood(review)
    }

    suspend fun modifyReview(reviewDate: String, reviewMsg: String, reviewTitle: String) {
        reviewDao.updateFood(reviewDate, reviewMsg, reviewTitle)
    }

    suspend fun removeReview(reviewTitle: String) {
        reviewDao.deleteFood(reviewTitle)
    }

    suspend fun getReviewByTitle(reviewTitle: String) : List<Review> = reviewDao.getReviewByTitle(reviewTitle)
}