package ddwu.com.mobile.finalproject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ReviewDAO {
    @Query("SELECT * FROM review_table")
    fun getAllReviews() : Flow<List<Review>>

    @Query("SELECT * FROM review_table WHERE title = :title")
    suspend fun getReviewByTitle(title: String) : List<Review>

    @Insert
    suspend fun insertFood(vararg review : Review)

    @Query("UPDATE review_table SET date = :date, msg = :msg WHERE title = :title")
    suspend fun updateFood(date : String, msg : String, title: String)

    @Query("DELETE FROM review_table WHERE title = :title")
    suspend fun deleteFood(title : String)
}