package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Mantén solo estos métodos:
    boolean existsByEmail(String email);

    boolean existsByDniUsuario(String dniUsuario);

    // Este es el único método que necesitas para obtener el usuario con relaciones
    @EntityGraph(attributePaths = { "empresa", "rol" })
    Optional<Usuario> findByEmail(String email);

    // Elimina el método findByEmailWithEmpresaAndRol() porque es redundante
}
