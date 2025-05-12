package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.List;

import com.proyecto.integrador1.proyecto_integrador.entities.CategoriaSolicitud;
import com.proyecto.integrador1.proyecto_integrador.entities.Dispositivo;
import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.proyecto.integrador1.proyecto_integrador.entities.TipoPrioridad;

public interface TicketService {
    List<TipoPrioridad> obtenerTodasLasPrioridades();

    List<CategoriaSolicitud> obtenerTodasLasCategorias();

    List<Dispositivo> obtenerTodosLosDispositivos();
    void guardarTicket(Ticket ticket);

}
