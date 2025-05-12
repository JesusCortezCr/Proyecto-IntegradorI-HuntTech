package com.proyecto.integrador1.proyecto_integrador.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proyecto.integrador1.proyecto_integrador.entities.CategoriaSolicitud;
import com.proyecto.integrador1.proyecto_integrador.entities.Dispositivo;
import com.proyecto.integrador1.proyecto_integrador.entities.Estado;
import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.proyecto.integrador1.proyecto_integrador.entities.TipoPrioridad;
import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;
import com.proyecto.integrador1.proyecto_integrador.repositories.CategoriaSolicitudRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.DispositivoRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.EstadoRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.TicketRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.TipoPrioridadRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.UsuarioRepository;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private CategoriaSolicitudRepository categoriaSolicitudRepository;

    @Autowired
    private TipoPrioridadRepository tipoPrioridadRepository;

    @Override
    public List<TipoPrioridad> obtenerTodasLasPrioridades() {
        return tipoPrioridadRepository.findAll();
    }

    @Override
    public List<CategoriaSolicitud> obtenerTodasLasCategorias() {
        return categoriaSolicitudRepository.findAll();
    }

    @Override
    public List<Dispositivo> obtenerTodosLosDispositivos() {
        return dispositivoRepository.findAll();

    }

    @Override
    public void guardarTicket(Ticket ticket) {
         ticketRepository.save(ticket);
    }

}
