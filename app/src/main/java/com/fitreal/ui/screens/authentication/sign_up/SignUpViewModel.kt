package com.fitreal.ui.screens.authentication.sign_up

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.fitreal.data.account.AccountRepository
import com.fitreal.ui.SignInScreen
import com.fitreal.ui.screens.FitRealAppViewModel
import com.fitreal.ui.SignUpScreen
import com.fitreal.ui.WorkoutsScreen
import com.fitreal.ui.screens.authentication.ERROR_TAG
import com.fitreal.ui.screens.authentication.UNEXPECTED_CREDENTIAL
import com.fitreal.ui.screens.authentication.isValidEmail
import com.fitreal.ui.screens.authentication.isValidPassword
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : FitRealAppViewModel() {
    // Backing properties to avoid state updates from other classes
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun onSignUpClick(openAndPopUp: (Any, Any) -> Unit) {
        launchCatching {
            if (!_email.value.isValidEmail()) {
                throw IllegalArgumentException("Invalid email format")
            }

            if (!_password.value.isValidPassword()) {
                throw IllegalArgumentException("Invalid password format")
            }

            if (_password.value != _confirmPassword.value) {
                throw IllegalArgumentException("Passwords do not match")
            }

            accountRepository.signUp(_email.value, _password.value)
            openAndPopUp(WorkoutsScreen, SignInScreen)
        }
    }

    fun onSignUpWithGoogle(credential: Credential, openAndPopUp: (Any, Any) -> Unit) {
        launchCatching {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                accountRepository.linkAccountWithGoogle(googleIdTokenCredential.idToken)
                openAndPopUp(WorkoutsScreen, SignInScreen)
            } else {
                Log.e(ERROR_TAG, UNEXPECTED_CREDENTIAL)
            }
        }
    }
}