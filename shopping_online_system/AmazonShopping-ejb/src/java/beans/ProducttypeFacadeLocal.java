/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Producttype;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface ProducttypeFacadeLocal {

    void create(Producttype producttype);

    void edit(Producttype producttype);

    void remove(Producttype producttype);

    Producttype find(Object id);

    List<Producttype> findAll();

    List<Producttype> findRange(int[] range);

    int count();
    
}
