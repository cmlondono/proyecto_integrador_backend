package com.cesde.proyecto_integrador_backend.service;

import com.cesde.proyecto_integrador_backend.Repository.RegistroRepository;
import com.cesde.proyecto_integrador_backend.enums.EstadoRegistro;
import com.cesde.proyecto_integrador_backend.model.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RegistroService {

    @Autowired
    RegistroRepository registroRepository;


public Registro guardarRegistro(Registro registro){
    if (registroRepository.existsByUsuarioIdAndRetoId(registro.getUsuario().getId(), registro.getReto().getId())){
        throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya esta inscrito en este reto");
    }
    return registroRepository.save(registro);
}

public Registro buscarRegistroPorId(UUID id){
    return registroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
}

public String borrarRegistro(UUID id){
    Registro registro = registroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

    // Solo se puede cancelar/rechazar mientras está inscrito; ya aceptado o finalizado queda como historial
    if (registro.getEstado() != EstadoRegistro.INSCRITO) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se puede cancelar una postulación mientras está inscrita");
    }

    registroRepository.deleteById(id);
    return "Registro eliminado";
}

public List<Registro> listarRegistros(){
    return registroRepository.findAll();
}

public Registro actualizarRegistro(UUID id, Registro registro){
    Registro reg = registroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

    reg.setFechaRegistro(registro.getFechaRegistro());
    reg.setObservacion(registro.getObservacion());
    // Cada transición queda guardada por separado: aceptar no se borra al finalizar
    if (reg.getEstado() != registro.getEstado()) {
        if (registro.getEstado() == EstadoRegistro.EN_PROCESO) {
            reg.setFechaAceptacion(LocalDateTime.now());
            reg.setAceptadoPor(registro.getGestionadoPor());
        } else if (registro.getEstado() == EstadoRegistro.FINALIZADO) {
            reg.setFechaFinalizacion(LocalDateTime.now());
            reg.setFinalizadoPor(registro.getGestionadoPor());
        }
    }
    reg.setEstado(registro.getEstado());
    reg.setUsuario(registro.getUsuario());
    reg.setReto(registro.getReto());

    return registroRepository.save(reg);
}


}