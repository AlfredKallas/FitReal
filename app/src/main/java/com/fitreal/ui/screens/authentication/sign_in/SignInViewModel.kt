package com.fitreal.ui.screens.authentication.sign_in

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.fitreal.data.account.AccountRepository
import com.fitreal.ui.screens.FitRealAppViewModel
import com.fitreal.ui.SignInScreen
import com.fitreal.ui.SignUpScreen
import com.fitreal.ui.WorkoutsScreen
import com.fitreal.ui.screens.authentication.ERROR_TAG
import com.fitreal.ui.screens.authentication.UNEXPECTED_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : FitRealAppViewModel() {
    // Backing properties to avoid state updates from other classes
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun onSignInClick(openAndPopUp: (Any, Any) -> Unit) {
        launchCatching {
            accountRepository.signInWithEmail(_email.value, _password.value)
            openAndPopUp(WorkoutsScreen, SignInScreen)
        }
    }

    fun onSignInWithGoogle(credential: Credential, openAndPopUp: (Any, Any) -> Unit) {
        launchCatching {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                accountRepository.signInWithGoogle(googleIdTokenCredential.idToken)
                openAndPopUp(WorkoutsScreen, SignInScreen)
            } else {
                Log.e(ERROR_TAG, UNEXPECTED_CREDENTIAL)
            }
        }
    }

    fun onSignUpClick(openScreen: (Any) -> Unit) {
        openScreen(SignUpScreen)
    }
}