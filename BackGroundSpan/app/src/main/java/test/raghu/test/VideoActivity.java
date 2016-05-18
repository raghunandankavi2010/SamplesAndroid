package test.raghu.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;
/*import com.ji.ttms.asynctask.AddVideoAsync;
import com.ji.ttms.asynctask.ChangePWDAsyncTask;
import com.ji.ttms.notifier.AddVideoNotifier;*/

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class VideoActivity extends Activity implements SurfaceHolder.Callback{//, AddVideoNotifier {
	private final String VIDEO_PATH_NAME = "/mnt/sdcard/VGA_30fps_512vbrate.mp4";

	private MediaRecorder mMediaRecorder;
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mHolder;
	private View mToggleButton;
	private boolean mInitSuccesful;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.video_activity);

	    // we shall take the video in landscape orientation
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	    mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
	    mHolder = mSurfaceView.getHolder();
	    mHolder.addCallback(this);
	    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	    mToggleButton = (ToggleButton) findViewById(R.id.toggleRecordingButton);
	    mToggleButton.setOnClickListener(new OnClickListener() {
	        @Override
	        // toggle video recording
	        public void onClick(View v) {
	            if (((ToggleButton)v).isChecked()) {
	                mMediaRecorder.start();
	                try {
	                    Thread.sleep(2 * 1000); // This will recode for 10 seconds, if you don't want then just remove it.
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
//	                finish();
	            }
	            else {
	                mMediaRecorder.stop();
	                mMediaRecorder.reset();
//	                try {
//	                    initRecorder(mHolder.getSurface());
//	                } catch (IOException e) {
//	                    e.printStackTrace();
//	                }
	                
//	                finish();
	                
	                
	                
	                File file=new File(VIDEO_PATH_NAME);
					try {
						byte[] video = FileUtils.readFileToByteArray(file);//Convert any file, image or video into byte array
//											String	strFile = Base64.encodeToString(video, Base64.NO_WRAP);//Convert byte array into string
						String imgString = "";
						if(video.length > 0)
						{
							imgString = Base64.encodeToString(video, 
				                       Base64.NO_WRAP);
							Log.i("VIdeo", "Pavan ==>"+imgString);
						}
						
						
						JsonObject jsonObject = new  JsonObject();
						jsonObject.addProperty("filetype","video");
						jsonObject.addProperty("filename","sample.mp4");
						jsonObject.addProperty("filesize","8000");//byte size
						jsonObject.addProperty("base64",imgString);   //Base64 encoded of video
						jsonObject.addProperty("isProfile","false");
						jsonObject.addProperty("ProfileId","8786fc68-b06d-4d3b-8348-6ab76f571f1b");
						
						Log.i("VIdeo", "Pavan output ==>"+jsonObject.toString());

//						ChangePWDAsyncTask asynctask=new ChangePWDAsyncTask(ChangePasswordActivity.this,ChangePasswordActivity.this);
						//AddVideoAsync addVideoAsync = new AddVideoAsync(VideoActivity.this, VideoActivity.this);
						//addVideoAsync.execute(jsonObject.toString());
					
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
//	                finish();
	            }
	        }
	    });     
	}

	/* Init the MediaRecorder, the order the methods are called is vital to
	 * its correct functioning */
	private void initRecorder(Surface surface) throws IOException {
	    // It is very important to unlock the camera before doing setCamera
	    // or it will results in a black preview
	    if(mCamera == null) {
	        mCamera = Camera.open();
	        mCamera.unlock();
	    }

	    if(mMediaRecorder == null)  mMediaRecorder = new MediaRecorder();
	    mMediaRecorder.setPreviewDisplay(surface);
	    mMediaRecorder.setCamera(mCamera);

	    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
	   //       mMediaRecorder.setOutputFormat(8);
	    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
	    mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
	    mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
	    mMediaRecorder.setVideoFrameRate(30);
	    
	    mMediaRecorder.setVideoSize(640, 480);
	    mMediaRecorder.setOutputFile(VIDEO_PATH_NAME);

	    try {
	        mMediaRecorder.prepare();
	    } catch (IllegalStateException e) {
	        // This is thrown if the previous calls are not called with the 
	        // proper order
	        e.printStackTrace();
	    }

	    mInitSuccesful = true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    try {
	        if(!mInitSuccesful)
	            initRecorder(mHolder.getSurface());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	    shutdown();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

	private void shutdown() {
	    // Release MediaRecorder and especially the Camera as it's a shared
	    // object that can be used by other applications
	    mMediaRecorder.reset();
	    mMediaRecorder.release();
	    mCamera.release();

	    // once the objects have been released they can't be reused
	    mMediaRecorder = null;
	    mCamera = null;
	}


	public void vedioAddSuccess(String response) {
		// TODO Auto-generated method stub
		Toast.makeText(VideoActivity.this, " Success Response "+response, Toast.LENGTH_SHORT).show();;
		((TextView)findViewById(R.id.textView1)).setText(response);
	}

	public void vedioAddOnError() {
		// TODO Auto-generated method stub
		Toast.makeText(VideoActivity.this, " Success error ", Toast.LENGTH_SHORT).show();;
	}
}