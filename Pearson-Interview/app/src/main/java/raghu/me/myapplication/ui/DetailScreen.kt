package raghu.me.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import raghu.me.myapplication.R
import raghu.me.myapplication.models.Users

class DetailScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_screen)
        val tv = findViewById<TextView>(R.id.textView)
        val bundle = intent.extras
        val user = bundle!!.getParcelable<Users>("user")
        if (user != null)
            tv.text = user.address!!.street
    }
}
