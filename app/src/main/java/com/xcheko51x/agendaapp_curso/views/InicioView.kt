package com.xcheko51x.agendaapp_curso.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xcheko51x.agendaapp_curso.viewmodels.AgendaViewModel

/**
 * Vista principal de la aplicación, donde se muestra la lista de citas.
 *
 * @param navController El controlador de navegación para gestionar las transiciones entre pantallas.
 * @param viewModel El ViewModel que proporciona los datos y operaciones para las citas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioView(
    navController: NavController,
    viewModel: AgendaViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "AgendaApp",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("agregar")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Cita"
                )
            }
        }
    ) {
        // Contenido de la vista de inicio
        InicioContentView(it, navController, viewModel)
    }
}

/**
 * Contenido principal de la vista de inicio, que muestra una lista de citas.
 *
 * @param it Padding de la pantalla principal.
 * @param navController El controlador de navegación para gestionar las transiciones entre pantallas.
 * @param viewModel El ViewModel que proporciona los datos y operaciones para las citas.
 */
@Composable
fun InicioContentView(
    it: PaddingValues,
    navController: NavController,
    viewModel: AgendaViewModel
) {
    val txtPaciente = remember { mutableStateOf("") }
    val state = viewModel.state

    Column(
        modifier = Modifier.padding(it)
    ) {
        // Campo de texto para buscar citas por nombre del paciente
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                value = txtPaciente.value,
                placeholder = {
                    Text(text = "Nombre del paciente a buscar ...")
                },
                onValueChange = {
                    txtPaciente.value = it
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Lista de citas
        LazyColumn {
            items(
                state.listaCitas.filter {
                    it.nomPaciente.contains(txtPaciente.value, ignoreCase = true)
                }.sortedBy { it.diaCita }
                    .sortedByDescending { it.horaCita }
            ) { cita ->

                // Determina el color de la tarjeta basado en el día de la cita
                val color = when (cita.diaCita) {
                    "Lunes" -> Color.Blue
                    "Martes" -> Color.Red
                    "Miércoles" -> Color.Green
                    "Jueves" -> Color.Yellow
                    "Viernes" -> Color.Cyan
                    "Sábado" -> Color.Gray
                    "Domingo" -> Color.White
                    else -> Color.LightGray
                }

                // Tarjeta para mostrar la cita
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = color,
                        contentColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = cita.nomPaciente,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = cita.diaCita,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = cita.horaCita,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        // Botones para editar y borrar la cita
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            IconButton(
                                onClick = {
                                    navController.navigate(
                                        "editar/${cita.idCita}/${cita.nomPaciente}/${cita.telPaciente}/${cita.asunto}/${cita.diaCita}/${cita.horaCita}"
                                    )
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                            }
                            IconButton(
                                onClick = { viewModel.borrarCita(cita) }
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar")
                            }
                        }
                    }
                }
            }
        }
    }
}