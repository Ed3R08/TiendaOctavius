package com.tiendaOctavius.controller;

import com.tiendaOctavius.domain.Producto;
import com.tiendaOctavius.service.CategoriaService;
import com.tiendaOctavius.service.ProductoService;
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
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        // Obtener lista de productos (false = no incluir inactivos, o lo que signifique en tu servicio)
        var lista = productoService.getProductos(false);
        model.addAttribute("productos", lista);
        model.addAttribute("totalProductos", lista.size());
        
        // Obtener lista de categorías para usarlas en el select (al agregar producto)
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        // Retornar la vista de listado de productos
        return "/producto/listado";
    }
    
    @GetMapping("/eliminar/{idProducto}")
    public String eliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }
 
    @GetMapping("/modificar/{idProducto}")
    public String modificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        return "/producto/modifica";
    }
    
    @PostMapping("/guardar")
    public String guardar(Producto producto,
                          @RequestParam("imagenFile") MultipartFile imagenFile) {
        // Verificar si se subió una imagen
        if (!imagenFile.isEmpty()) {
            // Guardar primero el producto para generar su ID
            productoService.save(producto);
            // Cargar la imagen en Firebase, usando ID del producto
            String ruta = firebaseStorageService.cargaImagen(imagenFile, "producto", producto.getIdProducto());
            producto.setRutaImagen(ruta);
        }
        
        // Guardar el producto (ya con la ruta de imagen si aplica)
        productoService.save(producto);
        
        return "redirect:/producto/listado";
    }
}
