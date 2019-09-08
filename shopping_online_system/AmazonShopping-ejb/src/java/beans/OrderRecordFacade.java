/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.OrderRecord;
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
public class OrderRecordFacade extends AbstractFacade<OrderRecord> implements OrderRecordFacadeLocal {

    @PersistenceContext(unitName = "AmazonShopping-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderRecordFacade() {
        super(OrderRecord.class);
    }
     @Override
    public List<OrderRecord> findRecordByName(String name) {
      Query query=em.createNamedQuery("OrderRecord.findByName").setParameter("username", name);
      return query.getResultList();
    }
    @Override
    public List<OrderRecord> findAllByName(String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }     
    
 @Override
    public int findTheMaxOrderId() {
       Query query=em.createNamedQuery("OrderRecord.findMaxId");
       List<Integer> list=query.getResultList();
       if(list.size()==0)
           return 1;
       else
       {
         int max=1;
         for(int i=0;i<list.size();i++)
         {
            if(list.get(i)>max)
                max=list.get(i);
         }
            return max;
       }
    }

}
