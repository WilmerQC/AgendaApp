package com.xcheko51x.agendaapp_curso.viewmodels

import android.app.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcheko51x.agendaapp_curso.models.Cita
import com.xcheko51x.agendaapp_curso.room.AgendaDatabaseDao
import com.xcheko51x.agendaapp_curso.states.AgendaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Period

/**
 * ViewModel para la gestión de la agenda.
 * Proporciona métodos para interactuar con la base de datos de citas.
 *
 * @param dao El DAO de la base de datos de la agenda.
 */
class AgendaViewModel(
    private val dao: AgendaDatabaseDao
) : ViewModel() {

    // Estado mutable que contiene la lista de citas y otros estados relacionados
    var state by mutableStateOf(AgendaState())
        private set

    // Inicialización del ViewModel
    init {
        viewModelScope.launch {
            // Recoge las citas de la base de datos y actualiza el estado
            dao.obtenerCitas().collectLatest {
                state = state.copy(listaCitas = it)
            }
        }
    }

    // Función para agregar una nueva cita a la base de datos
    fun agregarCita(cita: Cita) = viewModelScope.launch {
        dao.agregarCita(cita = cita)
    }

    // Función para actualizar una cita existente en la base de datos
    fun actualizarCita(cita: Cita) = viewModelScope.launch {
        dao.actualizarCita(cita = cita)
    }

    // Función para borrar una cita de la base de datos
    fun borrarCita(cita: Cita) = viewModelScope.launch {
        dao.borrarCita(cita = cita)
    }
}





