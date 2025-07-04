package ddwu.com.mobile.finalproject

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ddwu.com.mobile.finalproject.data.Review
import ddwu.com.mobile.finalproject.databinding.ActivityDetailTourPlaceBinding
import ddwu.com.mobile.finalproject.ui.FoodViewModelFactory
import ddwu.com.mobile.finalproject.ui.ReviewAdapter
import ddwu.com.mobile.finalproject.ui.ReviewViewModel
import java.text.SimpleDateFormat
import java.util.Date

class DetailTourPlaceActivity : AppCompatActivity() {

    lateinit var detailBinding: ActivityDetailTourPlaceBinding
    lateinit var adapter: ReviewAdapter

    val viewModel : ReviewViewModel by viewModels{
        FoodViewModelFactory((application as ReviewApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailTourPlaceBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail_tour_place)

        // 인텐트로 전달된 데이터 받아오기
        val title = intent.getStringExtra("title")
        val addr1 = intent.getStringExtra("addr1")
        val zipcode = intent.getStringExtra("zipcode")
        val tel = intent.getStringExtra("tel")
        val firstimage = intent.getStringExtra("firstimage")
        val mapx = intent.getStringExtra("mapx")
        val mapy = intent.getStringExtra("mapy")
        val areaCode = intent.getStringExtra("areaCode")

        adapter = ReviewAdapter()
        val now = System.currentTimeMillis()
        var date : Date = Date(now)
        var sdf : SimpleDateFormat= SimpleDateFormat("yyyy-MM-dd");
        var getTime = sdf.format(date);
        detailBinding.save.setOnClickListener{
            viewModel.addReview(
                Review(0, title!!, areaCode!!, firstimage!!, addr1!!, zipcode!!, tel!!, mapx!!, mapy!!, getTime, detailBinding.reviewEdit.text.toString()))
        }

        findViewById<TextView>(R.id.TitleText_detail).text = title
        Glide.with(findViewById<ImageView>(R.id.imageView_detail))
            .load(firstimage)
            .error(R.drawable.ic_launcher_foreground)                  // 오류 시 이미지
            .into(findViewById<ImageView>(R.id.imageView_detail))
        findViewById<TextView>(R.id.zipcodeText).text = zipcode
        findViewById<TextView>(R.id.addressText).text = addr1
        findViewById<TextView>(R.id.telText).text = tel
    }
}