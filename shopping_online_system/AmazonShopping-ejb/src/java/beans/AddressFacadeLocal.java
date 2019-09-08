/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Address;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface AddressFacadeLocal {

    void create(Address address);

    void edit(Address address);

    void remove(Address address);

    Address find(Object id);

    List<Address> findAll();

    List<Address> findRange(int[] range);

    int count();
    
    void addNewAddress(Address address);
        
    List<Address> findAllByName(String userName);
    
    List<Address> findAllByAddressID(int addressID);
    
    void createNewAddress(String userName, String id, String address);
}
