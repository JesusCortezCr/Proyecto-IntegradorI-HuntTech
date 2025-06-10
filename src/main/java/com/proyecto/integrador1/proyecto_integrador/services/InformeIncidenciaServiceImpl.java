package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.integrador1.proyecto_integrador.entities.InformeIncidencia;
import com.proyecto.integrador1.proyecto_integrador.repositories.InformeIncidenciaRepository;

@Service
public class InformeIncidenciaServiceImpl implements InformeIncidenciaService {

    @Autowired
    private InformeIncidenciaRepository informeIncidenciaRepository;

    @Override
    public InformeIncidencia guardar(InformeIncidencia informeIncidencia) {
        InformeIncidencia informeIncidenciaF = informeIncidenciaRepository.save(informeIncidencia);
        return informeIncidenciaF;
    }

    @Override
    public InformeIncidencia findById(Long id) {
        Optional<InformeIncidencia> informeIncidencia = informeIncidenciaRepository.findById(id);
        return informeIncidencia.get();
    }

    @Override
    public boolean tieneInforme(Long ticketId) {
        return informeIncidenciaRepository.existsByTicketId(ticketId);
    }

    @Override
    public List<InformeIncidencia> obtenerInformesPorUniversidad(String nombreUniversidad) {
         return informeIncidenciaRepository.obtenerTodosInformesIncidenciasPorUniversidada(nombreUniversidad);
    }

}
