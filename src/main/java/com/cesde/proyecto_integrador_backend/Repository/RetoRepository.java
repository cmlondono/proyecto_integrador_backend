package com.cesde.proyecto_integrador_backend.Repository;

import java.util.List;
import java.util.UUID;
import com.cesde.proyecto_integrador_backend.model.Reto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetoRepository  extends JpaRepository<Reto, UUID>{
    boolean existsByEmpresaId(UUID empresaId);
    boolean existsByCategoriaId(UUID categoriaId);
    List<Reto> findByEmpresaId(UUID empresaId);
}
