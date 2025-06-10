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
@Table(name = "DISPOSITIVOS")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISPOSITIVO")
    private Long id;
    
    @Column(name = "NOMBRE_DISPOSITIVO", nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    
    // Constructores, getters y setters
}