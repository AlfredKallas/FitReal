package com.fitreal.ui.screens.utils

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestStoragePermission(onPermissionGranted: () -> Unit, onPermissionDenied : () -> Unit, showRational:() -> Unit = { }) {
    val permissionState = rememberMultiplePermissionsState(listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA))

    LaunchedEffect(permissionState) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) { // Only for Android 9 and below
            permissionState.launchMultiplePermissionRequest()
        } else {
            onPermissionGranted()
        }
    }

    when {
        permissionState.allPermissionsGranted -> onPermissionGranted()
        permissionState.shouldShowRationale -> showRational
        else -> onPermissionDenied
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun RequestCameraPermission(onPermissionGranted: () -> Unit, onPermissionDenied : () -> Unit, showRational:() -> Unit = { }) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(permissionState) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) { // Only for Android 9 and below
            permissionState.launchPermissionRequest()
        }
    }

    when {
        permissionState.status.isGranted-> onPermissionGranted()
        permissionState.status.shouldShowRationale -> showRational
        else -> onPermissionDenied
    }
}

fun saveImageToDisk(context: Context, bitmap: Bitmap, fileName: String): String? {
    val fileNameWithExtension = "$fileName.jpg"
    var outputStream: OutputStream? = null
    var savedImagePath: String? = null

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // ✅ Android 10+ (Scoped Storage) - Save to MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileNameWithExtension)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/WorkoutApp")
            }

            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            outputStream = uri?.let { context.contentResolver.openOutputStream(it) }
            savedImagePath = uri.toString() // Returns content URI
        } else {
            // ✅ Android 9 and below - Save to external storage
            val imageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "WorkoutApp")
            if (!imageDir.exists()) imageDir.mkdirs()
            val imageFile = File(imageDir, fileNameWithExtension)
            outputStream = FileOutputStream(imageFile)
            savedImagePath = imageFile.absolutePath
        }

        // 🔥 Compress and save image
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream!!)
        outputStream.flush()
        outputStream.close()
        return savedImagePath
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}