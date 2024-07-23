package com.xcheko51x.agendaapp_curso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.xcheko51x.agendaapp_curso.navigation.NavManager
import com.xcheko51x.agendaapp_curso.room.AgendaDatabase
import com.xcheko51x.agendaapp_curso.ui.theme.AgendaApp_CursoTheme
import com.xcheko51x.agendaapp_curso.viewmodels.AgendaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgendaApp_CursoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicialización de la base de datos Room
                    val database = Room.databaseBuilder(
                        this,
                        AgendaDatabase::class.java,
                        "db_agenda"
                    ).build()
                    // Obtención del DAO de citas
                    val dao = database.citasDao()
                    // Inicialización del ViewModel con el DAO
                    val viewModel = AgendaViewModel(dao = dao)
                    // Configuración del NavManager con el ViewModel
                    NavManager(viewModel = viewModel)
                }
            }
        }
    }
}