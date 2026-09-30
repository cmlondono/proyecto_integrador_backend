package com.cesde.proyecto_integrador_backend.model;

import jakarta.persistence.*;

import com.cesde.proyecto_integrador_backend.enums.AREAS;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "CATEGORIA")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotBlank(message = "El área responsable es obligatoria")
    @Column(name = "area_responsable", nullable = false)
    @Enumerated(EnumType.STRING)
    private AREAS areaResponsable;

    public Categoria() {
    }

    public Categoria(UUID id, String nombre, String descripcion, AREAS areaResponsable) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.areaResponsable = areaResponsable;
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

    public AREAS getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(AREAS areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

}