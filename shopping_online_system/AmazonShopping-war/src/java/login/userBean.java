/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;


import beans.CustomerFacadeLocal;
import beans.AdministerFacadeLocal;
import java.io.Serializable; 
import entity.Administer;
import entity.Customer;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped; 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext; 
import javax.servlet.http.HttpSession;
import security.*;
 
@ManagedBean
@RequestScoped
public class userBean implements Serializable { 
    @EJB 
    CustomerFacadeLocal customerBean; 
    @EJB 
    AdministerFacadeLocal adminBean;
    
    // used for user search functionality : lightweight(because of the @RequestScoped)
    private int id;
    private String username;
    private String password;
    private int age;
    private String phonenumber;
    private String userMSG;
    private String email;
    private String outputTextForValidation = ""; 
    // END Attributes
     
     //attention: initialize() & isANyUserExist() before invoked need check if the database is exist, if not, generate it.

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUserMSG() {
        return userMSG;
    }

    public void setUserMSG(String userMSG) {
        this.userMSG = userMSG;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //modefy details for customer
    //implement by the cross-view session , context look up
    public void modifyDetailFromCustomer()throws SQLException{
         // get session value via so-called SessionBean
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);  
        
        // get username & password via session
        String username_logined_User = (String) session.getAttribute("username");
        // get session password and Decode it
        String password_logined_User = DeEnCode.decode( (String) session.getAttribute("password") );  
        
        //get the current user id
        int old_id  = customerBean.getCustomerID(username_logined_User, password_logined_User); //get customerBean by Id   
        List<Customer> customers = customerBean.getCustomerByID(old_id);                        //get the old entity by id
        Customer old_customer = null    ;                                                       //set initial 
      
        List<Customer> customers_byName = customerBean.getCustomerByName(username);             //check if the user whose has the usename like that if it is in database
        List<Customer> customers_byID   = customerBean.getCustomerByID(id);                     //check if the user whose has the id like that if it is in database
        
        if( customers_byID.isEmpty()==false || customers_byName.isEmpty()==false){              // if the username or id alriday exist in db,  stop executing method.
            outputTextForValidation = "User name OR the ID is allready exists on database, please change another";
            return;
        }  
        
        //if the old customer is exists on the database
        if(!customers.isEmpty()){ //check for get(0)
           old_customer  = customers.get(0); 
           outputTextForValidation = checkValidationForUserInput(id, username, password, age, phonenumber, userMSG, email); //get the check message for the user input if correct
           if( ! "".equals(outputTextForValidation) )                             //if not setify the gaurd condition : stop it.                         
               return;
            customerBean.updateCustomer(old_customer, id, username, password, age, phonenumber, userMSG, email); 
            session.setAttribute("username", username);                           //set session attribute if successful updated
            session.setAttribute("password", password);
            outputTextForValidation = "Suceeessful Update Now";                   //prompt user
        }
        //if there is no the old account exist do not anything & prompt exception
        if(old_customer == null){
            // no such customer named 'username' 
            outputTextForValidation = "  Exception! Please logout and re-login again"; 
        }  
    }
    
    
    private  String checkValidationForUserInput(int id,String username,String password,int age,String phonenumber,String userMSG,String email){
        String result = "";
        //if id inputed (not null is true)
        if( InputValidate.notNullCheck(id+""))
            result += "User id MUST BE  NOT NULL!!\n";
        if( InputValidate.notNullCheck(username) )
            result += "User username CANNOT BE  NOT NULL!!\n";
        if( password.length() <= 0)
            result += "password length must be greater than 1 character\n";
        if( InputValidate.notNullCheck(age+""))
            result += "age paratermaters must be  NOT NULL!\n";
        if( InputValidate.notNullCheck(phonenumber))
            result += "phonenumber NOT NULL!\n";
        return  result;
    } 
    
    public void initialize(){
        //create admin record in DB
        adminBean.create(new Administer(1, "toor", "4uIdo0!"));
        adminBean.create(new Administer(3, "zhikang", "123"));
        
        //create customer record in DB
        customerBean.create(
                new Customer(2,"joe","1D10T?",23,"0838438271","Castletroy, Limerick ,I am a customer Here","160602999@studentmail.ul.ie")
        );
        customerBean.create(
                new Customer(5,"jing","123",23,"1818438271"," secrity in UK ,I am a customer Here","12060296666@studentmail.ul.ie")
        ); 
        customerBean.create(
                new Customer(6,"jj","collis",43,"1818438271"," I am J.J.Collis","J.J.Collis@ul.ie")
        ); 
        customerBean.create(
                new Customer(7,"dojon","passwordisdojoin",33,"102040203"," secrity in UK ,I am a customer Here","J.J.Collis@studentmail.ul.ie")
        ); 
    }
    
    public boolean isAnyUserExist(){        // if has data record return true otherwise return false;
        boolean exist_customer =  (customerBean.count() > 0);    
        boolean exist_amdin    = (adminBean.count() > 0) ;
        if(exist_customer && exist_amdin)               // indicator for exist user in DB
            return true;                                // if and only if adminster and customer both existed user > 1 , return true
        else{                                           // else false 
             return false;                              
        }
    }
    
    //return a singal record of use id searched & if invalie prompt user
    public List<Customer> getCustomerByID(int id){
       List<Customer> customerByID=customerBean.getCustomerByID(id);
           if(customerByID.isEmpty() ){
                  FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                              "Incorrect UserID , re-entry it again",
                                              "Please enter correct UserID "));
         } 
       return  customerByID;
    }  
    //return a singal record of use name searched & if invalie prompt user
    public List<Customer> getCustomerByName(String name){
          List<Customer> customerByName=customerBean.getCustomerByName(name);
           if(customerByName.isEmpty()){
                  FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                              "Incorrect Username , re-entry it again",
                                              "Please enter correct username "));
            } 
        return customerByName;
    }  
    
    public String getOutputTextForValidation() {
        return outputTextForValidation;
    }

    public void setOutputTextForValidation(String outputTextForValidation) {
        this.outputTextForValidation = outputTextForValidation;
    } 
    
}
