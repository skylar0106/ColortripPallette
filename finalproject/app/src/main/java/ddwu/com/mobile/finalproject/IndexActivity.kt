package ddwu.com.mobile.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.identity.android.legacy.Utility

class IndexActivity : AppCompatActivity(){

    private val delayMillis = 5000L // 딜레이 시간 설정 (5초)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        // 지정된 딜레이 후 LoginActivity로 전환
        android.os.Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }, delayMillis)
    }
}