package com.fitreal.ui.screens.authentication.sign_in

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
import androidx.compose.material3.TextButton
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
fun SignInScreen(
    openScreen: (Any) -> Unit,
    openAndPopUp: (Any, Any) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        launchCredManBottomSheet(context) { result ->
            viewModel.onSignInWithGoogle(result, openAndPopUp)
        }
    }

    SignInScreen(
        modifier = modifier,
        email = email.value,
        password = password.value,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        onSignInWithEmailClick = { viewModel.onSignInClick(openAndPopUp) },
        onSignInWithGoogle = { credentials ->
            viewModel.onSignInWithGoogle(credential = credentials, openAndPopUp)
        },
        onSignUpClick = { viewModel.onSignUpClick(openScreen) }
    )
}

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun SignInScreen(
        modifier: Modifier = Modifier,
        email: String,
        password: String,
        updateEmail: (String) -> Unit,
        updatePassword: (String) -> Unit,
        onSignInWithEmailClick: () -> Unit,
        onSignInWithGoogle: (Credential) -> Unit,
        onSignUpClick:() -> Unit,
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
                focusedContainerColor = Color.Transparent,
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
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = password,
            onValueChange = { updatePassword(it) },
            placeholder = { Text(stringResource(R.string.password)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        Button(
            onClick = { onSignInWithEmailClick() },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_in),
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

        AuthenticationButton(buttonText = R.string.sign_in_with_google) { credential ->
            onSignInWithGoogle(credential)
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))

        TextButton(onClick = { onSignUpClick() }) {
            Text(text = stringResource(R.string.sign_up_description), fontSize = 16.sp, color = PrimaryColor)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthPreview() {
    FitRealTheme {
        SignInScreen(
            email = "email",
            password = "password",
            updateEmail = { },
            updatePassword = { },
            onSignInWithEmailClick = { },
            onSignInWithGoogle = { },
            onSignUpClick = { }
        )
    }
}