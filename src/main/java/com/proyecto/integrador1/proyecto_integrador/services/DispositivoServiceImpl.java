package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.integrador1.proyecto_integrador.entities.Dispositivo;
import com.proyecto.integrador1.proyecto_integrador.repositories.DispositivoRepository;

@Service
public class DispositivoServiceImpl implements DispositivoService{

    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Override
    public void guardarDispositivo(Dispositivo dispositivo) {
        dispositivoRepository.save(dispositivo);
    }
    @Override
    public List<Dispositivo> traerDispositivos() {
        return dispositivoRepository.findAll();
    }

}
