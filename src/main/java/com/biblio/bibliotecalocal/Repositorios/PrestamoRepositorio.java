package com.biblio.bibliotecalocal.Repositorios;

import com.biblio.bibliotecalocal.Entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 @Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
     
     /*
     @Query("SELECT p FROM Prestamo p WHERE p.alta = TRUE")
     public List<Prestamo> getPrestamosAbiertos();
     */
     /*
     @Query("SELECT p FROM Prestamo p WHERE p.cliente.documento = :dni")
     public List<Prestamo> getPrestamosPorDni(@Param("dni") Long dni);
     */
}
