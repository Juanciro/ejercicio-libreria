package com.biblio.bibliotecalocal.Controladores;

import com.biblio.bibliotecalocal.Entidades.Libro;
import com.biblio.bibliotecalocal.Servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroController {
    
    @Autowired
    LibroServicio ls;
    
    @GetMapping("/registro")
    public String registro(){
        
        return "carga-libro";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam String nombreAutor, @RequestParam String nombreEditorial, @RequestParam String image){
        
        try {
            ls.save(isbn, titulo, anio, ejemplares, ejemplaresPrestados, nombreAutor, nombreEditorial, image);
            modelo.put("exito", "Registro exitoso");
            return "carga-libro";
        } catch (Exception e) {
            modelo.put("error", "Falta alguno de los datos");
            return "carga-libro";
        }
        
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Libro> libros = ls.getAll();
        modelo.put("libros", libros);
        return "lista-libro";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        try {
            ls.baja(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        try {
            ls.alta(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id){
        modelo.put("libro", ls.buscarPorId(id));
        return "edit-libro";
    }
    
    @PostMapping("/modificar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id, @RequestParam Long isbn, @RequestParam String nombre, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam String nombreAutor, @RequestParam String nombreEditorial, @RequestParam String image){
        try {
            
            ls.edit(id, isbn, nombre, anio, ejemplares, ejemplaresPrestados, nombreAutor, nombreEditorial, image);
            modelo.put("libro", ls.buscarPorId(id));
            modelo.put("exito", "Modificacion exitosa");
            return "edit-libro";
            
        } catch (Exception e) {
            modelo.put("libro", ls.buscarPorId(id));
            modelo.put("error", "Hubo un error. Verifique que el autor y la editorial esten cargados en la base de datos");
            return "edit-libro";
        }
    }
    
    @GetMapping("/buscar-titulo")
    public String buscarTit(ModelMap modelo){
        return "buscar-por-titulo";
    }
    
    @PostMapping("/buscar-titulo")
    public String mostrarTit(ModelMap modelo, @RequestParam String tituloLibro){
        try {
            List<Libro> libros = ls.buscarPorTitulo(tituloLibro);
            modelo.put("libros", libros);
            return "redirect:/libro/buscar-por-titulo";
        } catch (Exception e) {
            modelo.put("libros",ls.buscarPorId(tituloLibro));
            modelo.put("error", "no hay resultados para la busqueda");
            return "redirect:/libro/buscar-por-titulo";
        }
    }
    
    
}
