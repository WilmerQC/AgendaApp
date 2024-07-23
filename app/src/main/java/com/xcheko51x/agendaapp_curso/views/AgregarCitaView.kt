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
import com.xcheko51x.agendaapp_curso.models.Cita
import com.xcheko51x.agendaapp_curso.viewmodels.AgendaViewModel

/**
 * Vista para agregar una nueva cita.
 *
 * @param navController El controlador de navegación para gestionar las transiciones entre pantallas.
 * @param viewModel El ViewModel que proporciona los datos y operaciones para las citas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarCitaView(
    navController: NavController,
    viewModel: AgendaViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Agregar Cita",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        AgregarCitaContentView(it, navController, viewModel)
    }
}

/**
 * Contenido de la vista para agregar una nueva cita.
 *
 * @param it Padding de la pantalla principal.
 * @param navController El controlador de navegación para gestionar las transiciones entre pantallas.
 * @param viewModel El ViewModel que proporciona los datos y operaciones para las citas.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AgregarCitaContentView(
    it: PaddingValues,
    navController: NavController,
    viewModel: AgendaViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var nomPaciente by remember { mutableStateOf("") }
    var telPaciente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }

    val diasLista = listOf("Selecciona un día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    var showDias by remember { mutableStateOf(false) }
    var diaSeleccionado by remember { mutableStateOf(diasLista[0]) }

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
    var horaSeleccionado by remember { mutableStateOf(horasLista[0]) }

    val maxTel = 10

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto para el nombre del paciente
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

        // Campo de texto para el teléfono del paciente
        OutlinedTextField(
            value = telPaciente,
            onValueChange = {
                if (it.length <= maxTel) {
                    telPaciente = it
                }
            },
            label = { Text(text = "Teléfono Paciente") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )

        // Campo de texto para el asunto de la cita
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

        // Selector desplegable para elegir el día de la cita
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
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDias) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = showDias,
                onDismissRequest = { showDias = false }
            ) {
                diasLista.forEach { dia ->
                    DropdownMenuItem(
                        text = { Text(text = dia) },
                        onClick = {
                            if (dia != diasLista[0]) {
                                diaSeleccionado = dia
                            }
                            showDias = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        // Selector desplegable para elegir la hora de la cita
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
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showHoras) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = showHoras,
                onDismissRequest = { showHoras = false }
            ) {
                horasLista.forEach { hora ->
                    DropdownMenuItem(
                        text = { Text(text = hora) },
                        onClick = {
                            if (hora != horasLista[0]) {
                                horaSeleccionado = hora
                            }
                            showHoras = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        // Botón para agregar la cita
        Button(
            onClick = {
                val cita = Cita(
                    idCita = System.currentTimeMillis().toString(),
                    nomPaciente = nomPaciente,
                    telPaciente = telPaciente,
                    asunto = asunto,
                    diaCita = diaSeleccionado,
                    horaCita = horaSeleccionado
                )

                viewModel.agregarCita(cita)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        ) {
            Text(text = "Agendar Cita")
        }
    }
}