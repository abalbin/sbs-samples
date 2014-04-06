/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.automoviles.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author josediaz
 */
@NamedQueries({
        @NamedQuery(name = Automovil.LISTAR_DESTACADOS, query = "select a from Automovil a", hints = {
                @QueryHint(name = "org.hibernate.cacheable", value = "true"),
                @QueryHint(name = "org.hibernate.cacheRegion", value = Automovil.LISTAR_DESTACADOS)})
})
@Entity
@Cacheable
public class Automovil implements Serializable {

    private static final long serialVersionUID = 9183366721265460766L;
    public static final String LISTAR_DESTACADOS = "Automovil.buscarDestacados";


    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private Modelo modelo;


    @Min(1900)
    private Integer anoFabricacion;
    @Min(1900)
    private Integer anoModelo;
    private Float precio;
    private Float kilometraje;
    private String observaciones;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Modelo getModelo() {
        return modelo;
    }
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }



    public Integer getAnoFabricacion() {
        return anoFabricacion;
    }

    public void setAnoFabricacion(Integer anoFabricacion) {
        this.anoFabricacion = anoFabricacion;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Float kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Automovil other = (Automovil) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Automovil{" + "id=" + id + ", modelo=" + modelo + ",  anoFabricacion=" + anoFabricacion + ", anoModelo=" + anoModelo + ", precio=" + precio + ", kilometraje=" + kilometraje + ", observaciones=" + observaciones + '}';
    }


}
