package com.xcheko51x.agendaapp_curso.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa una cita en la base de datos.
 *
 * @param idCita Identificador único de la cita.
 * @param nomPaciente Nombre del paciente.
 * @param telPaciente Teléfono del paciente.
 * @param asunto Asunto de la cita.
 * @param diaCita Día de la cita.
 * @param horaCita Hora de la cita.
 */
@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = false)
    val idCita: String,

    @ColumnInfo(name = "nomPaciente")
    var nomPaciente: String,

    @ColumnInfo(name = "telPaciente")
    val telPaciente: String,

    @ColumnInfo(name = "asunto")
    val asunto: String,

    @ColumnInfo(name = "diaCita")
    val diaCita: String,

    @ColumnInfo(name = "horaCita")
    val horaCita: String
)