package com.cesde.proyecto_integrador_backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cesde.proyecto_integrador_backend.Repository.CategoriaRepository;

import com.cesde.proyecto_integrador_backend.model.Categoria;

@Service
public class CategoriaService {

    @Autowired 
    CategoriaRepository categoriaRepository;

    public Categoria guardarCategoria (Categoria categoria){
        return categoriaRepository.save(categoria);
    }
    public Categoria buscarCategoriaPorId (UUID id){
        return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }
    public String borrarCategoria (UUID id){
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }

        categoriaRepository.deleteById(id);
        return "Se ha borrado con exito";
    }
    public List<Categoria> listarCategorias (){
        return categoriaRepository.findAll();
    }
    public Categoria actualizarCategoria(UUID id, Categoria categoria) {

        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        cat.setNombre(categoria.getNombre());
        cat.setDescripcion(categoria.getDescripcion());
        cat.setAreaResponsable(categoria.getAreaResponsable());

        return categoriaRepository.save(cat);
    }

}
