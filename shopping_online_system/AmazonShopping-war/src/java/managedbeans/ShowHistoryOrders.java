
package managedbeans;
import beans.CartFacadeLocal;
import beans.OrderRecordFacadeLocal;
import beans.ProductFacadeLocal;
import entity.*;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.util.*;
import java.io.Serializable;
import login.SessionBean;
@Named(value = "showHistoryOrders")
@SessionScoped
/*this method is used to display users historical orders. when this user had logged in. */
public class ShowHistoryOrders implements Serializable{
   private String name;      
    
    public String getName() {
        return name;
    } 
    public void setName(String name) {
        this.name = name;
    }
    
    public ShowHistoryOrders(){
        name =  SessionBean.getUserName();
        System.err.println("name="+SessionBean.getUserName());
        if(name==null)
            name="joe";
    } 
    @EJB
    private OrderRecordFacadeLocal orderRecordFacade;
    @EJB
    private ProductFacadeLocal productFacade;
    @EJB
    private CartFacadeLocal cartFacade;
    /*get all informatio about the products*/
    public List<Product> showAllProduct()
    {
        return productFacade.findAll();
    }
    /*get all relative records for this user following the database*/
    public List<OrderRecord> showAllRecordByUser()
    {
          return orderRecordFacade.findRecordByName(name);
    }
    /*Get the order details for each order record, because ordering includes the details about product,
    such as name, price.*/
    public List<Ordering> showAllOrders(OrderRecord ordrec)
    {
        return ordrec.getOrderingList();
    } 

    
}
