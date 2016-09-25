package example.raghunandan.databinding;

import android.view.View;
import android.widget.Toast;

/**
 * Created by Raghunandan on 24-09-2016.
 */

public class MyClickListeners {

    public void onButtonClick(View view) {

        User user = new User();
        Toast.makeText(view.getContext(),"Button Clicked",Toast.LENGTH_SHORT).show();
        user.setFirstName("Raghunandan");
    }
}
