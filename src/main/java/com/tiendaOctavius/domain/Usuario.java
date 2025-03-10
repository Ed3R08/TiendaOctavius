package com.tiendaOctavius.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(nullable = false, unique = true)
    private String username;  // Este será el correo o nombre de usuario
    
    @Column(nullable = false)
    private String password;  // Será almacenado de manera encriptada
    
    private String nombre;    // Nombre completo
    
    // Podrías guardar también un "rol" sencillo (p.e. ROLE_USER, ROLE_ADMIN) si gustas:
    private String rol = "ROLE_USER";

    private boolean activo = true;
}
