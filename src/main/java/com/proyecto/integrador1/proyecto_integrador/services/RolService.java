package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.proyecto.integrador1.proyecto_integrador.entities.Rol;

public interface RolService {
    Optional<Rol> findByNombre(@Param("nombre") String nombre);
}
