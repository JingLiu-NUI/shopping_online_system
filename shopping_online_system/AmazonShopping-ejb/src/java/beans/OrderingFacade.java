/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Ordering;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lenovo
 */
@Stateless
public class OrderingFacade extends AbstractFacade<Ordering> implements OrderingFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderingFacade() {
        super(Ordering.class);
    }
    
    @Override
    public List<Ordering> findByProductID(int productID){
        Query query = em.createNamedQuery("Ordering.findByProductID");
        query.setParameter("productID", productID);
        return query.getResultList();
    }
}
