package com.example.raghu.specbeeassignment


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import com.example.raghu.specbeeassignment.R.id.fab
import com.example.raghu.specbeeassignment.R.id.toolbar
import com.example.raghu.specbeeassignment.databinding.ActivityMainBinding
import permissions.dispatcher.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@RuntimePermissions
class MainActivity : AppCompatActivity(), OnItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var mCurrentPhotoPath: String? = null
    private var mAdapter: ImageAdapter? = null

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)



            setSupportActionBar(binding.toolbar)

            supportActionBar!!.title = "RecyclerView"

            binding.fabCamera.setOnClickListener {
                captureImageViaCameraWithPermissionCheck()

            }


            binding.fab.setOnClickListener {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                startActivityForResult(intent, REQUEST_RESULT)
            }


            binding.recyclerView.setHasFixedSize(true)
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
            binding.recyclerView.addItemDecoration(RecyclerViewMargin(spacingInPixels, 1))
            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapter = ImageAdapter(this@MainActivity, this@MainActivity)
            binding.recyclerView.adapter = mAdapter


        }


        @NeedsPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        fun captureImageViaCamera() {


            try {
                dispatchTakePictureIntent()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        @Throws(IOException::class)
        private fun dispatchTakePictureIntent() {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    return
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(this@MainActivity,
                            "com.example.raghu.specbeeassignment.fileProvider",
                            createImageFile())
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }

        @OnShowRationale(Manifest.permission.CAMERA)
        internal fun showRationaleForCamera(request: PermissionRequest) {
            AlertDialog.Builder(this)
                    .setMessage("Permission required fro Camera")
                    .setPositiveButton("Allow") { dialog, button -> request.proceed() }
                    .setNegativeButton("Deny") { dialog, button -> request.cancel() }
                    .show()
        }

        @OnPermissionDenied(Manifest.permission.CAMERA)
        internal fun showDeniedForCamera() {
            Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show()
        }

        @OnNeverAskAgain(Manifest.permission.CAMERA)
        fun showNeverAskForCamera() {
            Toast.makeText(this, "Never ask again", Toast.LENGTH_SHORT).show()
        }


        @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        fun showRationaleForWrite(request: PermissionRequest) {
            AlertDialog.Builder(this)
                    .setMessage("Permission required for Write")
                    .setPositiveButton("Allow") { dialog, button -> request.proceed() }
                    .setNegativeButton("Deny") { dialog, button -> request.cancel() }
                    .show()
        }

        @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        fun showDeniedForWrite() {
            Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show()
        }

        @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        internal fun showNeverAskForWrite() {
            Toast.makeText(this, "Never ask again", Toast.LENGTH_SHORT).show()
        }


        @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
        fun showRationaleForRead(request: PermissionRequest) {
            AlertDialog.Builder(this)
                    .setMessage("Permission required for READ")
                    .setPositiveButton("Allow") { dialog, button -> request.proceed() }
                    .setNegativeButton("Deny") { dialog, button -> request.cancel() }
                    .show()
        }

        @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
        fun showDeniedForRead() {
            Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show()
        }

        @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
        fun showNeverAskForRead() {
            Toast.makeText(this, "Never ask again", Toast.LENGTH_SHORT).show()
        }


        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            // NOTE: delegate the permission handling to generated function
            onRequestPermissionsResult(requestCode, grantResults)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
                // Show the thumbnail on ImageView
                val imageUri = Uri.parse(mCurrentPhotoPath)
                val file = File(imageUri.path)

                val items = Items(url = file.absolutePath)
                mAdapter?.add(items)

                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(this@MainActivity,
                        arrayOf(imageUri.path), null
                ) { path, uri -> }
            } else if (requestCode == REQUEST_RESULT ) {

                val items = Items(text = data.getStringExtra("MESSAGE"))
                mAdapter?.add(items)


            }
        }

        @Throws(IOException::class)
        private fun createImageFile(): File {
            // Create an image file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"
            val storageDir = File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM), "Camera")
            val image = File.createTempFile(
                    imageFileName, ".jpg", storageDir)

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + image.absolutePath
            return image
        }

        override fun onItemClick(path: String) {

            Toast.makeText(this.applicationContext, path.toString(), Toast.LENGTH_SHORT).show()
        }

        override fun onItemClickText(text: String) {
            Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT).show()
        }

        companion object {

        private val REQUEST_TAKE_PHOTO = 504
        val REQUEST_RESULT = 101
    }

    }

