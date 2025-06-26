package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.integrador1.proyecto_integrador.entities.Rol;
import com.proyecto.integrador1.proyecto_integrador.repositories.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        Optional<Rol> rolOptional = rolRepository.findByNombre(nombre);
        return rolOptional;
    }

}
