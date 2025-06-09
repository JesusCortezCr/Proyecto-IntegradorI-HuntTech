package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador1.proyecto_integrador.entities.Rol;

public interface RolRepository extends JpaRepository<Rol,Long> {

    Optional<Rol> findByNombre(String nombre);
}
