package com.proyecto.integrador1.proyecto_integrador.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.integrador1.proyecto_integrador.entities.CategoriaSolicitud;
import com.proyecto.integrador1.proyecto_integrador.entities.Dispositivo;
import com.proyecto.integrador1.proyecto_integrador.entities.Estado;
import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.proyecto.integrador1.proyecto_integrador.entities.TipoPrioridad;
import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;
import com.proyecto.integrador1.proyecto_integrador.repositories.UsuarioRepository;
import com.proyecto.integrador1.proyecto_integrador.services.TicketService;
import com.proyecto.integrador1.proyecto_integrador.services.UsuarioService;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TicketService ticketService;

    // muestra el formulario login
    @GetMapping("/login")
    public String login() {
        return "LoginCliente";
    }

    // mostrar formulario , crea un objeto Usuario para el formulario y devuelve la
    // vista RegistroUsuario
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "RegistroUsuario";
    }

    // valida si el usuario existe , si no existe lo guarda y manda al login
    // si hay error muestra mensaje y vuelve al formulario
    @PostMapping("/register")
    public String registrar(
            @ModelAttribute("usuario") Usuario usuario,
            @RequestParam("codigoEmpresa") String codigoEmpresa,
            Model model) {

        if (usuarioService.usuarioExiste(usuario.getEmail())) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "RegistroUsuario";
        }

        try {
            usuarioService.guardarUsuario(usuario, codigoEmpresa);
            return "redirect:/login?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "RegistroUsuario";
        }
    }

    // si hay usuarios autenticado , carga sus datos y los pasa a la vista
    // devuelve la lista HomeCliente
    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            model.addAttribute("usuario", usuario);
        }
        return "HomeCliente";
    }

    @GetMapping("/generar-ticket")
    public String mostrarFormularioTicket(Model model, Principal principal) {
        if (principal != null) {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            model.addAttribute("usuario", usuario);
        }
        model.addAttribute("ticket", new Ticket());

        //
        return "RegistroTickets";
    }

    //
    @PostMapping("/generar-ticket")
    public String guardarTicket(@RequestParam("descripcion") String descripcion,
            @RequestParam("direccion") String direccion,
            @RequestParam("dispositivoId") Long dispositivoId,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("prioridadId") Long prioridadId,
            Principal principal) {
        if (principal == null)
            return "redirect:/login";

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Dispositivo dispositivo = ticketService.obtenerDispositivoPorId(dispositivoId);
        CategoriaSolicitud categoria = ticketService.obtenerCategoriaPorId(categoriaId);
        TipoPrioridad prioridad = ticketService.obtenerPrioridadPorId(prioridadId);
        Estado estado = ticketService.obtenerEstadoPorNombre("En Espera");
        Ticket ticket = new Ticket();
        ticket.setDescripcion(descripcion);
        ticket.setDireccion(direccion);
        ticket.setFechaHoraSolicitudTicket(LocalDateTime.now());
        ticket.setUsuario(usuario);
        ticket.setDispositivo(dispositivo);
        ticket.setCategoria(categoria);
        ticket.setPrioridad(prioridad);
        ticket.setEstado(estado);

        ticketService.guardarTicket(ticket);

        return "redirect:/";
    }

    @GetMapping("/mis-tickets")
    public String mostrarMisTickets(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<Ticket> tickets = ticketService.obtenerTicketsPorUsuario(usuario);
        model.addAttribute("tickets", tickets);
        return "MisTickets";
    }

    // mostrar formulario de editar tickets
    @GetMapping("/editar-ticket/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Ticket ticket = ticketService.obtenerTicketPorId(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        model.addAttribute("ticket", ticket);
        model.addAttribute("usuario", usuario);
        return "EditarTicketCliente";
    }

    @PostMapping("/editar-ticket/{id}")
    public String actualizarTickets(@PathVariable Long id, @ModelAttribute("ticket") Ticket ticketActualizado,
            Principal principal, Model model, @RequestParam("descripcion") String descripcion,
            @RequestParam("direccion") String direccion,
            @RequestParam("dispositivoId") Long dispositivoId,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("prioridadId") Long prioridadId) {
        if (principal == null) {

            return "redirect:/login";
        }
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Dispositivo dispositivo = ticketService.obtenerDispositivoPorId(dispositivoId);
        CategoriaSolicitud categoria = ticketService.obtenerCategoriaPorId(categoriaId);
        TipoPrioridad prioridad = ticketService.obtenerPrioridadPorId(prioridadId);
        Estado estado = ticketService.obtenerEstadoPorNombre("En Espera");
        Ticket bdTicket = ticketService.obtenerTicketPorId(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        bdTicket.setId(id);
        bdTicket.setDescripcion(descripcion);
        bdTicket.setDireccion(direccion);
        bdTicket.setFechaHoraSolicitudTicket(LocalDateTime.now());
        bdTicket.setUsuario(usuario);
        bdTicket.setDispositivo(dispositivo);
        bdTicket.setCategoria(categoria);
        bdTicket.setPrioridad(prioridad);
        bdTicket.setEstado(estado);
        ticketService.guardarTicket(bdTicket);
        return "redirect:/mis-tickets";
    }

    @PostMapping("/eliminar-ticket/{id}")
    public String eliminarTicket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        // Verificar que el ticket pertenece al usuario actual
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Ticket ticket = ticketService.obtenerTicketPorId(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        // Validar que el ticket pertenece al usuario
        if (!ticket.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permiso para eliminar este ticket");
        }

        ticketService.eliminarPorId(id);

        return "redirect:/mis-tickets";
    }

}