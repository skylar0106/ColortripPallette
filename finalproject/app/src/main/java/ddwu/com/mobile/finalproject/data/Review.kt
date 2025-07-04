package ddwu.com.mobile.finalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "review_table")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val areaCode : String,
    val firstIMAGE : String,
    val addr1: String,
    val zipcode : String,
    val tel : String,
    val mapx : String,
    val mapy : String,
    val date : String,
    val msg : String
)
