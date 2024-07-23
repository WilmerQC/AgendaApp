package com.xcheko51x.agendaapp_curso.states

import com.xcheko51x.agendaapp_curso.models.Cita

data class AgendaState (
    var listaCitas: List<Cita> = emptyList()
)