package com.proyecto.integrador1.proyecto_integrador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.integrador1.proyecto_integrador.entities.Empresa;
import com.proyecto.integrador1.proyecto_integrador.entities.Rol;
import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;
import com.proyecto.integrador1.proyecto_integrador.repositories.EmpresaRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.RolRepository;
import com.proyecto.integrador1.proyecto_integrador.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void guardarUsuario(Usuario usuario, String codigoEmpresa) {
        // Validar email único
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Validar DNI único
        if (usuarioRepository.existsByDniUsuario(usuario.getDniUsuario())) {
            throw new RuntimeException("El DNI ya está registrado");
        }

        // Codificar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar rol CLIENTE por defecto
        Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));
        usuario.setRol(rolCliente);

        // Buscar y asignar empresa
        Empresa empresa = empresaRepository.findByCodigoEmpresa(codigoEmpresa)
                .orElseThrow(() -> new RuntimeException("Código de empresa no válido"));
        usuario.setEmpresa(empresa);

        usuarioRepository.save(usuario);
    }

    @Override
    public boolean usuarioExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioCompletoPorEmail(String email) {
        // Usa el método del repositorio que carga empresa y rol
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Override
    public void guardarTecnico(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listaTecnicos(Long empresaId) {
        return usuarioRepository.listaTecnicos(empresaId);
    }

    @Override
    public void eliminarTecnicoPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

}
