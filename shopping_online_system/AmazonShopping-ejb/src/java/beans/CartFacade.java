/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Cart;
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
public class CartFacade extends AbstractFacade<Cart> implements CartFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     @Override
    public List<Cart> findByName(String name)
    {
        Query query=em.createNamedQuery("Cart.findByUsername").setParameter("username", name);
        return query.getResultList();
    }
    public CartFacade() {
        super(Cart.class);
    }

    @Override
    public void createNewCart(Cart cart) {
        em.persist(cart);
    }
    
    
    
}
