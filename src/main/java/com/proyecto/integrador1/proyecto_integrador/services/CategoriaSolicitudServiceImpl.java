package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.integrador1.proyecto_integrador.entities.CategoriaSolicitud;
import com.proyecto.integrador1.proyecto_integrador.repositories.CategoriaSolicitudRepository;

@Service
public class CategoriaSolicitudServiceImpl implements CategoriaSolicitudService{

    @Autowired
    private CategoriaSolicitudRepository categoriaSolicitudRepository;

    @Override
    public List<CategoriaSolicitud> findAllCategoriasSolicitud() {
        return categoriaSolicitudRepository.findAll();
    }

}
