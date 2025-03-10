package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Para inicio de sesión, necesitamos buscar un usuario por username
    Usuario findByUsername(String username);
}
