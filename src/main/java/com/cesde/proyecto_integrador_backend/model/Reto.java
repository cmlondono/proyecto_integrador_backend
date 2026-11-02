package com.cesde.proyecto_integrador_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity 
@Table(name = "RETOS")
public class Reto {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatorio")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de finalización es obligatoria")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    @NotBlank (message = "El estado es obligatorio")
    private String estado;

    //id empresa
    @NotNull(message = "La empresa es obligatoria")
    @ManyToOne
    @JoinColumn (name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    // id categoria
    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne
    @JoinColumn (name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;

    // id prioridad
    //@JoinColumn (name = "prioridad_id", referencedColumnName = "id")
    //private Prioridad prioridad;

    // constructor vacío
    public Reto() {

    }

    public Reto(UUID id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, String estado, Empresa empresa, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.empresa = empresa;
        this.categoria = categoria;
        // prioirdad
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
