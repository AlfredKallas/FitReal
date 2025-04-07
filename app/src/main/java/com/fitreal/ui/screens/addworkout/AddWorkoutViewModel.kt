package com.fitreal.ui.screens.addworkout

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.fitreal.data.workout.Workout
import com.fitreal.data.workout.WorkoutsRepository
import com.fitreal.ui.screens.FitRealAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) : FitRealAppViewModel() {

    fun saveWorkout(title: String, duration: String, imageUri: Uri?) {
        val newWorkout = Workout(
            title = title,
            duration = duration,
            imageUrl = imageUri?.toString() ?: ""
        )

        viewModelScope.launch {
            workoutsRepository.addWorkOutForCurrentUser(newWorkout)

        }
    }
}