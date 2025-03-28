package com.tiendaOctavius.controller;

import com.tiendaOctavius.domain.Usuario;
import com.tiendaOctavius.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    // Mostrar el formulario de registro
    @GetMapping
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro/registro"; // Nombre de la vista (registro.html)
    }

    // Procesar el formulario de registro
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        // Guardamos el usuario (se encripta la contraseña)
        usuarioService.save(usuario);
        return "/login";
    }
}
