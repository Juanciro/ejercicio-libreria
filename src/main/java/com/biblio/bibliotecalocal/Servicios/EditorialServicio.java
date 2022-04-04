package com.biblio.bibliotecalocal.Servicios;

import com.biblio.bibliotecalocal.Entidades.Editorial;
import com.biblio.bibliotecalocal.Repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class EditorialServicio {
    
    @Autowired
    EditorialRepositorio er;
    
    @Transactional(readOnly = true)
    public List<Editorial> buscarPorNombre(String nombre) throws Exception{
        try {
            validator(nombre);
            return er.buscarPorNombre(nombre);
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public Editorial buscarPorId(String id){
        return er.getById(id);
    }
    
    @Transactional
    public Editorial save(String nombre) throws Exception{
        validator(nombre);
        Editorial entidad = new Editorial(nombre, true);
        return er.save(entidad);
    }
    
    @Transactional
    public Editorial edit(String id, String nombre) throws Exception{
        Optional<Editorial> respuesta = er.findById(id);
        if(respuesta.isPresent()){
            validator(nombre);
            Editorial e = respuesta.get();
            e.setNombre(nombre);
            return er.save(e);
        }else{
            return null;
        }
    }
    
    @Transactional
    public Editorial alta(String id) throws Exception{
        Optional<Editorial> respuesta = er.findById(id);
        if(respuesta.isPresent()){
            Editorial e = respuesta.get();
            e.setAlta(true);
            return er.save(e);
        }else{
            throw new Exception("No se pudo dar de alta");
        }
    }
    
    @Transactional
    public Editorial baja(String id) throws Exception{
        Optional<Editorial> respuesta = er.findById(id);
        if(respuesta.isPresent()){
            Editorial e = respuesta.get();
            e.setAlta(false);
            return er.save(e);
        }else{
            throw new Exception("No se pudo dar de baja");
        }
    }
    
    
    public void validator(String nombre) throws Exception{
        if(nombre == null || nombre.trim().isEmpty()){
            throw new Exception("Nombre autor invalido.");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> getAll(){
        return er.findAll();
    }
    
}
