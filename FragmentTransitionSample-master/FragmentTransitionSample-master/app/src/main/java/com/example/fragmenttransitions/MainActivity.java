package com.example.fragmenttransitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;

/**
 * Main activity that holds our fragments
 *
 * @author bherbst
 */
public class MainActivity extends AppCompatActivity implements OnKittenClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new GridFragment())
                    .commit();
        }
    }

    @Override
    public void onKittenClicked(View view, String name,int position) {

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(view, "kittenImage");
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("pos",position);
        //setExitTransition(new Fade());
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
