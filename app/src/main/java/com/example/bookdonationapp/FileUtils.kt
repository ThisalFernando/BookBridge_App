package com.example.bookdonationapp

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.IOException
import java.io.OutputStream

object FileUtils {
    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveBitmapToDownloads(context: Context, bitmap: Bitmap, filename: String): Boolean {
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, filename)
            put(MediaStore.Downloads.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                put(MediaStore.Downloads.IS_PENDING, 1)
            }
        }

        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        return if (uri != null) {
            try {
                val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                outputStream?.use {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                    contentResolver.update(uri, contentValues, null, null)
                }
                Toast.makeText(context, "QR Code saved to Downloads", Toast.LENGTH_SHORT).show()
                true
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to save QR Code", Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            Toast.makeText(context, "Failed to create MediaStore entry", Toast.LENGTH_SHORT).show()
            false
        }
    }
}