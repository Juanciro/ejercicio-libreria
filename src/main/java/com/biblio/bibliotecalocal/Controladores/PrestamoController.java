package com.biblio.bibliotecalocal.Controladores;

import com.biblio.bibliotecalocal.Servicios.PrestamoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {
    
    @Autowired
    PrestamoServicio ps;
    
    @GetMapping("/registro")
    public String registro(){
        return "carga-prestamo";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam Long dni, @RequestParam Long isbn){
        
        try {
            ps.save(dni, isbn);
            modelo.put("exito", "Prestamo creado");
            return "carga-prestamo";
        } catch (Exception e) {
            modelo.put("error", "Algun dato no es valido. Es posible que no esten cargados el cleinte o el libro en la base");
            return "carga-prestamo";
        }
    }
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        try {
            modelo.put("prestamos", ps.getAll());
            return "lista-prestamo";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            ps.baja(id);
            return "redirect:/prestamo/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id){
        
        modelo.put("prestamo", ps.buscarPorId(id));
        return "edit-prestamo";
    }
    
    @PostMapping("modificar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id, @RequestParam Long dni, @RequestParam Long isbn, @RequestParam Boolean abierto){
        
        try {
            ps.edit(id, dni, isbn, abierto);
            modelo.put("prestamo", ps.buscarPorId(id));
            modelo.put("exito", "Se modifico con exito");
            return "edit-prestamo";
        } catch (Exception e) {
            modelo.put("prestamo", ps.buscarPorId(id));
            modelo.put("error","Algun dato no valido");
            return "edit-prestamo";
        }
    }
    
    
    
}
