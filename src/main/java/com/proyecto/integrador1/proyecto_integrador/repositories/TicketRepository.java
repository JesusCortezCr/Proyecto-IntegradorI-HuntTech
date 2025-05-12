package com.proyecto.integrador1.proyecto_integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
