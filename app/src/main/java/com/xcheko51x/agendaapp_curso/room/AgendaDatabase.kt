package com.xcheko51x.agendaapp_curso.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xcheko51x.agendaapp_curso.models.Cita

/**
 * Definición de la base de datos de la agenda.
 *
 * @Database anota esta clase como una base de datos de Room.
 * @param entities Las entidades de la base de datos (en este caso, la entidad Cita).
 * @param version La versión de la base de datos.
 * @param exportSchema Si el esquema debe ser exportado o no (en este caso, no se exporta).
 */
@Database(
    entities = [Cita::class],
    version = 1,
    exportSchema = false
)
abstract class AgendaDatabase: RoomDatabase() {
    // Método abstracto que retorna el DAO para acceder a las citas en la base de datos.
    abstract fun citasDao(): AgendaDatabaseDao
}