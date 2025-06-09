package com.proyecto.integrador1.proyecto_integrador.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    private Long id;

    @Column(name = "FECHA_HORA_SOLICITUD_TICKET" , nullable = false)
    private LocalDateTime fechaHoraSolicitudTicket;

    @Column(name = "DESCRIPCION_TICKET", nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_ID",  nullable = false)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DISPOSITIVO", nullable = false)
    private Dispositivo dispositivo;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORIA_SOLICITUD_ID", nullable = false)
    private CategoriaSolicitud categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_PRIORIDAD_ID", nullable = false)
    private TipoPrioridad prioridad;

    @OneToMany(mappedBy = "ticket",fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    private List<InformeIncidencia> informesIncidencia;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL,orphanRemoval = true)
    private Reporte reporte;

    
}
