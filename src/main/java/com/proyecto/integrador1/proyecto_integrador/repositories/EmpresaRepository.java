package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador1.proyecto_integrador.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa,Long>{
    Optional<Empresa> findByCodigoEmpresa(String codigoEmpresa);
    

}
