package com.biblio.bibliotecalocal.Repositorios;

import com.biblio.bibliotecalocal.Entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public List<Libro> buscarPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.anio = :anio")
    public List<Libro> buscarPorAnio(@Param("anio") Integer anio);
    
//    TODAVIA NO SABEMOS SI FUNCIIONA
//    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
//    public List<Libro> buscarPorNombreAutor(@Param("nombre") String nombre);
    
}

// HACER LOS SERVICIOS
// LA BASE DE DATOS TIENE 2 LIBROS 2 AUTORES Y 2 EDITORIALES
// PROBAR EN SERVICIOS BUSCAR LIBRO POR NOMBRE DE AUTOR
// PROBAR EN SERVICIOS BUSCAR LIBRO POR NOMBRE DE EDITORIAL