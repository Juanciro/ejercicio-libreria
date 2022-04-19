package com.biblio.bibliotecalocal.Repositorios;

import com.biblio.bibliotecalocal.Entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    
    
    
    @Query("SELECT c FROM Cliente c WHERE c.documento = :dni")
    public Cliente buscarPorDni(@Param("dni") Long dni);
    
    
    
}
