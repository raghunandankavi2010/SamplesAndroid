package raghu.me.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import raghu.me.myapplication.model.Users;

public class DetailScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);
        TextView tv = findViewById(R.id.textView);
        Bundle bundle = getIntent().getExtras();
        Users user = bundle.getParcelable("user");
        if(user!=null)
        tv.setText(user.getAddress().getStreet());
    }
}
