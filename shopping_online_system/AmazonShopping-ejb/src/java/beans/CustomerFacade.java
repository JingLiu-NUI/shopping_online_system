/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Customer;
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
public class CustomerFacade extends AbstractFacade<Customer> implements CustomerFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em;
    //User name 
    private String userName;
    //user ID
    private long id;
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    public List<Customer> findAllByName(String userName) {
      Query query = em.createNamedQuery("Customer.findByUsername").setParameter("username", userName);
      return query.getResultList();
    }
    
        @Override // useless function (no place invoke this function)
    public boolean updateByID(int id, int new_id, String username, String password,int age, String phonenumber, String userMSG, String email){
            Customer customer = em.find(Customer.class, id);
            if(em.contains(customer)){
                 System.out.println("The entity bean CUSTOMER is Contained managed by em");
                customer.setId(new_id);
                customer.setUsername(username);
                customer.setPassword(password);
                customer.setAge(age);
                customer.setPhoneNumber(phonenumber);
                customer.setUserMSG(userMSG);
                customer.setEmail(email); 
            return true;
            }
            else{
                System.out.println("The entity bean CUSTOMER NOT in persistence unit now");
            return false;
            }
    }
       
    @Override // update the customer by pass the entity reference with the new value.
    public void updateCustomer(Customer old_customer, int id,String username, String password, int age, String phonenumber, String userMSG,String email){ 
        //fistly remove the previous record
        remove(old_customer);
        // then create a new record
        create(  new Customer(id, username, password, age, phonenumber, userMSG, email)  );
        // because it cannot invoke the method em.edit( Customer c) because of the javax.ejb.EJBException: Transaction aborted  Exception...........
    }
    
    @Override
    public int getCustomerID(String username, String password){ 
        //create Query
        Query query = em.createNamedQuery("Customer.getCustomerID");
        query.setParameter("username", username);
        query.setParameter("password", password);
         int id_returned ;
        //Execute Query
        try{
           id_returned = (int) query.getSingleResult(); 
        }
        catch(Exception e){
            e.printStackTrace();;
            return -1;
        }
        //setting current id 
        this.id = id_returned;
        return id_returned; 
    }

    @Override
    public boolean isCustomerExist(String username) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Customer.isCustomerExists").setParameter("username", username);
        // return query result
        if((long)query.getSingleResult() > 0)
        {
          System.out.println("(User"+ username +" is exists)");
          return true;
        }
        System.out.println("(User"+ username +" is not exists)");
        return false;
    }

    @Override
    public boolean isValideCustomer(String username, String password) {
         // create named query and set parameter
        Query query = em.createNamedQuery("Customer.validate");
        query.setParameter("username", username);
        query.setParameter("password", password);
        // return the number of records
        if((long)query.getSingleResult() > 0)
        {
         System.out.println("user "+ username + "login now");
         return true; // successful to login
        }
        //fail to login
        return false;
    } 

    @Override
    public void setCustomerName(String username) {
       this.userName = username;
    }

    @Override
    public List<Customer> getCustomerByID(int id) {
        Query query = em.createNamedQuery("Customer.findById");
        query.setParameter( "id" , id);
        return query.getResultList();
    }
     
    @Override
    public List<Customer> getCustomerByName(String  name) {
        Query query = em.createNamedQuery("Customer.findByUsername");
        query.setParameter( "username" , name);
        return query.getResultList();
    }

    
    
    
    
}
