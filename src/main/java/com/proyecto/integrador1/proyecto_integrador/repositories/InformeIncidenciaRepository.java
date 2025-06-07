package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.integrador1.proyecto_integrador.entities.InformeIncidencia;

public interface InformeIncidenciaRepository extends JpaRepository<InformeIncidencia,Long> {

    boolean existsByTicketId(Long ticket);
    
    @Query("select i from InformeIncidencia i where i.usuario.empresa.nombreEmpresa=:nombreUniversidad")
    List<InformeIncidencia> obtenerTodosInformesIncidenciasPorUniversidada(@Param("nombreUniversidad") String nombreUniversidad );
}
