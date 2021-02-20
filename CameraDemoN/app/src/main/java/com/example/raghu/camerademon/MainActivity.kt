package com.example.raghu.camerademon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var imageView: ImageView? = null
    private var mCurrentPhotoPath: String? = null
    private var photoURI: Uri? = null

    private var takePicture = registerForActivityResult(TakePicture()
    ) { result ->
        if (result) {
            try {
                val `is` = contentResolver.openInputStream(photoURI!!)
                var bitmap = BitmapFactory.decodeStream(`is`)
                bitmap = rotateImageIfRequired(this@MainActivity, bitmap, photoURI)
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            MediaScannerConnection.scanFile(this@MainActivity, arrayOf(photoURI!!.path), null
            ) { path, uri ->

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.captured_image)
        dispatchTakePictureIntent()
    }

    private fun dispatchTakePictureIntent() {
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (ex: IOException) {
            // Error occurred while creating the File
            ex.printStackTrace()
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this@MainActivity,
                    "com.example.raghu.camerademon.fileProvider",
                    photoFile)
            takePicture.launch(photoURI)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = File(getExternalFilesDir(Environment.DIRECTORY_DCIM).toString())

        // Save a file: path for use with ACTION_VIEW intents
        val image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir /* directory */
        )
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    companion object {
        @Throws(IOException::class)
        private fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri?): Bitmap {
            val input = context.contentResolver.openInputStream(selectedImage!!)
            val ei = ExifInterface(input!!)
            return when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
                else -> img
            }
        }

        private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
            img.recycle()
            return rotatedImg
        }
    }
}