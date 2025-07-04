package ddwu.com.mobile.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import ddwu.com.mobile.finalproject.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val undoBtn = findViewById<Button>(R.id.undobtn)
        val showReviewBtn = findViewById<ConstraintLayout>(R.id.ShowMyReview_menu)

        undoBtn.setOnClickListener{
            finish()
        }
        showReviewBtn.setOnClickListener{
            val intent = Intent(this, ShowMyReviewActivity::class.java)
            startActivity(intent)
        }
    }
}