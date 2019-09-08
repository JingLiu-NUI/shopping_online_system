/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Producttype;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lenovo
 */
@Stateless
public class ProducttypeFacade extends AbstractFacade<Producttype> implements ProducttypeFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProducttypeFacade() {
        super(Producttype.class);
    }
    
}
