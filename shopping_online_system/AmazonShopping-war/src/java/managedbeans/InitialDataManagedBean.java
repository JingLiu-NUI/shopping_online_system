package managedbeans;
import beans.AddressFacadeLocal;
import beans.CustomerFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import entity.*;
@Named(value = "initialDataManagedBean")
@SessionScoped
public class InitialDataManagedBean implements Serializable {
    @EJB
    private CustomerFacadeLocal customerFacade;
    private int count=0;
    public InitialDataManagedBean() {
        
    }
    public String InitialAllEntityClasses()
    {       
        return "haba";
    }
}
