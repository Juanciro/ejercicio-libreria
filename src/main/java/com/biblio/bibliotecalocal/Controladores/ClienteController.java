package com.biblio.bibliotecalocal.Controladores;

import com.biblio.bibliotecalocal.Entidades.Cliente;
import com.biblio.bibliotecalocal.Servicios.ClienteServicio;
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
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    ClienteServicio cs;
    
    @GetMapping("/registro")
    public String registro(){
        return "carga-cliente";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam Long documento){
        try {
            
            cs.save(documento, nombre, apellido, telefono);
            modelo.put("exito", "Registro exitoso");
            return "carga-cliente";
        } catch (Exception e) {
            modelo.put("error", "El nombre no es correcto");
            return "carga-cliente";
        }
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Cliente> clientes = cs.getAll();
        
        modelo.put("clientes", clientes);
        
        return "lista-cliente";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            cs.baja(id);
            return "redirect:/cliente/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        
        try {
            cs.alta(id);
            return "redirect:/cliente/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("cliente", cs.buscarPorId(id));
        return "edit-cliente";
    }
    
    @PostMapping("/modificar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam Long documento){
        try {
            
            cs.edit(id, documento, nombre, apellido, telefono);
            modelo.put("cliente", cs.buscarPorId(id));
            modelo.put("exito","Modificacion exitosa");
            return "edit-cliente";
        } catch (Exception e) {
            modelo.put("editorial", cs.buscarPorId(id));
            modelo.put("error","Algun dato no valido");
            return "edit-cliente";
        }
    }
    
}
