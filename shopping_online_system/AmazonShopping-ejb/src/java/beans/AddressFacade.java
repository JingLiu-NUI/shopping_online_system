/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Address;
import entity.AddressPK;
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
public class AddressFacade extends AbstractFacade<Address> implements AddressFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }
     
    @Override
    public void addNewAddress(Address address) {
        em.persist(address);
    }

 
    @Override
    public List<Address> findAllByName(String userName) {
       if(userName==null)
           return null; // null! you shuold cast it to a new List<Address>
      Query query = em.createNamedQuery("Address.findByUsername").setParameter("username", userName);
      return query.getResultList();
    }
    
    @Override
    public void createNewAddress(String userName, String id, String address){
        Address temp = new Address();
        AddressPK pk = new AddressPK(userName,Integer.parseInt(id)); 
      //  em.persist(temp);
        temp.setAddressPK(pk);
        temp.setAddress(address); 
    }
    
    @Override 
    public List<Address> findAllByAddressID(int addressID){
        if(addressID == 0)
           return  null;
        else{ 
            Query query = em.createNamedQuery("Address.findByAddressID").setParameter("addressID", addressID);
            return query.getResultList();
        }
            
    }
}
