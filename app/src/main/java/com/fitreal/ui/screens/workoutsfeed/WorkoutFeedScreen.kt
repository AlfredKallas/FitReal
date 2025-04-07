package com.fitreal.ui.screens.workoutsfeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitreal.data.workout.Workout
import com.fitreal.ui.screens.utils.WorkoutItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutFeedScreen(
    viewModel: WorkoutsFeedViewModel = hiltViewModel(),
    openScreen: (Any) -> Unit
) {
    val workouts by viewModel.workouts.collectAsStateWithLifecycle()
    WorkoutFeedScreen(workouts = workouts,
        onProfileButtonClicked = {
            viewModel.onProfileButtonClicked(openScreen)
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutFeedScreen(
    workouts: List<Workout>,
    onProfileButtonClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Friends' Workouts") },
                actions = {
                    IconButton(onClick = { onProfileButtonClicked() }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(workouts) { workout ->
                WorkoutItem(workout)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun WorkoutFeedPreview() {
    WorkoutFeedScreen(workouts = listOf(
        Workout("https://example.com/workout3.jpg", "Full Body Workout", "30 min"),
        Workout("https://example.com/workout4.jpg", "Cardio Blast", "20 min")
    ),
    onProfileButtonClicked = {})
}