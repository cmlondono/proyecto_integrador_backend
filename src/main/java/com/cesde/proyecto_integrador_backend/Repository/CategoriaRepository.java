package com.cesde.proyecto_integrador_backend.Repository;

import com.cesde.proyecto_integrador_backend.enums.AREAS;
import com.cesde.proyecto_integrador_backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    List<Categoria> findByAreaResponsable(AREAS areaResponsable);
}
