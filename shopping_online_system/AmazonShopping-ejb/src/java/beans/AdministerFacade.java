/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Administer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lenovo
 */
@Stateless
public class AdministerFacade extends AbstractFacade<Administer> implements AdministerFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em; 
    
    //User name 
    private String userName;
    //user ID
    private int id;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministerFacade() {
        super(Administer.class);
    }
    
       @Override
    public int getAdministerID(String administername, String password) {
           //create Query
        Query query = em.createNamedQuery("Administer.getAdminID");
        query.setParameter("username", administername);
        query.setParameter("password", password);
        //Execute Query
        int id_returned = (int) query.getSingleResult();
        //setting current id 
        this.id = id_returned;
        return id_returned; 
    }

    @Override
    public boolean isAdministerExist(String username) {
        Query query = em.createNamedQuery("Administer.findByUsername").setParameter("username", username);
        // return query result
        if((long)query.getSingleResult() > 0)
        {
         // exist
         return true;
        }
        // not exist
        return false;
    }

    @Override
    public boolean isValideAdmin(String username, String password) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Administer.validate");
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        // return the number of records
        if((long)query.getSingleResult() > 0)
        {
          System.out.println("ATTENTION : Administer " + username + "was queried");
         return true; // successful to login
        }
        //fail to login
        System.out.println("ATTENTION: Someone try to use name"+  username + "have a query");
        return false;
    }
    
    @Override
    public void setAdminName(String username){
        this.userName = username;
    }
    
}
