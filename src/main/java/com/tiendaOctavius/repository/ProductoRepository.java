package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Método para buscar productos por nombre (sin distinguir entre mayúsculas y minúsculas)
    List<Producto> findByNombreContainingIgnoreCase(String keyword);

    // Método para obtener productos activos
    List<Producto> findByActivo(boolean activo);
}
