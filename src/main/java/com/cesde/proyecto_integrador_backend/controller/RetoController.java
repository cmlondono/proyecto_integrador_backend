package com.cesde.proyecto_integrador_backend.controller;

import com.cesde.proyecto_integrador_backend.model.Reto;
import com.cesde.proyecto_integrador_backend.service.RetoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/reto")
public class RetoController {

    @Autowired
    RetoService retoService;

    @PostMapping
    public ResponseEntity<Reto> crear(@Valid @RequestBody Reto reto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retoService.guardaReto(reto));
    }

    @GetMapping
    public ResponseEntity<List<Reto>> listar(@RequestParam(required = false) UUID empresaId) {
        return ResponseEntity.ok(retoService.listarRetos(empresaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(retoService.buscarRetoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reto> actualizar(@PathVariable UUID id, @Valid @RequestBody Reto reto) {
        return ResponseEntity.ok(retoService.actualizaReto(id, reto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrar(@PathVariable UUID id) {
        return ResponseEntity.ok(retoService.borrarReto(id));
    }
}