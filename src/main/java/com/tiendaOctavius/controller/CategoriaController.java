package com.tiendaOctavius.controller;

import com.tiendaOctavius.domain.Categoria;
import com.tiendaOctavius.service.CategoriaService;
import com.tiendaOctavius.service.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        // Lógica para obtener la lista de categorías
        var lista = categoriaService.getCategorias(false);
        model.addAttribute("categorias", lista);
        model.addAttribute("totalCategorias", lista.size());        
        return "/categoria/listado";
    }
    
    @GetMapping("/eliminar/{idCategoria}")
    public String eliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        return "redirect:/categoria/listado";
    }
 
    @GetMapping("/modificar/{idCategoria}")
    public String modificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }
    
    @PostMapping("/guardar")
    public String guardar(Categoria categoria,
                          @RequestParam("imagenFile") MultipartFile imagenFile) {
        // Si se envía una imagen, primero guardamos la categoría para obtener su ID
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            // Cargamos la imagen en Firebase, usando el ID de la categoría
            String ruta = firebaseStorageService.cargaImagen(imagenFile, 
                                                             "categoria",
                                                             categoria.getIdCategoria());
            categoria.setRutaImagen(ruta);
        }
        // Guardamos definitivamente la categoría (con ruta de imagen si aplica)
        categoriaService.save(categoria);
        
        return "redirect:/categoria/listado";
    }
}
