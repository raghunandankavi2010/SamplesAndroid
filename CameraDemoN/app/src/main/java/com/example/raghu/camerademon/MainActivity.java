package com.example.raghu.camerademon;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    //private static final int REQUEST_TAKE_PHOTO = 504;
    private ImageView imageView;
    //private String mCurrentPhotoPath;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.captured_image);

        MainActivityPermissionsDispatcher.captureImageViaCameraWithCheck(this);

    }

    ActivityResultLauncher<Uri> takePicture = registerForActivityResult(new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        try {
                            InputStream is = getContentResolver().openInputStream(photoURI);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            bitmap = rotateImageIfRequired(MainActivity.this, bitmap, photoURI);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        MediaScannerConnection.scanFile(MainActivity.this,
                                new String[]{photoURI.getPath()}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    public void onScanCompleted(String path, Uri uri) {
                                    }
                                });
                    }
                }
            }
    );

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void captureImageViaCamera() {
            dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent(){
        //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        // Create the File where the photo should go
        File photoFile;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            ex.printStackTrace();
            return;
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(MainActivity.this,
                    "com.example.raghu.camerademon.fileProvider",
                    photoFile);
            takePicture.launch(photoURI);
        }
        //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("Permission required fro Camera")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Toast.makeText(this.getApplicationContext(), "Permision Denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        Toast.makeText(this.getApplicationContext(), "Never ask again", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForRead(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("Permission required for READ")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDeniedForRead() {
        Toast.makeText(this.getApplicationContext(), "Permision Denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAskForRead() {
        Toast.makeText(this.getApplicationContext(), "Never ask again", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

          File file = new File(mCurrentPhotoPath);
          Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.example.raghu.camerademon.fileProvider", file);
          Log.i("...........", "" + contentUri);
            // Show the thumbnail on ImageView
          *//*  Uri imageUri = Uri.parse(mCurrentPhotoPath);
            Log.i("...........", "" + imageUri);
            File file = new File(imageUri.getPath());*//*
            try {
                InputStream ims = new FileInputStream(file);
                Bitmap img = BitmapFactory.decodeStream(ims);

                img = rotateImageIfRequired(MainActivity.this, img, contentUri);

                imageView.setImageBitmap(img);
            } catch (FileNotFoundException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

            // ScanFile so it will be appeared on Gallery
            MediaScannerConnection.scanFile(MainActivity.this,
                    new String[]{contentUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_DCIM)));

        // Save a file: path for use with ACTION_VIEW intents
       // mCurrentPhotoPath = image.getAbsolutePath();
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        ei = new ExifInterface(input);

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}
