package com.proyecto.integrador1.proyecto_integrador.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.integrador1.proyecto_integrador.entities.CategoriaSolicitud;
import com.proyecto.integrador1.proyecto_integrador.entities.Dispositivo;
import com.proyecto.integrador1.proyecto_integrador.entities.Empresa;
import com.proyecto.integrador1.proyecto_integrador.entities.Estado;
import com.proyecto.integrador1.proyecto_integrador.entities.InformeIncidencia;
import com.proyecto.integrador1.proyecto_integrador.entities.Rol;
import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.proyecto.integrador1.proyecto_integrador.entities.TipoPrioridad;
import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;
import com.proyecto.integrador1.proyecto_integrador.repositories.EmpresaRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.RolRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.TicketRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.TipoPrioridadRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.UsuarioRepository;
import com.proyecto.integrador1.proyecto_integrador.services.CategoriaSolicitudService;
import com.proyecto.integrador1.proyecto_integrador.services.DispositivoService;
import com.proyecto.integrador1.proyecto_integrador.services.InformeIncidenciaService;
import com.proyecto.integrador1.proyecto_integrador.services.PdfGeneratorService;
import com.proyecto.integrador1.proyecto_integrador.services.RolService;
import com.proyecto.integrador1.proyecto_integrador.services.TicketService;
import com.proyecto.integrador1.proyecto_integrador.services.UsuarioService;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    
        @Autowired
private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private InformeIncidenciaService informeIncidenciaService;

    @Autowired
    private RolService rolService;

    @Autowired
    private CategoriaSolicitudService categoriaSolicitudService;

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private TipoPrioridadRepository tipoPrioridadRepository;

    private final TicketRepository ticketRepository;

    public AuthController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

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
            if ("ROLE_CLIENTE".equals(usuario.getRol().getNombre())) {
                return "HomeCliente";
            } else if ("ROLE_TECNICO".equals(usuario.getRol().getNombre())) {
                return "HomeAdministrador";
            } else if ("ROLE_ADMINISTRADOR".equals(usuario.getRol().getNombre())) {
                return "PrincipalAdministrador";
            }
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
        List<TipoPrioridad> tiposPrioridad = tipoPrioridadRepository.findAll();
        List<Dispositivo> dispositivos = dispositivoService.traerDispositivos();
        List<CategoriaSolicitud> categorias = categoriaSolicitudService.findAllCategoriasSolicitud();
        model.addAttribute("categorias", categorias);
        model.addAttribute("dispositivos", dispositivos);
        model.addAttribute("prioridades", tiposPrioridad);
        model.addAttribute("ticket", new Ticket());

        //
        return "RegistroTickets";
    }

    //
    @PostMapping("/generar-ticket")
    public String guardarTicket(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("direccion") String direccion,
            @RequestParam("dispositivoId") Long dispositivoId,
            @RequestParam("categoriaId") Long categoriaId,
            Principal principal) {

        if (principal == null)
            return "redirect:/login";

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Dispositivo dispositivo = ticketService.obtenerDispositivoPorId(dispositivoId);
        CategoriaSolicitud categoria = ticketService.obtenerCategoriaPorId(categoriaId);
        Estado estado = ticketService.obtenerEstadoPorNombre("En Espera");

        // Lógica para asignar prioridad según categoría
        TipoPrioridad prioridad = determinarPrioridadPorCategoria(categoria);

        Ticket ticket = new Ticket();
        ticket.setDescripcion(descripcion);
        ticket.setDireccion(direccion);
        ticket.setFechaHoraSolicitudTicket(LocalDateTime.now());
        ticket.setUsuario(usuario);
        ticket.setDispositivo(dispositivo);
        ticket.setCategoria(categoria);
        ticket.setPrioridad(prioridad); // Prioridad asignada automáticamente
        ticket.setEstado(estado);
        ticket.setTecnico_id(0L);
        ticketService.guardarTicket(ticket);
        return "redirect:/";
    }

    // Método auxiliar para definir prioridades
    private TipoPrioridad determinarPrioridadPorCategoria(CategoriaSolicitud categoria) {
        Long categoriaId = categoria.getId();

        // Prioridades según categoría (ajusta según tus reglas)
        if (categoriaId == 2L) { // 2 = Red → Urgente (3)
            return ticketService.obtenerPrioridadPorId(3L);
        } else if (categoriaId == 4L || categoriaId == 7L) { // 4 = Acceso y seguridad, 7 = Plataforma → Alta (2)
            return ticketService.obtenerPrioridadPorId(2L);
        } else { // Resto → Normal (1)
            return ticketService.obtenerPrioridadPorId(1L);
        }
    }

    @GetMapping("/mis-tickets")
    public String mostrarMisTickets(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<Ticket> tickets = ticketService.obtenerTicketsPorUsuario(usuario);
        model.addAttribute("tickets", tickets);
        model.addAttribute("usuario", usuario);
        return "MisTickets";
    }

    @GetMapping("/tickets-administrador")
    public String mostrarTicketsTecnico(@RequestParam(required = false) String orden, Model model,
            Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<Ticket> tickets;
        if ("prioridad".equals(orden)) {
            tickets = ticketService.obtenerTicketsOrdenadosPorPrioridad(usuario.getEmpresa().getNombreEmpresa());
        } else {
            tickets = ticketService.obtenerTicketsPorUniversidadPorTecnico(usuario.getEmpresa().getNombreEmpresa(), usuario.getId());
        }
        model.addAttribute("tickets", tickets);
        model.addAttribute("usuario", usuario);
        model.addAttribute("informeService", informeIncidenciaService);
        return "TicketsGenerados";
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

    @GetMapping("/mi-empresa")
    public String mostrarMiEmpresa(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("empresa", usuario.getEmpresa());
        return "MiEmpresa";
    }

    @PostMapping("/mi-empresa")
    public String editarCodigoEmpresa(@RequestParam("codigoEmpresa") String codigoEmpresa, Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Empresa empresa = usuario.getEmpresa();
        empresa.setCodigoEmpresa(codigoEmpresa);
        empresaRepository.save(empresa); // Guardar los cambios

        model.addAttribute("usuario", usuario); // volver a cargar datos
        model.addAttribute("mensaje", "Código actualizado correctamente");

        return "redirect:/mi-empresa"; // Redirige a la misma página (puedes cambiarlo según el nombre real)
    }

    // cambiar estado a rechazado
    @GetMapping("/rechazar-ticket/{id}")
    public String rechazarTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.obtenerTicketPorId(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        Estado estadoRechazado = ticketService.obtenerEstadoPorNombre("Rechazado");
        ticket.setEstado(estadoRechazado);

        ticketService.guardarTicket(ticket);

        return "redirect:/tickets-administrador";
    }

    @GetMapping("resolver-ticket/{id}")
    public String resolverTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.obtenerTicketPorId(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        Estado estadoResuelto = ticketService.obtenerEstadoPorNombre("Resuelto");
        ticket.setEstado(estadoResuelto);
        ticketService.guardarTicket(ticket);
        return "redirect:/tickets-administrador";

    }

    @GetMapping("prioridad-ticket/{id}")
    public String guardarPrioridad(@PathVariable Long id, @RequestParam("prioridadId") Long prioridadId) {
        Ticket ticket = ticketService.obtenerTicketPorId(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        TipoPrioridad prioridad = ticketService.obtenerPrioridadPorId(prioridadId);
        ticket.setPrioridad(prioridad);
        ticketService.guardarTicket(ticket);
        return "redirect:/tickets-administrador";

    }

    // mostrar generar informe de incidencias
    @GetMapping("/generar-informe/{id}")
    public String mostrarGenerarInformeIncidencias(@PathVariable Long id, Model model, Principal principal) {
        Optional<Ticket> ticketOptional = ticketService.obtenerTicketPorId(id);
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(principal.getName());
        if (ticketOptional.isPresent() && usuarioOptional.isPresent()) {
            model.addAttribute("ticket", ticketOptional.get());
            model.addAttribute("usuario", usuarioOptional.get());
        }
        InformeIncidencia informeIncidencia = new InformeIncidencia();
        model.addAttribute("informe", informeIncidencia);
        return "GenerarInformesIncidencias";
    }

    @PostMapping("/generar-informe/{id}")
    public String generarInformeIncidencia(
            @RequestParam("descripcion") String descripcion, @PathVariable Long id, Model model,
            @RequestParam("titulo") String titulo,
            Principal principal) {

        InformeIncidencia informe = new InformeIncidencia();
        Optional<Ticket> ticketOptional = ticketService.obtenerTicketPorId(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            informe.setTicket(ticket);
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(principal.getName());
        if (usuarioOptional.isPresent()) {
            informe.setUsuario(usuarioOptional.get());
        }
        informe.setDescripcion(descripcion);
        informe.setFechaHoraInforme(LocalDateTime.now());
        informe.setTitulo(titulo);
        informeIncidenciaService.guardar(informe);
        return "redirect:/tickets-administrador";
        // return "redirect:/login?success";
        /**
         * <div th:if="${param.success}" style="color: green;">
         * Registro exitoso! Por favor inicia sesión
         * </div>
         */
    }

    @GetMapping("/mostrar-historial-informes")
    public String mostrarHistorialInformes(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<InformeIncidencia> informes = informeIncidenciaService
                .obtenerInformesPorUniversidad(usuario.getEmpresa().getNombreEmpresa());
        model.addAttribute("informes", informes);
        model.addAttribute("usuario", usuario);

        return "HistorialInformes";

    }

    @GetMapping("/mantenimiento-tecnicos")
    public String mostrarMantenimientoTecnicos(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Usuario tecnico = new Usuario();
        List<Usuario> tecnicos = usuarioService.listaTecnicos(usuario.getEmpresa().getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("tecnico", tecnico);
        model.addAttribute("tecnicos", tecnicos);

        return "MantenimientoTecnicos";

    }

    @GetMapping("/agregar-tecnico")
    public String mostrarAgregarTecnico(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Usuario tecnico = new Usuario();
        model.addAttribute("tecnico", tecnico);
        model.addAttribute("usuario", usuario);
        return "AgregarTecnico";

    }

    @PostMapping("/agregar-tecnico")
    public String agregarTecnico(@ModelAttribute("tecnico") Usuario tecnicoRecibo,
            Principal principal) {

        Usuario usuarioActual = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Rol rolTecnico = rolRepository.findById(2L)
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol();
                    nuevoRol.setId(2L);
                    nuevoRol.setNombre("ROLE_TECNICO");
                    return rolRepository.save(nuevoRol);
                });

        tecnicoRecibo.setRol(rolTecnico);
        tecnicoRecibo.setEmpresa(usuarioActual.getEmpresa());
        usuarioService.guardarTecnico(tecnicoRecibo);
        return "redirect:/mantenimiento-tecnicos";
    }

    @PostMapping("eliminar-tecnico/{id}")
    public String eliminarTecnico(@PathVariable Long id) {
        usuarioService.eliminarTecnicoPorId(id);
        return "redirect:/mantenimiento-tecnicos";
    }

    @GetMapping("/mostrar-lista-asignar-tickets")
    public String mostrarListaAsignarTickets(@RequestParam(required = false) String orden, Principal principal,
            Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<Ticket> tickets;
        tickets = ticketService.obtenerTodosTicketsPorUniversidad(usuario.getEmpresa().getNombreEmpresa());
        // Traer lista de tecnicos de esa empresa
        List<Usuario> tecnicos = usuarioService.listaTecnicos(usuario.getEmpresa().getId());
        model.addAttribute("tickets", tickets);
        model.addAttribute("usuario", usuario);
        model.addAttribute("tecnicos", tecnicos);
        return "AsignarTicket";
    }

    @GetMapping("/gestion-tipo-incidencias")
    public String mostrarGestionTipoIncidencias(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<CategoriaSolicitud> categorias = categoriaSolicitudService.findAllCategoriasSolicitud();
        List<Dispositivo> dispositivos = dispositivoService.traerDispositivos();
        Dispositivo dispositivo = new Dispositivo();
        model.addAttribute("dispositivos", dispositivos);
        model.addAttribute("dispositivo", dispositivo);
        model.addAttribute("usuario", usuario);
        model.addAttribute("categorias", categorias);
        return "GestionTipoIncidencias";
    }

    @PostMapping("/guardar-dispositivo")
    public String guardarDispositivo(@ModelAttribute("dispositivo") Dispositivo dispositivoRecibido) {
        dispositivoService.guardarDispositivo(dispositivoRecibido);
        return "redirect:/gestion-tipo-incidencias";
    }

    @PostMapping("/asignar-tecnico")
    public String asignarTecnico(
            @RequestParam("ticketId") Long ticketId,
            @RequestParam("tecnicoId") Long tecnicoId) {

        // Obtener el ticket
        Ticket ticket = ticketService.obtenerTicketPorId(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        // Asignar el técnico
        ticket.setTecnico_id(tecnicoId);

        // Guardar los cambios
        ticketService.guardarTicket(ticket);

        return "redirect:/mostrar-lista-asignar-tickets";
    }



@GetMapping("/generar-pdf/{ticketId}")
public ResponseEntity<byte[]> generarPdfTicket(@PathVariable Long ticketId) {
    try {
        Ticket ticket = ticketService.obtenerTicketPorId(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        
        byte[] pdfBytes = pdfGeneratorService.generatePdfFromTicket(ticket);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "reporte-ticket-" + ticketId + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    } catch (IOException e) {
        throw new RuntimeException("Error al generar el PDF", e);
    }
}

}