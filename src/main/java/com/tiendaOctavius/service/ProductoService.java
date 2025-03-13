package com.tiendaOctavius.service;

import com.tiendaOctavius.domain.Producto;
import com.tiendaOctavius.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        if (activos) {
            return productoRepository.findByActivo(true);
        } else {
            return productoRepository.findAll();
        }
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarProductos(String keyword) {
        return productoRepository.findByNombreContainingIgnoreCase(keyword);
    }

    // Se escriben los métodos de un CRUD (Create, Read, Update, Delete)
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoRepository.findById(producto.getIdProducto())
                .orElse(null);
    }

    @Transactional
    public void delete(Producto producto) {
        // Elimina el registro que contiene el ID pasado en producto.getIdProducto()
        productoRepository.delete(producto);
    }

    @Transactional
    public void save(Producto producto) {
        // Si producto.idProducto está vacío... se inserta un registro
        // Si producto.idProducto tiene algo... se modifica el registro
        productoRepository.save(producto);
    }
}
