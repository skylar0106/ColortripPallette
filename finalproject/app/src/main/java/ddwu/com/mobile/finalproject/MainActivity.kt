package ddwu.com.mobile.finalproject

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import java.util.Locale
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.net.Uri
import android.os.Build
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var geocoder : Geocoder
    private lateinit var currentLoc : Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.getDefault())

        val regionBtns = listOf<Button>(
            findViewById(R.id.areaBtn1),
            findViewById(R.id.areaBtn2),
            findViewById(R.id.areaBtn3),
            findViewById(R.id.areaBtn4),
            findViewById(R.id.areaBtn5),
            findViewById(R.id.areaBtn6),
            findViewById(R.id.areaBtn7),
            findViewById(R.id.areaBtn8),
            findViewById(R.id.areaBtn31),
            findViewById(R.id.areaBtn32)
            // Add more buttons as needed
        )

        val menu = findViewById<Button>(R.id.TopMenuBtn)
        menu.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val searchMylocTourPlaceBtn = findViewById<ConstraintLayout>(R.id.searchMylocTourPlace_btn_main)

        searchMylocTourPlaceBtn.setOnClickListener {
            checkPermissions()
        }

        val searchKrlocTourPlaceBtn = findViewById<ConstraintLayout>(R.id.searchKrlocTourPlace_btn_main)

        searchKrlocTourPlaceBtn.setOnClickListener {
            var selectedArea = findSelectedButton(regionBtns)
            val intent = Intent(this, KrlocTourPlaceActivity::class.java)
            Log.d("Area", "selectedArea: $selectedArea")
            intent.putExtra("areaCode", selectedArea)
            startActivity(intent)
        }

        val placeBtn1 = findViewById<Button>(R.id.place_btn1)
        val placeBtn2 = findViewById<Button>(R.id.place_btn2)
        val placeBtn3 = findViewById<Button>(R.id.place_btn3)
        val placeBtn4 = findViewById<Button>(R.id.place_btn4)
        val placeBtn5 = findViewById<Button>(R.id.place_btn5)

        val img = findViewById<ImageView>(R.id.tour_place_img)

        placeBtn1.setOnClickListener {
            img.setImageResource(R.drawable.place1)
            placeBtn1.setBackgroundResource(R.drawable.place_btn_click_true)
            placeBtn2.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn3.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn4.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn5.setBackgroundResource(R.drawable.place_btn_click_false)
        }
        placeBtn2.setOnClickListener {
            img.setImageResource(R.drawable.place2)
            placeBtn1.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn2.setBackgroundResource(R.drawable.place_btn_click_true)
            placeBtn3.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn4.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn5.setBackgroundResource(R.drawable.place_btn_click_false)
        }
        placeBtn3.setOnClickListener {
            img.setImageResource(R.drawable.place3)
            placeBtn1.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn2.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn3.setBackgroundResource(R.drawable.place_btn_click_true)
            placeBtn4.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn5.setBackgroundResource(R.drawable.place_btn_click_false)
        }
        placeBtn4.setOnClickListener {
            img.setImageResource(R.drawable.place4)
            placeBtn1.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn2.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn3.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn4.setBackgroundResource(R.drawable.place_btn_click_true)
            placeBtn5.setBackgroundResource(R.drawable.place_btn_click_false)
        }
        placeBtn5.setOnClickListener {
            img.setImageResource(R.drawable.place5)
            placeBtn1.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn2.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn3.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn4.setBackgroundResource(R.drawable.place_btn_click_false)
            placeBtn5.setBackgroundResource(R.drawable.place_btn_click_true)
        }

        regionBtns[0].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    0 -> regionBtns[0].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[1].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    1 -> regionBtns[1].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[2].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    2 -> regionBtns[2].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[3].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    3 -> regionBtns[3].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[4].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    4 -> regionBtns[4].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[5].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    5 -> regionBtns[5].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[6].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    6 -> regionBtns[6].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[7].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    7 -> regionBtns[7].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[8].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    8 -> regionBtns[8].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
        regionBtns[9].setOnClickListener {
            for (index in 0 until 10) {
                when(index){
                    9 -> regionBtns[9].setBackgroundResource(R.drawable.region_btn_click_true)
                    else -> regionBtns[index].setBackgroundResource(R.drawable.region_btn_click_false)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locCallback)
    }

    private fun findSelectedButton(buttons: List<Button>): Int {
        buttons.forEachIndexed { index, button ->
            if (button.background.constantState == resources.getDrawable(R.drawable.region_btn_click_true).constantState) {
                return if (index < 8) index + 1 else if (index == 8) 31 else 32
            }
        }
        return -1 // 선택된 버튼이 없을 경우 -1을 리턴하거나 다른 방식으로 처리할 수 있습니다.
    }

    /*registerForActivityResult 는 startActivityForResult() 대체*/
    val locationPermissionRequest
            = registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions() ) {
            permissions ->
        when {
            permissions.getOrDefault(ACCESS_FINE_LOCATION, false) -> {
                Log.d("Permission", "FINE_LOCATION is granted")
                startLocUpdates()
                getLastLocation()
            }
            permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) -> {
                Log.d("Permission", "COARSE_LOCATION is granted")
                startLocUpdates()
                getLastLocation()
            }
            else -> {
                Log.d("Permission", "Location permissions are required")
                Toast.makeText(this, "허용하지 않으면 해당 기능 사용 불가", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkPermissions () {
        if (checkSelfPermission(ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "Permissions are already granted")
            startLocUpdates()
            getLastLocation()
        } else {
            locationPermissionRequest.launch(arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION))
        }
    }

    val locCallback: LocationCallback = object :LocationCallback(){
        override fun onLocationResult(locResult: LocationResult) {
            val currentLoc : Location = locResult.locations[0]
            Log.d("location", "위도 : ${currentLoc.latitude}, 경도: ${currentLoc.longitude}")
        }
    }

    val locRequest : LocationRequest = LocationRequest.Builder(10000)
        .setMinUpdateIntervalMillis(5000)
        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        .build()

    @SuppressLint("MissingPermission")
    private fun startLocUpdates(){
        fusedLocationClient.requestLocationUpdates(
            locRequest,
            locCallback,
            Looper.getMainLooper()
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val intent = Intent(this, MylocTourPlaceActivity::class.java)
                Log.d("위치", "${location.latitude}")
                Log.d("위치", "${location.longitude}")
                intent.putExtra("mapY", location.latitude)
                intent.putExtra("mapX", location.longitude)
                startActivity(intent)
            }
        }
        fusedLocationClient.lastLocation.addOnFailureListener { e: Exception ->
            Log.d("E", e.toString())
        }
    }
}