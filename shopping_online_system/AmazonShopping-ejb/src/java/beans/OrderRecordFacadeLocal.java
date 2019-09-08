/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.OrderRecord;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface OrderRecordFacadeLocal {

    void create(OrderRecord orderRecord);

    void edit(OrderRecord orderRecord);

    void remove(OrderRecord orderRecord);

    OrderRecord find(Object id);

    List<OrderRecord> findAll();

    List<OrderRecord> findRange(int[] range);
    
    List<OrderRecord> findRecordByName(String name);
    
    List<OrderRecord> findAllByName(String userName);
    
    int findTheMaxOrderId();

    int count();
    
}
