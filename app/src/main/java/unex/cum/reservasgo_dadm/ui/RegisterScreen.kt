package unex.cum.reservasgo_dadm.ui

import android.util.Log
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
import unex.cum.reservasgo_dadm.viewmodel.RegisterVM
import unex.cum.reservasgo_dadm.viewmodel.RegisterVMFactory

@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerVM: RegisterVM = viewModel(factory = RegisterVMFactory())
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    val registerResult by registerVM.registerResult.collectAsState()

    // Verifica si el registro fue exitoso y navega al login
    LaunchedEffect(registerResult) {
        if (registerResult == "Usuario registrado correctamente") {
            navController.navigate("loginScreen") {
                popUpTo("registerScreen") { inclusive = true }
            }
        }
    }


    Scaffold(
        modifier = Modifier.background(colorApp) // Color de fondo
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorApp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo de la app
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo de la app",
                modifier = Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "Registrarse",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de entrada
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

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

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de registro
            Button(
                onClick = {
                    registerVM.registrarUsuario(nombre, email, password, direccion, telefono)
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Registrarse")
            }

            registerResult?.let {
                Text(text = it, color = if (it.contains("correctamente")) Color.Green else Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Opción de inicio de sesión
            Row {
                Text("¿Ya tiene cuenta?", color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = { navController.navigate("loginScreen") }
                ) {
                    Text("Inicie sesión aquí", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
