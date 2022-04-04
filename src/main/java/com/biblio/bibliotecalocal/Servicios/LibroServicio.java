package com.biblio.bibliotecalocal.Servicios;

import com.biblio.bibliotecalocal.Entidades.Autor;
import com.biblio.bibliotecalocal.Entidades.Editorial;
import com.biblio.bibliotecalocal.Entidades.Libro;
import com.biblio.bibliotecalocal.Repositorios.AutorRepositorio;
import com.biblio.bibliotecalocal.Repositorios.EditorialRepositorio;
import com.biblio.bibliotecalocal.Repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired
    LibroRepositorio lr;
    
    @Autowired
    EditorialRepositorio er;
    
    @Autowired
    AutorRepositorio ar;
    
    @Transactional(readOnly = true)
    public List<Libro> buscarPorTitulo(String titulo) throws Exception{
        
        if(titulo != null && !titulo.trim().isEmpty()){
            return lr.buscarPorTitulo(titulo);
        }else{
            throw new Exception("No se encontro el titulo en la bdd");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Libro> buscarPorAnio(Integer anio) throws Exception{
        
        if(anio != null && anio>0){
            return lr.buscarPorAnio(anio);
        }else{
            throw new Exception("No se encontro el año en la bdd");
        }
    }
    
    @Transactional(readOnly = true)
    public Libro buscarPorId(String id){
        
        return lr.getById(id);
    }
    
    @Transactional
    public Libro save(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String nombreAutor, String nombreEditorial, String image) throws Exception{
        
        try {
            validator(isbn, titulo, anio, ejemplares, ejemplaresPrestados, nombreAutor, nombreEditorial);
            Editorial ed = er.buscarPorNombre(nombreEditorial).get(0);
            Autor au = ar.buscarPorNombre(nombreAutor).get(0);
            if(ed == null || au == null){
                throw new Exception("No se encontro el Autor o la Editorial en la base de datos");
            }
            Libro entidad = new Libro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, true, au, ed, image);
            return lr.save(entidad);
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    @Transactional
    public Libro edit(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String nombreAutor, String nombreEditorial, String image) throws Exception{
        
        Optional<Libro> respuesta = lr.findById(id);
        if(respuesta.isPresent()){
            validator(isbn, titulo, anio, ejemplares, ejemplaresPrestados, nombreAutor, nombreEditorial);
            Editorial ed = er.buscarPorNombre(nombreEditorial).get(0);
            Autor au = ar.buscarPorNombre(nombreAutor).get(0);
            if(ed == null || au == null){
                throw new Exception("No se encontro el Autor o la Editorial en la base de datos");
            }
            Libro l = respuesta.get();
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            l.setAnio(anio);
            l.setEjemplares(ejemplares);
            l.setEjemplaresPrestados(ejemplaresPrestados);
            l.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
            l.setAutor(au);
            l.setEditorial(ed);
            l.setImage(image);
            return lr.save(l);
        }else{
            throw new Exception("No se detecto el Libro por id");
        }
        
    }
    
    @Transactional
    public Libro baja(String id){
        Optional<Libro> respuesta = lr.findById(id);
        if(respuesta.isPresent()){
            Libro l = respuesta.get();
            l.setAlta(false);
            return lr.save(l);
        }else{
            return null;
        }
    }
    
    @Transactional
    public Libro alta(String id){
        Optional<Libro> respuesta = lr.findById(id);
        if(respuesta.isPresent()){
            Libro l = respuesta.get();
            l.setAlta(true);
            return lr.save(l);
        }else{
            return null;
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Libro> getAll(){
        return lr.findAll();
    }
    
    
    public void validator(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String nombreAutor, String nombreEditorial) throws Exception{
        
        if(isbn == null){
            throw new Exception("Isbn vacio.");
        }
        if(titulo == null || titulo.trim().isEmpty()){
            throw new Exception("Titulo vacio.");
        }
        if(anio == null){
            throw new Exception("Año vacio.");
        }else if(anio <0){
            throw new Exception("Año invalido.");
        }
        if(ejemplares == null || ejemplares< 0){
            throw new Exception("Ejemplares invalido.");
        }
        if(ejemplaresPrestados == null || ejemplaresPrestados<0){
            throw new Exception("Ejemplares prestados invalido.");
        }
        if(nombreAutor == null || nombreAutor.trim().isEmpty()){
            throw new Exception("Autor vacio.");
        }
        if(nombreEditorial == null || nombreEditorial.trim().isEmpty()){
            throw new Exception("Editorial vacio.");
        }
        
    }
    
    
}
