package com.xcheko51x.agendaapp_curso.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xcheko51x.agendaapp_curso.models.Cita
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz DAO para la base de datos de la agenda.
 * Define las operaciones de base de datos que se pueden realizar en la entidad Cita.
 */
@Dao
interface AgendaDatabaseDao {

    /**
     * Consulta para obtener todas las citas.
     *
     * @return Un Flow que emite una lista de todas las citas.
     */
    @Query("SELECT * FROM citas")
    fun obtenerCitas(): Flow<List<Cita>>

    /**
     * Inserta una nueva cita en la base de datos.
     *
     * @param cita La cita a insertar.
     */
    @Insert
    suspend fun agregarCita(cita: Cita)

    /**
     * Actualiza una cita existente en la base de datos.
     *
     * @param cita La cita a actualizar.
     */
    @Update
    suspend fun actualizarCita(cita: Cita)

    /**
     * Borra una cita de la base de datos.
     *
     * @param cita La cita a borrar.
     */
    @Delete
    suspend fun borrarCita(cita: Cita)
}