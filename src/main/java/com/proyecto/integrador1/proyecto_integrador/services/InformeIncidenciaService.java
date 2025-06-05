package com.proyecto.integrador1.proyecto_integrador.services;

import com.proyecto.integrador1.proyecto_integrador.entities.InformeIncidencia;

public interface InformeIncidenciaService {

    InformeIncidencia guardar(InformeIncidencia informeIncidencia);
    InformeIncidencia findById(Long id);
}
