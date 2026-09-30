package com.cesde.proyecto_integrador_backend.controller;

import com.cesde.proyecto_integrador_backend.model.Categoria;
import com.cesde.proyecto_integrador_backend.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaContoller {

    @Autowired
    CategoriaService categoriaService;

    @PostMapping
    public Categoria guardarCategoria(@Valid @RequestBody Categoria categoria){
        return categoriaService.guardarCategoria(categoria);
    }

    @GetMapping("/{id}")
    public Categoria buscarCategoriaPorId(@PathVariable UUID id){
        return categoriaService.buscarCategoriaPorId(id);
    }

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias(){
        return categoriaService.listarCategorias();

    }

    @DeleteMapping("/{id}")
    public String borrarCategoriaPorId(@PathVariable UUID id){
        categoriaService.borrarCategoria(id);
        return "Se ha borrado con Exito";
    }

    @PutMapping("/actualizar/{id}")
    public Categoria actualizarCategoria(@PathVariable UUID id, @Valid @RequestBody Categoria categoria){
        return categoriaService.actualizarCategoria(id, categoria);
    }
}
