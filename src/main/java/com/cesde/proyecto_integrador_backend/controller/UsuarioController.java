package com.cesde.proyecto_integrador_backend.controller;

import com.cesde.proyecto_integrador_backend.dto.LoginRequest;
import com.cesde.proyecto_integrador_backend.model.Usuario;
import com.cesde.proyecto_integrador_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/registro")
    public Usuario registrar(@Valid @RequestBody Usuario usuario){
        return usuarioService.registrar(usuario);
    }

    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @PostMapping("/login")
    public Usuario iniciarSesion(@Valid @RequestBody LoginRequest datos){
        return usuarioService.iniciarSesion(datos);
    }

    @DeleteMapping("/{id}")
    public String borrarUsuario(@PathVariable UUID id){
        return usuarioService.borrarUsuario(id);
    }
}
