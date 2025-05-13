package com.proyecto.integrador1.proyecto_integrador.services;

import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;

public interface UsuarioService {

    void guardarUsuario(Usuario usuario, String codigoEmpresa);
    boolean usuarioExiste(String email);
    Usuario obtenerUsuarioCompletoPorEmail(String email);
    
}
