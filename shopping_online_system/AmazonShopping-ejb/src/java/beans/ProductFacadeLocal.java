/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface ProductFacadeLocal {

    void create(Product product);

    void edit(Product product);

    void remove(Product product);

    Product find(Object id);

    List<Product> findAll();

    List<Product> findRange(int[] range);

    int count(); 
         
    //get details of specific user by id
    public List<Product> getProductByID(int id); 

    // get the max primary key
    Integer FindmaxId();
    
    //get the product list by the product name
    List<Product> getProductsByName(String productName);
    
}
