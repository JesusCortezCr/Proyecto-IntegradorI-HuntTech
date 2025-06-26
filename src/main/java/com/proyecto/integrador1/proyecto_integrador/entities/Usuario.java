package com.proyecto.integrador1.proyecto_integrador.entities;

import java.util.ArrayList;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUARIO_ID")
    private Long id;
    @Column(name="NOMBRE_USUARIO", nullable = false)
    private String nombre;
    @Column(name="email_usuario", unique = true , nullable = false)
    private String email;
    @Column(name="password_usuario",nullable = false)
    private String password;
    @Column(nullable = false)
    private String apellido;
    @Column(name="DNI",nullable = false, unique = true)
    private String dniUsuario;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ID_ROL")
    private Rol rol;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;

    @OneToMany(mappedBy = "usuario" , cascade =CascadeType.ALL , orphanRemoval = true)
    private List<Ticket> tickets=new ArrayList<>();
    
    
}
