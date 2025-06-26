package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.integrador1.proyecto_integrador.entities.Rol;

public interface RolRepository extends JpaRepository<Rol,Long> {

    @Query("select r from Rol r where r.nombre=:nombre")
    Optional<Rol> findByNombre(@Param("nombre") String nombre);

}
