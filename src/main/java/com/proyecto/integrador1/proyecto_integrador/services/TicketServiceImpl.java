package com.proyecto.integrador1.proyecto_integrador.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Dispositivo obtenerDispositivoPorId(Long id) {
        return dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
    }

    @Override
    public CategoriaSolicitud obtenerCategoriaPorId(Long id) {
        return categoriaSolicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Override
    public TipoPrioridad obtenerPrioridadPorId(Long id) {
        return tipoPrioridadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prioridad no encontrada"));
    }

    @Override
    public void guardarTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Estado obtenerEstadoPorNombre(String nombre) {
        return estadoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + nombre));
    }

    @Override
    public List<Ticket> obtenerTicketsPorUsuario(Usuario usuario) {
        return ticketRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Ticket> obtenerTicketPorId(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public void actualizarTicket(Long id, Ticket tickeckActualizado) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        Ticket ticketExistente = ticketOptional
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));

        ticketExistente.setDescripcion(tickeckActualizado.getDescripcion());
        ticketExistente.setDireccion(tickeckActualizado.getDireccion());
        ticketExistente.setFechaHoraSolicitudTicket(LocalDateTime.now());
        ticketExistente.setUsuario(tickeckActualizado.getUsuario());
        ticketExistente.setDispositivo(tickeckActualizado.getDispositivo());
        ticketExistente.setCategoria(tickeckActualizado.getCategoria());
        ticketExistente.setPrioridad(tickeckActualizado.getPrioridad());
        ticketExistente.setEstado(tickeckActualizado.getEstado());
        ticketRepository.save(ticketExistente);

    }

    @Override
    public void eliminarPorId(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<Ticket> obtenerTodosTicketsPorUniversidad(String nombreUniversidad) {
        return ticketRepository.obtenerTodosTicketsPorUniversidad(nombreUniversidad);
    }

    @Override
    public List<Ticket> obtenerTicketsOrdenadosPorPrioridad(String universidad) {
        return ticketRepository.obtenerTicketOrdenadosPorPrioridad(universidad);
    }

}
