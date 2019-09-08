package managedbeans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import beans.AddressFacadeLocal;
import beans.CustomerFacadeLocal;
import entity.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Named(value = "newJSFManagedBean")
@SessionScoped
public class AddressManagedBean implements Serializable{

    @Resource(mappedName = "jms/dest")
    private Queue dest;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    
    @EJB
    private AddressFacadeLocal addressFacadeLocal;    
    
    private static int count=0;
    private List<Address> emp;
    private String UserName;
    private String address1;
    private int MAX;
    private String selectValue = null;
    public static int getCount() {
        return count;
    }
    public ArrayList<String> getAddress(String userName){
        ArrayList<String> list = new ArrayList<String>();
        emp = addressFacadeLocal.findAllByName(userName);
        for(int i = 0; i < emp.size(); ++i){
            list.add(emp.get(i).getAddress());
        }
        return list;
    }
    
    public List<Address> getList(String userName){
        emp = addressFacadeLocal.findAllByName(userName);
        return emp;
    }

    public String showUserName(Address address){
        return address.getAddressPK().getUsername();
    }
    
    public int showUserID(Address address){
        return address.getAddressPK().getAddressID();
    }
    
    public String showUserAddress(Address address){
        return address.getAddress();
    }
    
 
    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }
  
    public int getMAX() {
        return MAX;
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
    }
    
    public void setAddress(String address) {
        this.address1 = address;
    }

    public String getAddress() {
        return address1;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return UserName;
    }
 
    public AddressManagedBean() {  }
 
    public void addAddress(String userName){
        String address = getAddress();
        if(address == null || address.equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "Please input address",
                                                "Address cannot be empty")
            );
            return ;
        }
       int temp = getNewAddressId(userName);  //get the max address id in the table 
                                              //thus it is never has the simuation for the address id duplication for this customer in table 
        
       CustomerFacadeLocal customerSB = null;
       Customer customer = null; 
       
       // get the current customer
       try {
            // obtain initial context object 
            InitialContext context = new InitialContext();
            customerSB = (CustomerFacadeLocal) context.lookup("java:global/AmazonShopping/AmazonShopping-ejb/CustomerFacade!beans.CustomerFacadeLocal");
           //if it cann't get the Session Bean for customer, return. 
            if(customerSB ==null){
                System.err.println("Error for JNDI Lookup the EJB: java:global/AmazonShopping/AmazonShopping-ejb/CustomerFacad!beans.CustomerFacadeLocal");
                return;
            } 
            // invoke EJB method            
            List<Customer> customerListWithSpecificName = customerSB.getCustomerByName(userName);
            if(!customerListWithSpecificName.isEmpty()){
                customer = customerListWithSpecificName.get(0);  // successful find the current user
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null,
                                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                "Please log out and re-try it again",
                                                                "No such customer in the data base.\n OR user session has expired now.")
                        );
                return ;
            }
        }  
       catch (NamingException ex) {
            Logger.getLogger(AddressManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }  
       //add new address
       Address add = new Address();
       AddressPK pk = new AddressPK(userName, temp); 
       add.setAddressPK(pk);
       add.setAddress(getAddress());
       add.setCustomer( customer );
       addressFacadeLocal.create(add);
       sendJMSMessageToDest("user:"+ userName +" add a new address:"+getAddress());
    } 
    
   // return the max id for the customer with the username is 'Username'
    public int getNewAddressId(String Username){
         int max = 0;
         emp = addressFacadeLocal.findAllByName(Username);
          for(int i = 0; i < emp.size(); ++i){
              int temp = emp.get(i).getAddressPK().getAddressID();
              if(temp >= max)
                  max = temp;
          }
          return max + 1;
    }

    private void sendJMSMessageToDest(String messageData) {
        context.createProducer().send(dest, messageData);
    }
      
}
