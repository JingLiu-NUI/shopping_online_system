package managedbeans;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import javax.ejb.EJB;
import beans.*;
import entity.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import login.SessionBean;
@Named(value = "orderRecordManagedBean")
@SessionScoped
public class OrderRecordManagedBean implements  Serializable{

    @Resource(mappedName = "jms/dest")
    private javax.jms.Queue dest;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @EJB
    private CartFacadeLocal cartFacade;
    @EJB
    private OrderingFacadeLocal orderingFacade;   
    @EJB
    private OrderRecordFacadeLocal orderRecordFacadeLocal;
    @EJB
    private CustomerFacadeLocal customerFacadeLocal;
    private static List<OrderRecord> emp;
    private static List<Customer> temp;
    public OrderRecordManagedBean() {
        
    }
    //INSERT INTO amazonsystemdb.order_record (`Username`, `Order_ID`, `Total_Price`, `Order_Date`, `Status`, `Address`) 
    //VALUES ('joe', 43, 567.89, '2017-04-03 21:13:48.0', true, 'limerrick......');
    //OrderRecordPK orderRecordPK, double totalPrice, Date orderDate, boolean status, String address
    public String createNewRecord(String Username, int Order_ID, String Total_Price, String Address,List<Cart> cart) throws ParseException{
        if( "".equals(Address)|| Address==null || Username ==null || Total_Price==null ){
             FacesContext.getCurrentInstance()
                     .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                 "Please input address(address cannot be empty); if you address is not empty please re-log again, chrees.",
                                 "User seesion has expried now \n Otherwise the address is empty")
                     );
             return "HomePage";
        }
        // for update the product quantity
        ProductFacadeLocal productSB = null;
         try {
            // obtain initial context object 
            InitialContext context = new InitialContext();
            productSB = (ProductFacadeLocal)context.lookup("java:global/AmazonShopping/AmazonShopping-ejb/ProductFacade!beans.ProductFacadeLocal");
           //if it cann't get the Session Bean for customer, return. 
            if(productSB ==null){
                System.err.println("Error for JNDI Lookup the EJB: java:global/AmazonShopping/AmazonShopping-ejb/ProductFacade!beans.ProductFacadeLocal");
                FacesContext.getCurrentInstance().addMessage(null,
                                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                "Please log out and re-try it again",
                                                                "Session has expired now.")
                        );
                return "login";
            } 
        } 
       catch (NamingException ex) {
            Logger.getLogger(OrderRecordManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }  
         
        int sizeOfCartList = cart.size();
        for(int i = 0; i <sizeOfCartList; i++){
            Product product = cart.get(i).getProduct();
            int quantity = product.getQuantity();
            product.setQuantity( quantity - cart.get(i).getProductQuantity());
            productSB.edit(product);
        }
        
        double Total = Double.parseDouble(Total_Price);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = formatter.format(currentTime); 
        Date temp = formatter.parse(dateString);
        OrderRecord record = new OrderRecord();
        Customer customer=new Customer();
        customer.setId(Integer.parseInt( SessionBean.getUserId() ));
        customer.setUsername(Username);
        record.setUsername(customer);
        record.setOrderID(Order_ID);
        record.setTotalPrice(Total);
        record.setOrderDate(temp);
        record.setAddress(Address);
        record.setStatus(true);        
        createNewOrdering(cart,record);
        deleteMyCart(cart);  
        orderRecordFacadeLocal.create(record);
        sendJMSMessageToDest("You have confirmed a new order");
        return "SuccessfulPayment";
    }
    
    public int searchCusstomerId(String userName){  
       temp = customerFacadeLocal.getCustomerByName(userName);
       int id = temp.get(0).getId();
       return id;
    }
    
    public void deleteMyCart(List<Cart> mycart)
    {
        for(Cart item:mycart)
        {          
            cartFacade.remove(item);
            
        }
    }
    //
    public void createNewOrdering(List<Cart> cart,OrderRecord rec)
    {
        List<Ordering> mycList=new ArrayList<>();
        for(Cart m:cart)
        {
            Ordering ordering=new Ordering();
            OrderingPK orderingPK=new OrderingPK();
            orderingPK.setOrderID(rec.getOrderID());
            orderingPK.setProductID(m.getProduct().getProductID());
            ordering.setOrderingPK(orderingPK);
            ordering.setProduct(m.getProduct());
            ordering.setProductQuantity(m.getProductQuantity());
            ordering.setOrderRecord(rec);
            mycList.add(ordering);
            
        }
          rec.setOrderingList(mycList); 
          sendJMSMessageToDest("You have input the correct details of product");
    }
    
  
    public String showUserName(OrderRecord record){
               return record.getUsername().getUsername();
             //return record.getOrderRecordPK().getUsername();
    }
    
    public int showUserID(OrderRecord record){
              return record.getOrderID();
            //return record.getOrderRecordPK().getOrderID();
    }
    
    public Double showPrice(OrderRecord record){
        return record.getTotalPrice();
    }
    
    public Date showDate(OrderRecord record){
        return record.getOrderDate();
    }
    
    public boolean showStatus(OrderRecord record){
        return record.getStatus();
    }
    
    public String showAddress(OrderRecord record){
        return record.getAddress();
    }
    public int getMaxId()
    {
        return orderRecordFacadeLocal.findTheMaxOrderId()+1;
    }    

    private void sendJMSMessageToDest(String messageData) {
        context.createProducer().send(dest, messageData);
    }

   
}