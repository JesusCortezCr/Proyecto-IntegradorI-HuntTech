package com.proyecto.integrador1.proyecto_integrador.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Mantén solo estos métodos:
    boolean existsByEmail(String email);

    boolean existsByDniUsuario(String dniUsuario);

    // Este es el único método que necesitas para obtener el usuario con relaciones
    @EntityGraph(attributePaths = { "empresa", "rol" })
    Optional<Usuario> findByEmail(String email);

    //metodo para traer la lista de los tecnicos
    @Query("select t from Usuario t where t.rol.id=2 and t.empresa.id=:empresaId ")
    List<Usuario> listaTecnicos(@Param("empresaId")Long empresaId );

    //metodo para crear lista de tecnicos disponibles
    @Query("select t from Usuario t where t.rol.id=2 and t.empresa.id=:empresaId and t.cantidad_tickets<5")
    List<Usuario> listaTecnicosDisponibles(@Param("empresaId") Long empresaId);

    
}
