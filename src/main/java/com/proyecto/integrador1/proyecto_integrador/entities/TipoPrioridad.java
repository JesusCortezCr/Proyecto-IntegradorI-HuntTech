package com.proyecto.integrador1.proyecto_integrador.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "TIPO_PRIORIDAD")
public class TipoPrioridad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_PRIORIDAD")
    private Long id;
    
    @Column(name = "NOMBRE_TIPO_PRIORIDAD", nullable = false, unique = true)
    private String nombre;
    
    @OneToMany(mappedBy = "prioridad", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    
    // Constructores, getters y setters
}