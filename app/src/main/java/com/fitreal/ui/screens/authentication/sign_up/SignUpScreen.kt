package com.fitreal.ui.screens.authentication.sign_up

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitreal.R
import com.fitreal.ui.screens.authentication.AuthenticationButton
import com.fitreal.ui.screens.authentication.launchCredManBottomSheet
import com.fitreal.ui.theme.FitRealTheme
import com.fitreal.ui.theme.PrimaryColor

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignUpScreen(
    openAndPopUp: (Any, Any) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()
    val confirmPassword = viewModel.confirmPassword.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        launchCredManBottomSheet(context) { result ->
            viewModel.onSignUpWithGoogle(result, openAndPopUp)
        }
    }

    SignUpScreen(
        modifier = modifier,
        email = email.value,
        password = password.value,
        confirmPassword = confirmPassword.value,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        updateConfirmPassword = viewModel::updateConfirmPassword,
        onSignUpWithEmailClick = { viewModel.onSignUpClick(openAndPopUp) },
        onSignUpWithGoogle = { credential ->
            viewModel.onSignUpWithGoogle(credential, openAndPopUp)
        }
    )
}

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun SignUpScreen(
        modifier: Modifier = Modifier,
        email: String,
        password: String,
        confirmPassword: String,
        updateEmail: (String) -> Unit,
        updatePassword: (String) -> Unit,
        updateConfirmPassword: (String) -> Unit,
        onSignUpWithEmailClick: () -> Unit,
        onSignUpWithGoogle: (Credential) -> Unit
    ) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = PrimaryColor),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = email,
            onValueChange = { updateEmail(it) },
            placeholder = { Text(stringResource(R.string.email)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = PrimaryColor),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = password,
            onValueChange = { updatePassword(it) },
            placeholder = { Text(stringResource(R.string.password)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email") },
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = PrimaryColor),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = confirmPassword,
            onValueChange = { updateConfirmPassword(it) },
            placeholder = { Text(stringResource(R.string.confirm_password)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        Button(
            onClick = { onSignUpWithEmailClick() },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 16.sp,
                modifier = modifier.padding(0.dp, 6.dp)
            )
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))

        Text(text = stringResource(R.string.or), fontSize = 16.sp, color = PrimaryColor)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))

        AuthenticationButton(R.string.sign_up_with_google) { credential ->
            onSignUpWithGoogle(credential)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthPreview() {
    FitRealTheme {
        SignUpScreen(
            email = "email",
            password = "password",
            confirmPassword = "password",
            updateEmail = { },
            updatePassword = { },
            updateConfirmPassword = { },
            onSignUpWithEmailClick = { },
            onSignUpWithGoogle = { }
        )
    }
}