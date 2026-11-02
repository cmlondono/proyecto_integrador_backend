package com.cesde.proyecto_integrador_backend.service;

import com.cesde.proyecto_integrador_backend.Repository.EmpresaRepository;
import com.cesde.proyecto_integrador_backend.Repository.RetoRepository;
import com.cesde.proyecto_integrador_backend.Repository.UsuarioRepository;
import com.cesde.proyecto_integrador_backend.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    RetoRepository retoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public Empresa guardarEmpresa (Empresa empresa){
       return empresaRepository.save(empresa);
    }

    public Empresa buscarEmpresaPorId (UUID id){
        return empresaRepository.findById(id).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    public String borrarEmpresa (UUID id){
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa no encontrada");
        }
        if (retoRepository.existsByEmpresaId(id)) {
            throw new RuntimeException("La empresa tiene retos asociados");
        }
        if (usuarioRepository.existsByEmpresaId(id)) {
            throw new RuntimeException("La empresa tiene usuarios asociados");
        }

        empresaRepository.deleteById(id);
        return "Se ha borrado con exito";
    }

    public List<Empresa> listarEmpresas (){
        return empresaRepository.findAll();
    }

    public Empresa actualizarEmpresa(UUID id, Empresa empresa) {

        Empresa emp = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        emp.setNombre(empresa.getNombre());
        emp.setNit(empresa.getNit());
        emp.setSector(empresa.getSector());
        emp.setContacto(empresa.getContacto());
        emp.setCorreo(empresa.getCorreo());
        emp.setTelefono(empresa.getTelefono());
        emp.setActiva(empresa.getActiva());

        return empresaRepository.save(emp);
    }



}
