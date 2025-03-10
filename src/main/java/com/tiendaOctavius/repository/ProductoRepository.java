
package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long>   {
    
}
