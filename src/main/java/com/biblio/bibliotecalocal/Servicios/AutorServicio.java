package com.biblio.bibliotecalocal.Servicios;

import com.biblio.bibliotecalocal.Entidades.Autor;
import com.biblio.bibliotecalocal.Repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    AutorRepositorio ar;
    
    @Transactional(readOnly = true)
    public List<Autor> buscarPorNombre(String nombre) throws Exception{
        
        try {
            validator(nombre);
            
            return ar.buscarPorNombre(nombre);
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public Autor buscarPorId(String id){
        return ar.getById(id);
    }
    
    @Transactional
    public Autor save(String nombre) throws Exception{
        validator(nombre);
        Autor entidad = new Autor(nombre, true);
        return ar.save(entidad);
    }
    
    @Transactional
    public Autor edit(String id, String nombre) throws Exception{
        
        Optional<Autor> respuesta = ar.findById(id);
        if(respuesta.isPresent()){
            validator(nombre);
            Autor a = respuesta.get();
            a.setNombre(nombre);
            return ar.save(a);
        }else{
            throw new Exception("No se pudo editar");
        }
    }
    
    @Transactional
    public Autor alta(String id){
        Optional<Autor> respuesta = ar.findById(id);
        if(respuesta.isPresent()){
            Autor a = respuesta.get();
            a.setAlta(true);
            return ar.save(a);
        }else{
            return null;
        }
    }
    
    @Transactional
    public Autor baja(String id){
        Optional<Autor> respuesta = ar.findById(id);
        if(respuesta.isPresent()){
            Autor a = respuesta.get();
            a.setAlta(false);
            return ar.save(a);
        }else{
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Autor> getAll(){
        return ar.findAll();
    }
    
    
    public void validator(String nombre) throws Exception{
        if(nombre == null || nombre.trim().isEmpty()){
            throw new Exception("Nombre autor invalido.");
        }
    }
    
}
