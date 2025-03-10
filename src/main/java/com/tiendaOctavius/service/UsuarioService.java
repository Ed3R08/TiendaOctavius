package com.tiendaOctavius.service;

import com.tiendaOctavius.domain.Usuario;
import com.tiendaOctavius.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Spring Security inyectará un PasswordEncoder (BCrypt)
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Método para registrar/guardar un usuario
    public void save(Usuario usuario) {
        // Encriptamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }
    
    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
