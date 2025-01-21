package unex.cum.reservasgo_dadm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import unex.cum.reservasgo_dadm.ui.theme.colorApp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import unex.cum.reservasgo_dadm.R
import unex.cum.reservasgo_dadm.viewmodel.LoginVM
import unex.cum.reservasgo_dadm.viewmodel.LoginVMFactory

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginVM: LoginVM
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by loginVM.loginResult.collectAsState()

    // Si el login es exitoso, navegar a MainScreen
    LaunchedEffect(loginResult) {
        if (loginResult == "success") {
            navController.navigate("mainScreen") {
                popUpTo("loginScreen") { inclusive = true }
            }
        }
    }


    Scaffold(
        modifier = Modifier.background(colorApp) // Aplica el color de fondo
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorApp), // Asegura que el fondo se aplica a toda la pantalla
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo de la app
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo de la app",
                modifier = Modifier.height(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground // Asegura contraste
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de entrada
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de inicio de sesión
            Button(
                onClick = {
                    loginVM.loginUser(email, password)
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Iniciar sesión")
            }

            loginResult?.let {
                if (it != "success") {
                    Text(text = it, color = Color.Red)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Opción de registro
            Row {
                Text("¿No está registrado?", color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = { navController.navigate("registerScreen") }
                ) {
                    Text("Regístrese aquí", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}