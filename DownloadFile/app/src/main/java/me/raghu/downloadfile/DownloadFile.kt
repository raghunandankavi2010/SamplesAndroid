package me.raghu.downloadfile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.withContext
import java.io.EOFException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadFile {

    private val READ_SIZE = 1024 * 2


    suspend fun downloadFile(url: URL, channel: SendChannel<Float>): ByteArray? = withContext(Dispatchers.IO) {

        var byteBuffer: ByteArray? = null

        try {
            // If there's no cache buffer for this image
            if (null == byteBuffer) {


                // Defines a handle for the byte download stream
                var byteStream: InputStream? = null

                // Downloads the image and catches IO errors
                try {

                    // Opens an HTTP connection to the image's URL
                    val httpConn = url.openConnection() as HttpURLConnection

                    // Gets the input stream containing the image
                    byteStream = httpConn.inputStream

                    val contentSize = httpConn.contentLength


                    if (-1 == contentSize) {

                        // Allocates a temporary buffer
                        var tempBuffer = ByteArray(READ_SIZE)

                        // Records the initial amount of available space
                        var bufferLeft = tempBuffer.size

                        var bufferOffset = 0
                        var readResult = 0


                        outer@ do {
                            while (bufferLeft > 0) {

                                readResult = byteStream!!.read(
                                    tempBuffer, bufferOffset,
                                    bufferLeft
                                )

                                if (readResult < 0) {
                                    // The read is finished, so this breaks
                                    // the to "outer" loop
                                    break@outer
                                }

                                bufferOffset += readResult

                                // Subtracts the number of bytes read from
                                // the amount of buffer left
                                bufferLeft -= readResult

                            }


                            // Resets the amount of buffer left to be the
                            // max buffer size
                            bufferLeft = READ_SIZE


                            val newSize = tempBuffer.size + READ_SIZE

                            val expandedBuffer = ByteArray(newSize)
                            System.arraycopy(
                                tempBuffer, 0, expandedBuffer, 0,
                                tempBuffer.size
                            )
                            tempBuffer = expandedBuffer
                        } while (true)


                        byteBuffer = ByteArray(bufferOffset)

                        // Copies the temporary buffer to the image buffer
                        System.arraycopy(tempBuffer, 0, byteBuffer, 0, bufferOffset)


                    } else {
                        byteBuffer = ByteArray(contentSize)

                        // How much of the buffer still remains empty
                        var remainingLength = contentSize

                        // The next open space in the buffer
                        var bufferOffset = 0
                        var totalBytesRead = 0;


                        while (remainingLength > 0) {
                            val readResult = byteStream!!.read(
                                byteBuffer,
                                bufferOffset,
                                remainingLength
                            )
                            totalBytesRead += readResult
                            //Log.i("DownloadFile :","Background thread that does some computation :${Thread.currentThread().name}")
                            val progress = (totalBytesRead / (contentSize / 100f))
                            Log.i(
                                "DownloadFile :",
                                "Background thread that does some computation :${Thread.currentThread().name} $progress"
                            )

                            channel.send(progress)


                            if (readResult < 0) {

                                // Throws an EOF Exception
                                throw EOFException()
                            }

                            // Moves the buffer offset to the next open byte
                            bufferOffset += readResult

                            // Subtracts the # of bytes read from the
                            // remaining length
                            remainingLength -= readResult

                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()

                } finally {
                    channel.close()
                    if (null != byteStream) {
                        try {
                            byteStream.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
            }

            // Catches exceptions thrown in response to a queued interrupt
        } catch (e1: Exception) {
            e1.printStackTrace()

        }
        byteBuffer

    }

    suspend fun processImage(byteArray: ByteArray, targetWidth: Int, targetHeight: Int): Bitmap? = withContext(Dispatchers.IO) {


        // Defines the Bitmap object that this thread will create
        var returnBitmap: Bitmap? = null


        try {

            // Sets up options for creating a Bitmap object from the
            // downloaded image.
            val bitmapOptions = BitmapFactory.Options()


            bitmapOptions.inJustDecodeBounds = true

            BitmapFactory
                .decodeByteArray(byteArray, 0, byteArray.size, bitmapOptions)


            val hScale = bitmapOptions.outHeight / targetHeight
            val wScale = bitmapOptions.outWidth / targetWidth

            val sampleSize = Math.max(hScale, wScale)

            if (sampleSize > 1) {
                bitmapOptions.inSampleSize = sampleSize
            }

            // Second pass of decoding. If no bitmap is created, nothing
            // is set in the object.
            bitmapOptions.inJustDecodeBounds = false

            returnBitmap = BitmapFactory.decodeByteArray(
                byteArray,
                0,
                byteArray.size,
                bitmapOptions
            )


        } catch (e1: Throwable) {

            e1.printStackTrace()

        }
        returnBitmap
    }
}
