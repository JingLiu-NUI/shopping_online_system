/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Product;
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
public class ProductFacade extends AbstractFacade<Product> implements ProductFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
     @Override
    public List<Product> getProductByID(int id) {
        Query query = em.createNamedQuery("Product.findByProductID");
        query.setParameter("productID", id);
        return query.getResultList();
    }

    @Override
    public Integer FindmaxId(){
       Query query=em.createNamedQuery("Product.findMaxId");
       return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Product> getProductsByName(String productName) {
        Query query = em.createNamedQuery("Product.findByProductName");
        query.setParameter("productName", productName);
        return query.getResultList();
    }
    
}
