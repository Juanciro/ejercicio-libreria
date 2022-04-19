package com.biblio.bibliotecalocal.Entidades;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Prestamo {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private String id;
    
    @Temporal(TemporalType.DATE)
    private Date fechaPrestamo;
    
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    
    @OneToOne
    private Libro libro;
    @OneToOne
    private Cliente cliente;
    
    Boolean alta;

    public Prestamo() {
    }

    public Prestamo(Libro libro, Cliente cliente) {
        this.fechaPrestamo = new Date();
        this.fechaDevolucion = null;
        this.libro = libro;
        this.cliente = cliente;
        this.alta = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Boolean getAlta(){
        return this.alta;
    }
    
    public void setAlta(Boolean alta){
        this.alta = alta;
    }
    
    
}
