package com.kroy.sseditor.utils

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Build
import android.os.Environment
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
        contactName:String,
        contactPic:Bitmap?,
        messages: List<ChatMessage>,
        initialTimeString: String, // Time in "hh:mm a" format, e.g., "12:48 AM"
        backgroundBitmap: Bitmap?,
        senderImage: Bitmap?,
        userReplySticker: Bitmap?
    ) {
        val context = LocalContext.current


        LaunchedEffect(Unit) {
            delay(5000) // Wait for 5 seconds
            captureAndSaveComposableToBitmap(context, contactName =contactName,
                contactPic = contactPic,
                messages = messages,
                initialTimeString = initialTimeString,
                backgroundBitmap = backgroundBitmap,
                senderImage = senderImage,
                userReplySticker = userReplySticker)
        }

        // Show some UI while waiting for the capture
        // Text(text = "Capturing Composable in 5 seconds...")
    }

    // Captures the Composable content as a bitmap outside the Composable context
    @RequiresApi(Build.VERSION_CODES.O)
    fun captureAndSaveComposableToBitmap(context: Context,   contactName:String,
                                         contactPic:Bitmap?,
                                         messages: List<ChatMessage>,
                                         initialTimeString: String, // Time in "hh:mm a" format, e.g., "12:48 AM"
                                         backgroundBitmap: Bitmap?,
                                         senderImage: Bitmap?,
                                         userReplySticker: Bitmap?) {
        val composeView = ComposeView(context).apply {
            setContent {
                CustomTelegramLayout(   contactName ="Random Name",
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
        val parentView = (context as? Activity)?.findViewById<ViewGroup>(android.R.id.content)
        parentView?.addView(composeView)

        // Wait for the ComposeView to be attached to the window and laid out
        composeView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Ensure it's attached
                if (composeView.isAttachedToWindow) {
                    // Remove listener after it's been called once
                    composeView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    // Capture the bitmap
                    val bitmap = Bitmap.createBitmap(composeView.width, composeView.height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    composeView.draw(canvas)

                    // Save the bitmap (you can modify this to save it wherever needed)
                    saveBitmapToGallery(context, bitmap)

                    // Clean up: remove the view from its parent once done
                    parentView?.removeView(composeView)
                }
            }
        })
    }
    fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(picturesDir, "saved_image_${System.currentTimeMillis()}.png")
        Log.d("Image capturing->","saving image at ${file.absolutePath}")

        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error saving image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun getBitmapFromResource(context: Context, resourceId: Int): Bitmap? {
        return BitmapFactory.decodeResource(context.resources, resourceId)
    }

}