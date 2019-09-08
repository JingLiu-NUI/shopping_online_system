/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface CustomerFacadeLocal {
 //add a new record into the table customer 
    void create(Customer customer);
 
    public void edit(Customer customer);
     
    //update      
    public boolean updateByID(int id, int new_id, String username, String password,int age, String phonenumber, String userMSG, String email);

    //delete
    void remove(Customer customer);

    Customer find(Object id);

    // reuturn all records of the the table Customer
    List<Customer> findAll();

    List<Customer> findRange(int[] range);

    //IDE Auto-generated : return the number of records of the Customer Table
    int count();
    
    //set customer name ?
    public void setCustomerName(String username);
    
    // get customer id ?
    public int getCustomerID(String username,String password) ;

    // check if the user is exist in the database
    public boolean isCustomerExist(String username);
    
    //check if it is a valide recored in database
    public boolean isValideCustomer(String username ,String password);
     
    //get details of specific user by id
    public List<Customer> getCustomerByID(int id) ;
    
    //get user details by specific name
    public List<Customer> getCustomerByName(String name);

    public void updateCustomer(Customer old_customer, int id,String username, String password, int age, String phonenumber, String userMSG,String email);
 
}
