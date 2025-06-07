package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.integrador1.proyecto_integrador.entities.InformeIncidencia;
import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUsuario(Usuario usuario);

    @Query("select t from Ticket t where t.usuario.empresa.nombreEmpresa=:nombreUniversidad")
    List<Ticket> obtenerTodosTicketsPorUniversidad(@Param("nombreUniversidad") String nombreUniversidad);

    @Query("""
                SELECT t FROM Ticket t
                WHERE t.usuario.empresa.nombreEmpresa = :nombreUniversidad
                ORDER BY
                    CASE t.prioridad.nombre
                        WHEN 'Urgente' THEN 1
                        WHEN 'Alta' THEN 2
                        WHEN 'Normal' THEN 3
                        ELSE 4
                    END
            """)
    List<Ticket> obtenerTicketOrdenadosPorPrioridad(@Param("nombreUniversidad") String nombreUniversidad);

    
}
