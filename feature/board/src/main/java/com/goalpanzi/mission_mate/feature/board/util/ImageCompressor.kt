package com.goalpanzi.mission_mate.feature.board.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object ImageCompressor {

    fun getCompressedImage(context: Context, filePath: Uri) : File {

        val bitmap = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, filePath)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, filePath)
            ImageDecoder.decodeBitmap(source)
        }

        val file = File(context.cacheDir, "${filePath.toString().split("/").last()}.jpg")

        try {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val byteArray = outputStream.toByteArray()
            val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            val fileOutputStream = FileOutputStream(file)
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }
}