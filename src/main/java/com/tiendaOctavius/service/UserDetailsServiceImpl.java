package com.tiendaOctavius.service;

import com.tiendaOctavius.domain.Usuario;
import com.tiendaOctavius.repository.UsuarioRepository;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Intentando autenticar usuario: " + username);
        
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            logger.error("Usuario no encontrado: " + username);
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        
        logger.debug("Usuario encontrado: " + usuario.getUsername() + ", Rol: " + usuario.getRol());
        
        var roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isActivo(),
                true, true, true, roles);
    }
}
