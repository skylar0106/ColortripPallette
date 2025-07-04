package ddwu.com.mobile.finalproject

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalproject.data.Place
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.URL

class KrlocTourPlaceActivity : AppCompatActivity() {
    val num_of_rows = 10
    val page_no = 1
    val mobile_app = "ColorTripPalette"
    val mobile_os = "AND"
    val list_yn = "Y"
    val arrange = "O"
    val content_type_id = 12
    var area_code = 0

    var itemLatLngs: ArrayList<Place> = arrayListOf()

    lateinit var KrlocTourList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_krloc_tour_place)

        KrlocTourList = findViewById(R.id.KrlocTourList)
        val Region = findViewById<TextView>(R.id.KrlocText)
        val MapBtn = findViewById<ConstraintLayout>(R.id.showMapBtn_krloc)

        area_code = intent.getIntExtra("areaCode", 1)

        // 리아시클러뷰 설정
        KrlocTourList.layoutManager = LinearLayoutManager(this@KrlocTourPlaceActivity)
        when (area_code){
            1 -> Region.text = "서울"
            2 -> Region.text = "인천"
            3 -> Region.text = "대전"
            4 -> Region.text = "대구"
            5 -> Region.text = "광주"
            6 -> Region.text = "부산"
            7 -> Region.text = "울산"
            8 -> Region.text = "세종"
            31 -> Region.text = "경기도"
            32 -> Region.text = "강원도"
        }

        val serviceUrl = "http://apis.data.go.kr/B551011/KorService1/areaBasedList1"
        val serviceKey = "8WhSj%2B1pkX%2ByWEEsJF22BdJvPqgjQYpis5wen371dG8Vvy%2B6qk%2BzLo2znZdx1WrE3msSLvHefH7bWQIxH7uC4A%3D%3D"

        // 이 url 주소 가지고 xml에서 데이터 파싱하기
        val requstUrl = serviceUrl +
                "?serviceKey=" + serviceKey +
                "&pageNo=" + page_no +
                "&numOfRows=" + num_of_rows +
                "&MobileOS=" + mobile_os +
                "&MobileApp=" + mobile_app +
                "&arrange=" + arrange +
                "&contentTypeId=" + content_type_id +
                "&areaCode=" + area_code +
                "&listYN=" + list_yn

        fetchXML(requstUrl)

        MapBtn.setOnClickListener{
            val intent = Intent(this, GoogleMapActivity::class.java)
            intent.putExtra("mapX", itemLatLngs[0].mapx!!.toDouble())
            intent.putExtra("mapY", itemLatLngs[0].mapy!!.toDouble())
            intent.putExtra("itemLatLngs", itemLatLngs)
            intent.putExtra("kind", 0)
            startActivity(intent)
        }
    }

    // xml 파싱하기
    fun fetchXML(url : String) {
        lateinit var page : String  // url 주소 통해 전달받은 내용 저장할 변수
        Log.d("XMLParsing", "파싱 시작")
        // xml 데이터 가져와서 파싱하기
        // 외부에서 데이터 가져올 때 화면 계속 동작하도록 AsyncTask 이용
        class getDangerGrade : AsyncTask<Void, Void, Void>() {
            // url 이용해서 xml 읽어오기
            override fun doInBackground(vararg p0: Void?): Void? {
                Log.d("XMLParsing", "클래스 접근 xml파일 읽어오기")
                // 데이터 스트림 형태로 가져오기
                val stream = URL(url).openStream()
                val bufReader = BufferedReader(InputStreamReader(stream, "UTF-8"))

                // 한줄씩 읽어서 스트링 형태로 바꾼 후 page에 저장
                page = ""
                var line = bufReader.readLine()
                while (line != null) {
                    page += line
                    line = bufReader.readLine()
                    Log.d("XMLParsing", "$line")
                }
                Log.d("XMLParsing", "$page")

                return null
            }

            // 읽어온 xml 파싱하기
            override fun onPostExecute(result: Void?) {
                Log.d("XMLParsing", "클래스 접근 xml파일 파싱")
                super.onPostExecute(result)
                var itemList : ArrayList<Place> = arrayListOf()  // 저장될 데이터 배열

                var tagImage = false   // 이미지 태그
                var tagTitle = false   // 제목 태그
                var tagAddr1 = false   // 주소 태그
                var tagmapx = false
                var tagmapy = false
                var tagTel = false
                var tagZipcode = false

                var firstimage = ""    // 이미지
                var title = ""         // 제목
                var addr1 = ""         // 주소
                var mapx = ""
                var mapy = ""
                var tel = ""
                var zipcode = ""

                var factory = XmlPullParserFactory.newInstance()    // 파서 생성
                factory.setNamespaceAware(true)                     // 파서 설정
                var xpp = factory.newPullParser()                   // XML 파서

                // 파싱하기
                xpp.setInput(StringReader(page))

                // 파싱 진행
                var eventType = xpp.eventType
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {}
                    else if (eventType == XmlPullParser.START_TAG) {
                        Log.d("XMLParsing", "시작 태그")
                        var tagName = xpp.name
                        Log.d("XMLParsing", "$tagName" )
                        if (tagName.equals("addr1")) { tagAddr1 = true
                            Log.d("XMLParsing", "주소 수정 완료") }
                        else if (tagName.equals("firstimage")) { tagImage = true
                            Log.d("XMLParsing", "이미지 수정 완료") }
                        else if (tagName.equals("mapx")) { tagmapx = true
                            Log.d("XMLParsing", "x좌표 수정 완료") }
                        else if (tagName.equals("mapy")) { tagmapy = true
                            Log.d("XMLParsing", "y좌표 수정 완료") }
                        else if (tagName.equals("title")) { tagTitle = true
                            Log.d("XMLParsing", "제목 수정 완료") }
                        else if (tagName.equals("tel")) { tagTel = true
                            Log.d("XMLParsing", "전화번호 수정 완료") }
                        else if (tagName.equals("zipcode")) { tagZipcode = true
                            Log.d("XMLParsing", "우편번호 수정 완료") }
                    }
                    if (eventType == XmlPullParser.TEXT) {
                        Log.d("XMLParsing", "텍스트 태그 접근")
                        if (tagAddr1) {
                            addr1 = xpp.text
                            tagAddr1 = false
                            // 추가: 주소 로그 출력
                            Log.d("XMLParsing", "addr1: $addr1")
                        }else if (tagImage) {
                            firstimage = xpp.text
                            tagImage = false
                            // 추가: 이미지 로그 출력
                            Log.d("XMLParsing", "firstimage: $firstimage")
                        } else if (tagmapx) {
                            mapx = xpp.text
                            tagmapx = false
                            // 추가: mapx 로그 출력
                            Log.d("XMLParsing", "mapx: $mapx")
                        } else if (tagmapy) {
                            mapy = xpp.text
                            tagmapy = false
                            // 추가: mapy 로그 출력
                            Log.d("XMLParsing", "mapy: $mapy")
                        } else if (tagTitle) {
                            title = xpp.text
                            tagTitle = false
                            // 추가: 제목 로그 출력
                            Log.d("XMLParsing", "title: $title")
                        } else if (tagTel) {
                            tel = xpp.text
                            tagTel = false
                            // 추가: 제목 로그 출력
                            Log.d("XMLParsing", "tel: $tel")
                        } else if (tagZipcode) {
                            zipcode = xpp.text
                            tagZipcode = false
                            // 추가: 제목 로그 출력
                            Log.d("XMLParsing", "zipcode: $zipcode")

                            var item = Place(addr1, firstimage, mapx, mapy, title, tel, zipcode, area_code.toString())
                            itemList.add(item)
                            itemLatLngs.add(Place(addr1, firstimage, mapx, mapy, title, tel, zipcode, area_code.toString()))
                        }
                    }
                    if (eventType == XmlPullParser.END_TAG) {
                        var tagName = xpp.name
                        if (tagName.equals("item")) {
                            // 여기서 한 아이템의 파싱이 끝났으니 초기화
                            addr1 = ""
                            firstimage = ""
                            mapx = ""
                            mapy = ""
                            title = ""
                            tel = ""
                            zipcode = ""
                        }
                    }
                    eventType = xpp.next()
                }

                // 리아시클러 뷰에 데이터 연결
                KrlocTourList.adapter = KrlocTourPlaceAdapter(itemList)
            }
        }
        getDangerGrade().execute()
    }
}