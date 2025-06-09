package com.proyecto.integrador1.proyecto_integrador.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORTE_ID")
    private Long id;

    @Column(name = "FECHA_HORA_GENERADO", nullable = false)
    private LocalDateTime fechaHoraGenerado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID", nullable = false)
    private Ticket ticket;

}
