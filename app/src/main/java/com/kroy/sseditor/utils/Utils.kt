package com.kroy.sseditor.utils

import android.R
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import com.kroy.sseditor.models.ChatMessage
import com.kroy.sseditor.screens.CustomTelegramLayout
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream

object Utils {


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun CaptureAndSaveComposable(
        contactName: String,
        contactPic: Bitmap?,
        messages: List<ChatMessage>,
        initialTimeString: String, // Time in "hh:mm a" format, e.g., "12:48 AM"
        backgroundBitmap: Bitmap?,
        senderImage: Bitmap?,
        userReplySticker: Bitmap?
    ) {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            delay(5000) // Wait for 5 seconds
            captureAndSaveComposableToBitmap(
                context = context,
                contactName = contactName,
                contactPic = contactPic,
                messages = messages,
                initialTimeString = initialTimeString,
                backgroundBitmap = backgroundBitmap,
                senderImage = senderImage,
                userReplySticker = userReplySticker
            )
        }

        // Optionally, show some UI while waiting for the capture
        // Text(text = "Capturing Composable in 5 seconds...")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun captureAndSaveComposableToBitmap(
        context: Context,
        contactName: String,
        contactPic: Bitmap?,
        messages: List<ChatMessage>,
        initialTimeString: String, // Time in "hh:mm a" format, e.g., "12:48 AM"
        backgroundBitmap: Bitmap?,
        senderImage: Bitmap?,
        userReplySticker: Bitmap?
    ) {
        // Ensure context is an Activity
        val activity = context as? Activity ?: return

        val composeView = ComposeView(context).apply {
            setContent {
                CustomTelegramLayout(
                    contactName = contactName,
                    contactPic = contactPic,
                    messages = messages,
                    initialTimeString = initialTimeString,
                    backgroundBitmap = backgroundBitmap,
                    senderImage = senderImage,
                    userReplySticker = userReplySticker
                )
            }
        }

        // Attach ComposeView to the window so it can be rendered
        val parentView = activity.findViewById<ViewGroup>(android.R.id.content)
        parentView?.addView(composeView)

        // Define the target resolution
        val targetWidth = 1290 // iPhone 15 Pro Max width in pixels
        val targetHeight = 2796 // iPhone 15 Pro Max height in pixels

        // Wait for the ComposeView to be attached to the window and laid out
        composeView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (composeView.isAttachedToWindow) {
                    composeView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    // Create a bitmap with the target resolution
                    val bitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)

                    // Scale the canvas to fit the content correctly if needed
                    val scaleX = targetWidth.toFloat() / composeView.width
                    val scaleY = targetHeight.toFloat() / composeView.height
                    canvas.scale(scaleX, scaleY)

                    // Draw the ComposeView on the canvas
                    composeView.draw(canvas)

                    // Save the bitmap to gallery
                    saveBitmapToGallery(context, bitmap)

                    // Clean up: remove the view from its parent once done
                    parentView?.removeView(composeView)
                }
            }
        })
    }

    fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use MediaStore for Android 10 and above
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "saved_image_${System.currentTimeMillis()}.png")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                context.contentResolver.openOutputStream(it).use { outputStream ->
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    }
                }
                Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(context, "Error saving image", Toast.LENGTH_SHORT).show()
        } else {
            // Save directly for older Android versions
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(picturesDir, "saved_image_${System.currentTimeMillis()}.png")

            try {
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error saving image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getBitmapFromResource(context: Context, resourceId: Int): Bitmap? {
        return BitmapFactory.decodeResource(context.resources, resourceId)
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}