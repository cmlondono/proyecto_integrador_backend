package com.cesde.proyecto_integrador_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.cesde.proyecto_integrador_backend.Repository.RegistroRepository;
import com.cesde.proyecto_integrador_backend.Repository.RetoRepository;
import com.cesde.proyecto_integrador_backend.model.Reto;

@Service 
public class RetoService {
    
    @Autowired 
    RetoRepository retoRepository;

    @Autowired
    RegistroRepository registroRepository;

    public Reto guardaReto (Reto reto){
        return retoRepository.save(reto);
    }

    public Reto buscarRetoPorId (UUID id){
        return retoRepository.findById(id).orElseThrow(() -> new RuntimeException("Reto no encontrado"));
    }

    public String borrarReto (UUID id){
        if (!retoRepository.existsById(id)) {
            throw new RuntimeException("Reto no encontrado");
        }
        if (registroRepository.existsByRetoId(id)) {
            throw new RuntimeException("El reto tiene registros asociados");
        }

        retoRepository.deleteById(id);
        return "Se ha borrado con exito";
    }

    // empresaId: si viene, solo trae los retos de esa empresa (cuentas tipo EMPRESA)
    public List<Reto> listarRetos (UUID empresaId){
        if (empresaId != null) {
            return retoRepository.findByEmpresaId(empresaId);
        }
        return retoRepository.findAll();
    }

    public Reto actualizaReto (UUID id, Reto reto){

        Reto rt = retoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reto no encontrado"));

        rt.setNombre(reto.getNombre());
        rt.setDescripcion(reto.getDescripcion());
        rt.setFechaInicio(reto.getFechaInicio());
        rt.setFechaFin(reto.getFechaFin());
        rt.setEstado(reto.getEstado());
        rt.setEmpresa(reto.getEmpresa());
        rt.setCategoria(reto.getCategoria());
        //prioridad

        return retoRepository.save(rt);
    }

}
