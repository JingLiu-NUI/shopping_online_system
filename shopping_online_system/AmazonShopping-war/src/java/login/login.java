package login;
 
import beans.CustomerFacadeLocal;
import beans.AdministerFacadeLocal; 
import java.io.Serializable; 
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;  
import javax.servlet.http.HttpSession;  
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import security.*;

 
@Named(value ="loginBean")
@RequestScoped
public class login implements Serializable {
    
    //making ejb customer & administer session bean object
    @EJB
    private CustomerFacadeLocal customerSB;
    @EJB
    private AdministerFacadeLocal administerSB;

     private String token;//token
 
        
        //gettter for token
        public String getToken() {
            return token;
        }
    
        public login() 
        {
        }
    
        //required attributes
        private String password;//passwword
	private String message; //message
	private String username;//user 
        
       
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        } 
        
        //return userID by passing username and password
        public long userID()
        {
            return customerSB.getCustomerID(username, password);
        } 
        
        public String validateUsernamePassword() 
        {
            String direction = "";  
            //get the current seesion of user
            HttpSession session = SessionBean.getSession();        //if validate http session set to current session 
             int id ;
            boolean valid = customerSB.isValideCustomer(username, password);
            if (valid) { 
                    customerSB.setCustomerName(username);                       //setting username in newUserBean ejb
                    customerSB.setCustomerName(password);                       //setting password in newUserBean ejb
                    id = customerSB.getCustomerID(username, password);
                    session.setAttribute("type", "customer");
                    direction = "home_customer";                                //redirect to correct user page 
            } 
            else if(administerSB.isValideAdmin(username, password))
            {
                    administerSB.setAdminName(username);                      //setting username in newUserBean ejb
                    id = administerSB.getAdministerID(username, password);    // set id attribute into session
                    session.setAttribute("type", "admin");
                    direction = "home_admin";                                //redirect to correct administer page 
            }
            else {//message if validate failed
                    FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                              "Incorrect Passowrd OR Username , re-entry it again",
                                              "Please enter correct username and Password"));
                    return "login";
            }

            //CSRF
            post.generateToken();                                     //generets a token 
            this.token=post.getToken();                               //gets the generated token
            //set session attribute usename and password after encrypted
            session.setAttribute("username", username);
            session.setAttribute("password", DeEnCode.encode(password));//encode the password and store into session
            session.setAttribute("id", id);
            session.setAttribute("token",post.getToken());            //set the token in seesion 
            session.setMaxInactiveInterval(20*60);
            //END _ CSRF
            
           return direction;
	}
 
	//logout event, invalidate session
        public String logout() {
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "login";
	}
}
