package assignment.test.raghu.peppersqaure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Raghunandan on 02-12-2015.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomViewGroup customViewGroup  = new CustomViewGroup(this);
        setContentView(customViewGroup);
    }
}
