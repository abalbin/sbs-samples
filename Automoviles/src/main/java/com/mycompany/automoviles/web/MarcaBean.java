package com.mycompany.automoviles.web;

import com.mycompany.automoviles.entidades.Marca;
import com.mycompany.automoviles.util.JPAUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by josediaz on 4/3/14.
 */
@ManagedBean
@ViewScoped
public class MarcaBean {

    private Marca marca;
    private List<Marca> marcas;
    private boolean continuarInsercion;

    @PostConstruct
    public void init(){
        //inicializamos objetos
        marca = new Marca();
    }

    public void grabar() {
        EntityManager em = JPAUtil.getEntityManager();
        /* TODO: Tenemos que abrir transaccion sino no grabara nada*/
        em.getTransaction().begin();
        em.persist(marca);
        em.getTransaction().commit();
        em.close();

    }

    public List<Marca> getMarcas(){
        if(marcas == null){
            marcas = JPAUtil.getEntityManager().
              createQuery("select m from Marca m", Marca.class).getResultList();
        }
        return marcas;
    }


    //getters y setters

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    public boolean isContinuarInsercion() {
        return continuarInsercion;
    }

    public void setContinuarInsercion(boolean continuarInsercion) {
        this.continuarInsercion = continuarInsercion;
    }

}
