package com.tiendaOctavius.service;

import com.tiendaOctavius.domain.Usuario;
import com.tiendaOctavius.repository.UsuarioRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        // Asignamos el rol que tenga el usuario
        var roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(usuario.getRol()));

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isActivo(), 
                true, true, true, roles);
    }
}
