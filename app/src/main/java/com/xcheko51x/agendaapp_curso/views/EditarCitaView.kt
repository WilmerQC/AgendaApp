package com.xcheko51x.agendaapp_curso.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.xcheko51x.agendaapp_curso.models.Cita
import com.xcheko51x.agendaapp_curso.viewmodels.AgendaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCitaView(
    navController: NavHostController,
    viewModel: AgendaViewModel,
    idCita: String,
    nomPaciente: String,
    telPaciente: String,
    asunto: String,
    diaCita: String,
    horaCita: String
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Editar Cita", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                }
            )
        }
    ) {
        ContentEditarCitaView(
            it,
            navController,
            viewModel,
            idCita,
            nomPaciente,
            telPaciente,
            asunto,
            diaCita,
            horaCita
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContentEditarCitaView(
    it: PaddingValues,
    navController: NavController,
    viewModel: AgendaViewModel,
    idCita: String,
    nomPaciente: String,
    telPaciente: String,
    asunto: String,
    diaCita: String,
    horaCita: String
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var nomPaciente by remember { mutableStateOf(nomPaciente) }
    var telPaciente by remember { mutableStateOf(telPaciente) }
    var asunto by remember { mutableStateOf(asunto) }

    val diasLista = listOf("Selecciona un día", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
    var showDias by remember { mutableStateOf(false) }
    var diaSeleccionado by remember { mutableStateOf(diaCita) }

    val horasLista = listOf(
        "Selecciona una hora",
        "9:00 a 10:00",
        "10:00 a 11:00",
        "11:00 a 12:00",
        "12:00 a 13:00",
        "13:00 a 14:00",
        "14:00 a 15:00",
        "15:00 a 16:00",
        "16:00 a 17:00",
        "17:00 a 18:00",
        "18:00 a 19:00",
        "19:00 a 20:00"
    )
    var showHoras by remember { mutableStateOf(false) }
    var horaSeleccionado by remember { mutableStateOf(horaCita) }

    val maxTel = 10

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nomPaciente,
            onValueChange = { nomPaciente = it },
            label = { Text(text = "Nombre del Paciente") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )

        OutlinedTextField(
            value = telPaciente,
            onValueChange = {
                if (it.length <= maxTel) {
                    telPaciente = it
                }
            },
            label = { Text(text = "Telefono paciente") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )

        OutlinedTextField(
            value = asunto,
            onValueChange = { asunto = it },
            label = { Text(text = "Asunto") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )

        ExposedDropdownMenuBox(
            expanded = showDias,
            onExpandedChange = {
                showDias = !showDias
            },
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        ) {
            keyboardController?.hide()

            TextField(
                modifier = Modifier.menuAnchor(),
                value = diaSeleccionado,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDias)},
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = showDias,
                onDismissRequest = { showDias = false }
            ) {
                diasLista.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = {
                            Text(text = s)
                        },
                        onClick = {
                            if (s != diasLista[0]) {
                                diaSeleccionado = s
                            }
                            showDias = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = showHoras,
            onExpandedChange = {
                showHoras = !showHoras
            },
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        ) {
            keyboardController?.hide()

            TextField(
                modifier = Modifier.menuAnchor(),
                value = horaSeleccionado,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showHoras)},
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = showHoras,
                onDismissRequest = { showHoras = false }
            ) {
                horasLista.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = {
                            Text(text = s)
                        },
                        onClick = {
                            if (s != horasLista[0]) {
                                horaSeleccionado = s
                            }
                            showHoras = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Button(
            onClick = {
                val cita = Cita(
                    idCita,
                    nomPaciente,
                    telPaciente,
                    asunto,
                    diaSeleccionado,
                    horaSeleccionado
                )

                viewModel.actualizarCita(cita)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        ) {
            Text(text = "Actualizar Cita")
        }
    }
}