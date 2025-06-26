package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.List;

import com.proyecto.integrador1.proyecto_integrador.entities.Dispositivo;

public interface DispositivoService {

    void guardarDispositivo(Dispositivo dispositivo);
    List<Dispositivo> traerDispositivos();
}
