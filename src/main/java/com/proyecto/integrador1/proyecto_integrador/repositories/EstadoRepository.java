package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador1.proyecto_integrador.entities.Estado;

public interface EstadoRepository extends JpaRepository<Estado,Long>{

    Optional<Estado> findByNombre(String nombreEstado);

}
