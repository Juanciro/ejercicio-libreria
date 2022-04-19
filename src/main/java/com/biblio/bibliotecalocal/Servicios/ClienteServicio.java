package com.biblio.bibliotecalocal.Servicios;

import com.biblio.bibliotecalocal.Entidades.Cliente;
import com.biblio.bibliotecalocal.Repositorios.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {
    
    @Autowired
    ClienteRepositorio cr;
    
    @Transactional
    public Cliente save(Long documento, String nombre, String apellido, String telefono) throws Exception{
        validator(documento, nombre, apellido, telefono);
        Cliente entidad = new Cliente(documento, nombre, apellido, telefono);
        return cr.save(entidad);
    }
    
    @Transactional(readOnly = true)
    public Cliente buscarPorDni(Long dni) throws Exception{
        if(dni == null || dni <0 || dni > 70000000){
            throw new Exception("documento no valido");
        }
        return cr.buscarPorDni(dni);
    }
    
    @Transactional(readOnly = true)
    public List<Cliente> getAll(){
        return cr.findAll();
    }
    
    @Transactional(readOnly = true)
    public Cliente buscarPorId(String id){
        return cr.getById(id);
    }
    
    @Transactional
    public Cliente alta(String id) throws Exception{
        Optional<Cliente> respuesta = cr.findById(id);
        if(respuesta.isPresent()){
            Cliente c = respuesta.get();
            c.setAlta(true);
            return cr.save(c);
        }else{
            throw new Exception("no se encontro en la base de datos");
        }    
    }
    
    @Transactional
    public Cliente baja(String id) throws Exception{
        Optional<Cliente> respuesta = cr.findById(id);
        if(respuesta.isPresent()){
            Cliente c = respuesta.get();
            c.setAlta(false);
            return cr.save(c);
        }else{
            throw new Exception("no se encontro en la base de datos");
        }    
    }
    
    @Transactional
    public Cliente edit(String id, Long documento, String nombre, String apellido, String telefono) throws Exception{
        
        Optional<Cliente> respuesta = cr.findById(id);
        if(respuesta.isPresent()){
            validator(documento, nombre, apellido, telefono);
            Cliente c = respuesta.get();
            c.setDocumento(documento);
            c.setNombre(nombre);
            c.setApellido(apellido);
            c.setTelefono(telefono);
            return cr.save(c);
        }else{
            throw new Exception("No se encontro el cliente por id");
        }
    }
    
    
    
    private void validator(Long documento, String nombre, String apellido, String telefono) throws Exception{
        if(documento == null || documento <0 || documento > 70000000){
            throw new Exception("documento no valido");
        }
        if(nombre == null || nombre.trim().isEmpty()){
            throw new Exception("nombre no valido");
        }
        if(apellido == null || apellido.trim().isEmpty()){
            throw new Exception("apellido no valido");
        }
        if(telefono == null || telefono.length()<10){
            throw new Exception("telefono no valido");
        }
    }
    
}
