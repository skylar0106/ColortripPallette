package ddwu.com.mobile.finalproject

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalproject.databinding.ActivityShowMyReviewBinding
import ddwu.com.mobile.finalproject.ui.FoodViewModelFactory
import ddwu.com.mobile.finalproject.ui.ReviewAdapter
import ddwu.com.mobile.finalproject.ui.ReviewViewModel

class ShowMyReviewActivity : AppCompatActivity() {

    lateinit var mainBinding : ActivityShowMyReviewBinding
    lateinit var adapter: ReviewAdapter

    val viewModel : ReviewViewModel by viewModels{
        FoodViewModelFactory((application as ReviewApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityShowMyReviewBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_show_my_review)

        adapter = ReviewAdapter()
        mainBinding.ShowReviewRecycler.adapter = adapter
        mainBinding.ShowReviewRecycler.layoutManager = LinearLayoutManager(this)
        viewModel.allReviews.observe(this, Observer {reviews ->
            adapter.reviews = reviews
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Observing!!!")
        })
    }
}