package com.fitreal.ui.screens.addworkout

import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.fitreal.ui.screens.utils.RequestCameraPermission
import com.fitreal.ui.screens.utils.RequestStoragePermission
import com.fitreal.ui.theme.FitRealTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddWorkoutScreen(
    viewModel: AddWorkoutViewModel = hiltViewModel(),
    popBackStack:() -> Unit
) {
    AddWorkoutScreen(
        saveWorkout = viewModel::saveWorkout,
        popBackStack = popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddWorkoutScreen(
    saveWorkout: (String, String, Uri?) -> Unit,
    popBackStack:() -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var showCameraPermission by remember { mutableStateOf<Boolean>(false) }
    var showStoragePermission by remember { mutableStateOf<Boolean>(false) }


    val photoFile = remember { mutableStateOf<File?>(null) }
    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri = Uri.fromFile(photoFile.value)
        }
    }

    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            photoFile.value = this
        }
    }

    if(showStoragePermission){
        RequestStoragePermission(onPermissionGranted = {

        },
        onPermissionDenied = {

        })
    }

    if(showCameraPermission){
        RequestCameraPermission(onPermissionGranted = {

        },
        onPermissionDenied = {

        })
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Workout") }) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("Duration") })
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                    val file = createImageFile()
                    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                    takePictureLauncher.launch(uri)
            }) {
                Text("Take Photo")
            }

            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                saveWorkout(title, duration, imageUri) // Replace with actual user ID
                popBackStack()
            }) {
                Text("Save Workout")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddWorkoutScreenPreview() {
    FitRealTheme {
        AddWorkoutScreen(
            saveWorkout = { _, _, _ -> },
            popBackStack = { }
        )
    }
}