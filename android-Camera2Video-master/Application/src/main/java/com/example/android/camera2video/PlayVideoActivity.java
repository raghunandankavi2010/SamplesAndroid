package com.example.android.camera2video;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Raghu on 13-02-2017.
 */

public class PlayVideoActivity extends AppCompatActivity implements EasyVideoCallback {



    private EasyVideoPlayer player;

    private List<Bitmap> mList = new ArrayList<>();

    private RecyclerView horizontal_recycler_view;


    private String path;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_play);

        horizontal_recycler_view= (RecyclerView) findViewById(R.id.recyclerView);

        path = getIntent().getStringExtra("videoPath");

        // Grabs a reference to the player view
        player = (EasyVideoPlayer) findViewById(R.id.player);

        // Sets the callback to this Activity, since it inherits EasyVideoCallback
        player.setCallback(this);

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        player.setSource(Uri.parse(path));







    }

    @Override
    public void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        player.pause();
    }

    // Methods for the implemented EasyVideoCallback

    @Override
    public void onPreparing(EasyVideoPlayer player) {
        // TODO handle if needed
    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        // TODO handle
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        mediaMetadataRetriever.setDataSource(this,Uri.parse(path));

        for(int i=0;i< player.getDuration()*1000;i+=1000000){
            Bitmap bitmap=mediaMetadataRetriever.getFrameAtTime(i);
            mList.add(bitmap);
        }
      /*  Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(5000000); //unit in microsecond
        ImageView capturedImageView = (ImageView) this.findViewById(R.id.imageView);
        capturedImageView.setImageBitmap(bmFrame);*/

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(mList);
        horizontal_recycler_view.setAdapter(horizontalAdapter);
    }

    @Override
    public void onBuffering(int percent) {
        // TODO handle if needed
    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {
        // TODO handle
    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        // TODO handle if needed
    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {
        // TODO handle if used
    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {
        // TODO handle if used
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {
        // TODO handle if needed
    }

    @Override
    public void onPaused(EasyVideoPlayer player) {
        // TODO handle if needed
    }
}
