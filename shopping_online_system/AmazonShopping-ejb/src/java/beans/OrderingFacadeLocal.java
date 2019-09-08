/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Ordering;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface OrderingFacadeLocal {

    void create(Ordering ordering);

    void edit(Ordering ordering);

    void remove(Ordering ordering);

    Ordering find(Object id);

    List<Ordering> findAll();

    List<Ordering> findRange(int[] range);

    int count();
    
    List<Ordering> findByProductID(int productID);
}
