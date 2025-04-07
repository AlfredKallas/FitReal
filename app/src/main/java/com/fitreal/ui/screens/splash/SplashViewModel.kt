package com.fitreal.ui.screens.splash

import com.fitreal.data.account.AccountRepository
import com.fitreal.ui.SignInScreen
import com.fitreal.ui.SignUpScreen
import com.fitreal.ui.SplashScreen
import com.fitreal.ui.WorkoutsScreen
import com.fitreal.ui.screens.FitRealAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val accountRepository: AccountRepository
) : FitRealAppViewModel() {

  fun onAppStart(openAndPopUp: (Any, Any) -> Unit) {
    if (accountRepository.hasUser()) openAndPopUp(WorkoutsScreen, SplashScreen)
    else openAndPopUp(SignInScreen, SplashScreen)
  }

}
