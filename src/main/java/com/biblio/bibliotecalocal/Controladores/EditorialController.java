package com.biblio.bibliotecalocal.Controladores;

import com.biblio.bibliotecalocal.Entidades.Editorial;
import com.biblio.bibliotecalocal.Servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {
    
    @Autowired
    EditorialServicio es;
    
    @GetMapping("/registro")
    public String registro(){
        return "carga-editorial";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre){
        try {
            
            es.save(nombre);
            modelo.put("exito", "Registro exitoso");
            return "carga-editorial";
        } catch (Exception e) {
            modelo.put("error", "El nombre no es correcto");
            return "carga-editorial";
        }
    }
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Editorial> editoriales = es.getAll();
        
        modelo.put("editoriales", editoriales);
        
        return "lista-editorial";
    }
    
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            es.baja(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        
        try {
            es.alta(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", es.buscarPorId(id));
        return "edit-editorial";
    }
    
    @PostMapping("/modificar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre){
        try {
            
            es.edit(id, nombre);
            modelo.put("editorial", es.buscarPorId(id));
            modelo.put("exito","Modificacion exitosa");
            return "edit-editorial";
        } catch (Exception e) {
            modelo.put("editorial", es.buscarPorId(id));
            modelo.put("error","Nombre invalido");
            return "edit-editorial";
        }
    }
    
    
}
