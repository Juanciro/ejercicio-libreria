package com.biblio.bibliotecalocal.Controladores;

import com.biblio.bibliotecalocal.Entidades.Autor;
import com.biblio.bibliotecalocal.Servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorController {
    
    @Autowired
    AutorServicio as;
    
    @GetMapping("/registro")
    public String registro(){
        
        return "carga-autor";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre){
        
        try {
            
            as.save(nombre);
            
            modelo.put("exito", "Registro exitoso");
            return "carga-autor";
            
        } catch (Exception e) {
            
            modelo.put("error", "El nombre no es correcto");
            return "carga-autor";
        }
        
    }
    
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", as.buscarPorId(id));
        return "edit-autor";
    }
    
    @PostMapping("/modificar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre){
        
        
        try {
            if(nombre.trim().isEmpty()){
                throw new Exception("no se pudo editar");
            }
            as.edit(id, nombre);
            modelo.put("exito", "Modificacion exitosa");
            modelo.put("autor", as.buscarPorId(id));
            return "edit-autor";
        } catch (Exception e) {
            modelo.put("error", "El nombre no es correcto");
            modelo.put("autor", as.buscarPorId(id));
            return "edit-autor";
        }
        
    }
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
            List<Autor> autores = as.getAll();
            modelo.put("autores", autores);
            return "lista-autor";
    }
    
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        try {
            as.baja(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        try {
            as.alta(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
}
