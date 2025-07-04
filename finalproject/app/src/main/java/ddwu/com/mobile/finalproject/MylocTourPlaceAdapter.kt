package ddwu.com.mobile.finalproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ddwu.com.mobile.finalproject.data.Place

class MylocTourPlaceAdapter (var items : ArrayList<Place>) : RecyclerView.Adapter<MylocTourPlaceAdapter.ViewHolder>() {
    // 뷰 홀더 만들어서 반환, 뷰릐 레이아웃은 list_item_tour.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MylocTourPlaceAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.myloc_tour_place_list, parent, false)
        return ViewHolder(itemView)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: MylocTourPlaceAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    // 아이템 갯수 리턴
    override fun getItemCount() = items.count()

    // 뷰 홀더 설정
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item : Place) {
            val imgFirstImage = itemView.findViewById<ImageView>(R.id.imgFirstImage)    // 이미지
            val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)                 // 제목
            val tvAddr1 = itemView.findViewById<TextView>(R.id.tvAddr1)                 // 주소 // 상세 주소

            Glide.with(imgFirstImage)
                .load(item.firstimage)
                .error(R.drawable.ic_launcher_foreground)                  // 오류 시 이미지
                .into(imgFirstImage)
            tvTitle.text = item.title
            tvAddr1.text = item.addr1

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailTourPlaceActivity::class.java)

                // Put the selected item's information as extras to the Intent
                intent.putExtra("title", item.title)
                intent.putExtra("addr1", item.addr1)
                intent.putExtra("zipcode", item.zipcode)
                intent.putExtra("firstimage", item.firstimage)
                intent.putExtra("areaCode", item.areaCode)
                intent.putExtra("tel", item.tel)
                intent.putExtra("mapx", item.mapx)
                intent.putExtra("mapy", item.mapy)

                // Start DetailTourPlaceActivity with the Intent
                itemView.context.startActivity(intent)
            }
        }
    }
}