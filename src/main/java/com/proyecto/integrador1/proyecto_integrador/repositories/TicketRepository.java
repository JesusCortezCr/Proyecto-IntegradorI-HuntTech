package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findByUsuario(Usuario usuario);
}
