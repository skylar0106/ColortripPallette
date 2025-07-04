package ddwu.com.mobile.finalproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalproject.data.Review
import ddwu.com.mobile.finalproject.databinding.ShowMyReviewListBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {
    var reviews: List<Review>? = null

    override fun getItemCount(): Int {
        return reviews?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val itemBinding = ShowMyReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.itemBinding.tvTitle.text = reviews?.get(position).toString()
    }

    class ReviewHolder(val itemBinding: ShowMyReviewListBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
