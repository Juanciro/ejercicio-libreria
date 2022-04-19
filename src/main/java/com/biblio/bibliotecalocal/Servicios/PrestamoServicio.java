package com.biblio.bibliotecalocal.Servicios;

import com.biblio.bibliotecalocal.Entidades.Cliente;
import com.biblio.bibliotecalocal.Entidades.Libro;
import com.biblio.bibliotecalocal.Entidades.Prestamo;
import com.biblio.bibliotecalocal.Repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {
    
    @Autowired
    PrestamoRepositorio pr;
    
    @Autowired
    ClienteServicio cs;
    
    @Autowired
    LibroServicio ls;
    
    @Transactional()
    public Prestamo save(Long dni, Long isbn) throws Exception{
        
        Libro li = ls.buscarPorISBN(isbn);
        if(li == null){
            throw new Exception("no se encontro el libro");
        }
        Cliente cl = cs.buscarPorDni(dni);
        if(cl == null){
            throw new Exception("no se encontro el cliente");
        }
        Prestamo p = new Prestamo(li, cl);
        
        return pr.save(p);
    }
    
    @Transactional(readOnly = true)
    public List<Prestamo> getAll(){
        
        return pr.findAll();
    }
    
    @Transactional(readOnly = true)
    public Prestamo buscarPorId(String id){
        return pr.getById(id);
    }
    
    @Transactional
    public Prestamo alta(String id) throws Exception{
        Optional<Prestamo> respuesta = pr.findById(id);
        if(respuesta.isPresent()){
            Prestamo p = respuesta.get();
            p.setAlta(true);
            return pr.save(p);
        }else{
            throw new Exception("no se encontro en la base de datos");
        }
    }
    
    @Transactional
    public Prestamo baja(String id) throws Exception{
        Optional<Prestamo> respuesta = pr.findById(id);
        if(respuesta.isPresent()){
            Prestamo p = respuesta.get();
            p.setAlta(false);
            p.setFechaDevolucion(new Date());
            return pr.save(p);
        }else{
            throw new Exception("no se encontro en la base de datos");
        }
    }
    
    @Transactional
    public Prestamo edit(String id, Long dni, Long isbn, Boolean abierto) throws Exception{
        
        Optional<Prestamo> respuesta = pr.findById(id);
        if(respuesta.isPresent()){
            
            Cliente cl = cs.buscarPorDni(dni);
            Libro li = ls.buscarPorISBN(isbn);
            
            Prestamo p = respuesta.get();
            p.setCliente(cl);
            p.setLibro(li);
            if(abierto){
                p.setFechaDevolucion(null);
                p.setAlta(true);
            }else if(!abierto && p.getAlta()){
                p.setFechaDevolucion(new Date());
                p.setAlta(false);
            }
            return pr.save(p);
        }else{
            throw new Exception("No se encontro el cliente por id");
        }
        
    }
    
    
}
