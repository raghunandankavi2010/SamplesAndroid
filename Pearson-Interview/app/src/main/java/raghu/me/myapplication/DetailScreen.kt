package raghu.me.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import raghu.me.myapplication.model.Users

class DetailScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_screen)
        val tv = findViewById<TextView>(R.id.textView)
        val bundle = intent.extras
        val user = bundle!!.getParcelable<Users>("user")
        if (user != null)
            tv.text = user.address.street
    }
}
