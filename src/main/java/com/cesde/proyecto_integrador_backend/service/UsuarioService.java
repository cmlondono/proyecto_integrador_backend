package com.cesde.proyecto_integrador_backend.service;

import com.cesde.proyecto_integrador_backend.Repository.RegistroRepository;
import com.cesde.proyecto_integrador_backend.Repository.UsuarioRepository;
import com.cesde.proyecto_integrador_backend.dto.LoginRequest;
import com.cesde.proyecto_integrador_backend.enums.ROLES;
import com.cesde.proyecto_integrador_backend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RegistroRepository registroRepository;

    // Cifra las contraseñas para no guardarlas en texto plano
    BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder();

    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }
        if (usuario.getRol() == ROLES.EMPRESA && (usuario.getEmpresa() == null || usuario.getEmpresa().getId() == null)) {
            throw new RuntimeException("Debe seleccionar la empresa a la que pertenece");
        }
        usuario.setContrasena(cifrador.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public String borrarUsuario(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        if (registroRepository.existsByUsuarioId(id)) {
            throw new RuntimeException("El usuario tiene postulaciones asociadas");
        }

        usuarioRepository.deleteById(id);
        return "Se ha borrado con exito";
    }

    public Usuario iniciarSesion(LoginRequest datos) {
        Usuario usuario = usuarioRepository.findByCorreo(datos.correo())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

        if (!cifrador.matches(datos.contrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }
        return usuario;
    }
}
