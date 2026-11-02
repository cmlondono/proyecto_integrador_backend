package com.cesde.proyecto_integrador_backend.model;

import com.cesde.proyecto_integrador_backend.enums.EstadoRegistro;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "REGISTRO",
// Creo la restricción UNIQUE en la BDD que respalda la regla de negocio del 409 (no duplicar usuario+reto)
uniqueConstraints = @UniqueConstraint (columnNames ={"id_usuario", "id_reto"}))

public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @NotBlank(message = "La observación es obligatoria")
    @Column(name = "observacion", nullable = false)
    private String observacion;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoRegistro estado;

    // Cuándo se aceptó (null si todavía no se acepta)
    @Column(name = "fecha_aceptacion")
    private LocalDateTime fechaAceptacion;

    // Cuándo se finalizó (null si todavía no se finaliza)
    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    // Relación: muchos registros pertenecen a un usuario
    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Relación: muchos registros pertenecen a un reto
    @NotNull(message = "El reto es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_reto", nullable = false)
    private Reto reto;

    // Quién aceptó (null si todavía no se acepta)
    @ManyToOne
    @JoinColumn(name = "aceptado_por", referencedColumnName = "id")
    private Usuario aceptadoPor;

    // Quién finalizó (null si todavía no se finaliza)
    @ManyToOne
    @JoinColumn(name = "finalizado_por", referencedColumnName = "id")
    private Usuario finalizadoPor;

    // Recibido al aceptar/finalizar: quién hace la acción. No se guarda tal cual,
    // el service lo copia a aceptadoPor o finalizadoPor según el nuevo estado.
    @Transient
    private Usuario gestionadoPor;


    public Registro() {
    }

    public Registro(UUID id, LocalDateTime fechaRegistro, String observacion, EstadoRegistro estado, Usuario usuario, Reto reto) {
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.observacion = observacion;
        this.estado = estado;
        this.usuario = usuario;
        this.reto = reto;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getObservacion() {
        return observacion;
    }

    public EstadoRegistro getEstado() {
        return estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Reto getReto() {
        return reto;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setEstado(EstadoRegistro estado) {
        this.estado = estado;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setReto(Reto reto) {
        this.reto = reto;
    }

    public LocalDateTime getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(LocalDateTime fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public LocalDateTime getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Usuario getAceptadoPor() {
        return aceptadoPor;
    }

    public void setAceptadoPor(Usuario aceptadoPor) {
        this.aceptadoPor = aceptadoPor;
    }

    public Usuario getFinalizadoPor() {
        return finalizadoPor;
    }

    public void setFinalizadoPor(Usuario finalizadoPor) {
        this.finalizadoPor = finalizadoPor;
    }

    public Usuario getGestionadoPor() {
        return gestionadoPor;
    }

    public void setGestionadoPor(Usuario gestionadoPor) {
        this.gestionadoPor = gestionadoPor;
    }
}


