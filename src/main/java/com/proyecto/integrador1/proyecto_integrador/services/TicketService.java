package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.integrador1.proyecto_integrador.entities.CategoriaSolicitud;
import com.proyecto.integrador1.proyecto_integrador.entities.Dispositivo;
import com.proyecto.integrador1.proyecto_integrador.entities.Estado;
import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.proyecto.integrador1.proyecto_integrador.entities.TipoPrioridad;
import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;

public interface TicketService {
    void guardarTicket(Ticket ticket);
    Dispositivo obtenerDispositivoPorId(Long id);
     CategoriaSolicitud obtenerCategoriaPorId(Long id);
    TipoPrioridad obtenerPrioridadPorId(Long id);
    public Estado obtenerEstadoPorNombre(String nombre);
    List<Ticket> obtenerTicketsPorUsuario(Usuario usuario);
    Optional<Ticket> obtenerTicketPorId(Long id);
    public void actualizarTicket(Long id,Ticket tickeckActualizado);
    public void eliminarPorId(Long id);

}
