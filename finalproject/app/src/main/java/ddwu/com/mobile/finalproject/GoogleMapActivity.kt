package ddwu.com.mobile.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ddwu.com.mobile.finalproject.data.Place

class GoogleMapActivity : AppCompatActivity() {
    lateinit var markersList: ArrayList<MarkerData>
    private lateinit var googleMap : GoogleMap
    var mapx = 0.0
    var mapy = 0.0
    lateinit var items : ArrayList<Place>
    var kind = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)

        mapx = intent.getDoubleExtra("mapX", 0.0)
        mapy = intent.getDoubleExtra("mapY", 0.0)
        items = intent.getSerializableExtra("itemLatLngs") as ArrayList<Place>
        kind = intent.getIntExtra("kind", 0)

        markersList = ArrayList()

        val mapFragment : SupportMapFragment
        = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(mapReadyCallback)
    }

    val mapReadyCallback = object: OnMapReadyCallback{
        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            Log.d("MAP", "GoogleMap is ready")

            var targetLoc: LatLng = LatLng(mapy, mapx)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 12F))

            for(item in items){
                var target : LatLng = LatLng((item.mapy)!!.toDouble(), (item.mapx)!!.toDouble())

                val markerOptions : MarkerOptions = MarkerOptions()
                    .title("${item.title}")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

                val marker = addMarker(target, markerOptions, item)
                markersList.add(MarkerData(marker!!, item))
            }
            if(kind == 1) {
                val markerOptions: MarkerOptions = MarkerOptions()
                    .title("기준 위치")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

                val marker = addMarker(targetLoc, markerOptions, items[0])
                markersList.add(MarkerData(marker!!, items[0]))
            }
        }
    }

    fun addMarker(targetLoc: LatLng, markerOptions: MarkerOptions, item: Place) : Marker? {
        val marker = googleMap.addMarker(markerOptions.position(targetLoc))
        marker?.showInfoWindow()

        googleMap.setOnMarkerClickListener { marker ->
            false
        }
        if (marker?.title.equals("기준 위치")) {
        } else {
            googleMap.setOnInfoWindowClickListener {marker ->
                val clickedMarkerData = markersList.find { it.marker == marker }
                if (clickedMarkerData != null) {
                    // 마커와 관련된 정보 사용
                    val clickedItem = clickedMarkerData.place
                    val intent = Intent(this, DetailTourPlaceActivity::class.java)
                    intent.putExtra("title", clickedItem.title)
                    intent.putExtra("addr1", clickedItem.addr1)
                    intent.putExtra("zipcode", clickedItem.zipcode)
                    intent.putExtra("tel", clickedItem.tel)
                    intent.putExtra("firstimage", clickedItem.firstimage)
                    intent.putExtra("mapx", clickedItem.mapx)
                    intent.putExtra("mapy", clickedItem.mapy)
                    intent.putExtra("areaCode", clickedItem.areaCode)
                    startActivity(intent)
                }
            }
        }
        return marker
    }
    data class MarkerData(val marker: Marker, val place: Place)
}