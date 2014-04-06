/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.automoviles.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author josediaz
 */
public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("default");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
