package com.demo.sticker;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setTitle("Add Stickers to GBoard");

    ArrayList<Data> data = new ArrayList<>();

    data.add(new Data(R.drawable.good_vibes_01));
    data.add(new Data(R.drawable.good_vibes_02));
    data.add(new Data(R.drawable.good_vibes_03));
    data.add(new Data(R.drawable.good_vibes_04));
    data.add(new Data(R.drawable.good_vibes_05));
    data.add(new Data(R.drawable.good_vibes_06));
    data.add(new Data(R.drawable.good_vibes_07));
    data.add(new Data(R.drawable.good_vibes_08));
    data.add(new Data(R.drawable.good_vibes_09));
    data.add(new Data(R.drawable.good_vibes_10));
    data.add(new Data(R.drawable.blessings));
    data.add(new Data(R.drawable.heart_icon));
    data.add(new Data(R.drawable.just_relax));
    data.add(new Data(R.drawable.keep_going));
    data.add(new Data(R.drawable.lets_go));
    data.add(new Data(R.drawable.lightbulbicon));
    data.add(new Data(R.drawable.live_and_maintain));
    data.add(new Data(R.drawable.love_yourself));
    data.add(new Data(R.drawable.remind_to_smile));
    data.add(new Data(R.drawable.shine_a_light));
    data.add(new Data(R.drawable.step_by_step));
    data.add(new Data(R.drawable.take_time));
    data.add(new Data(R.drawable.tray_blessed));
    data.add(new Data(R.drawable.we_mov));
    data.add(new Data(R.drawable.world_energy));
    data.add(new Data(R.drawable.you_got_his));
    data.add(new Data(R.drawable.fist_style_1));

    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    CustomAdapter customAdapter = new CustomAdapter(data);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.gridSpace);
    recyclerView.addItemDecoration(new GridSpaceItemDecoration(spacingInPixels));
    recyclerView.setAdapter(customAdapter);
    recyclerView.setLayoutManager(gridLayoutManager);
    MaterialButton mBtnAddSticker = findViewById(R.id.btn_add_sticker);
    mBtnAddSticker.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            AppIndexingUpdateService.enqueueWork(HomeActivity.this);
          }
        });
  }
}
