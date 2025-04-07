package com.fitreal.ui.screens.profile

import androidx.lifecycle.viewModelScope
import com.fitreal.data.workout.Workout
import com.fitreal.data.workout.WorkoutsRepository
import com.fitreal.ui.AddNewWorkoutScreen
import com.fitreal.ui.screens.FitRealAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
): FitRealAppViewModel() {

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    init {
        getFeedWorkout()
    }

    private fun getFeedWorkout() {
        launchCatching {
            workoutsRepository.getCurrentUserWorkouts().collectLatest { latestWorkout ->
                _workouts.update { latestWorkout }
            }
        }
    }

    fun onAddWorkoutButtonClicked(goToScreen: (Any) -> Unit) {
        goToScreen(AddNewWorkoutScreen)
    }
}