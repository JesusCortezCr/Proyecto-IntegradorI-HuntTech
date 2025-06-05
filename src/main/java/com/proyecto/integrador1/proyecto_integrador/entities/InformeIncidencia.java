package com.proyecto.integrador1.proyecto_integrador.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "INFORMES_INCIDENCIAS")
public class InformeIncidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INFORME_INCIDENCIA_ID")
    private Long id;

    @Column(name = "FECHA_HORA_INFORME", nullable = false)
    private LocalDateTime fechaHoraInforme;
    
    @Column(name = "DESCRIPCION_INFORME", nullable = false, length = 2000)
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "TICKET_ID", nullable = false)
    private Ticket ticket;

    private String titulo;

}
