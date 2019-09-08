/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;
 
import beans.CartFacadeLocal;
import entity.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.EJB; 
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import login.SessionBean; 

@Named(value = "showCart")
@SessionScoped
public class ShowCart implements Serializable {

      @EJB
    private CartFacadeLocal cartFacade;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private double totalprice;
    private List<Cart> mycart;
    private List<Cart> checkedList;
    private int count=0;
    
    public double getTotalprice() {
        totalprice=0;
        int size = mycart.size();
        for(int i = 0; i <size ; i++ )
        {
            Cart cart = (Cart)mycart.get(i);  
            if(cart.isChecked())
                totalprice += showProductTotalPrice(cart);
        }
        return totalprice;
    }
    
    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

    public List<Cart> getMycart() {
       
        return mycart;
    }

    public void setMycart(List<Cart> mycart) {
        this.mycart = mycart;
    }

    public CartFacadeLocal getCartFacade() {
        return cartFacade;
    }

    public void setCartFacade(CartFacadeLocal cartFacade) {
        this.cartFacade = cartFacade;
    }
    public ShowCart() {
        mycart=new ArrayList<>();
    }
  
    
    public void showAllCartByName(String name)
    {
       
        this.mycart=cartFacade.findByName(name);      
    }
    
    public String showProductName(Cart cart)
    {
        if(cart==null)
            return "";
        
       return cart.getProduct().getProductName();
    }
    
    public String MyCart()
    { 
        this.username = SessionBean.getUserName();
        showAllCartByName(username);
        return "ShoppingCartPage";
    }
    public double showProductPrice(Cart cart)
    {
        return cart.getProduct().getPrice();
    }
    
    public double showProductTotalPrice(Cart cart)
    {
        double unitPrice = cart.getProduct().getPrice();
        int quantity = cart.getProductQuantity(); 
        BigDecimal x=new BigDecimal(Double.toString(unitPrice*quantity));
        double totalPrice=x.doubleValue(); 
        return totalPrice; 
    }
    
    public void increaseQuantity(Cart cart)
    {
        int quantity = cart.getProductQuantity();
        if(quantity<cart.getProduct().getQuantity())
            quantity+=1;
         else{
             FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                              "I know your are rich , but there is no sooooo much product in the storage, man...",
                                              "The limitation for quanity"));
        }
        cart.setProductQuantity(quantity);
        cartFacade.edit(cart);
    }
    
    public void decreaseQuantity(Cart cart)
    {
        int quantity = cart.getProductQuantity();
        if(quantity>0)
            quantity-=1;
        else{
             FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                              "It is not possible that a qutity could be less than 0! ",
                                              "The limitation for quanity "));
        }
        cart.setProductQuantity(quantity);
         cartFacade.edit(cart); 
    }
    public List<Cart> getCheckedList()
    {    
        checkedList=new ArrayList<>();
        for(Cart item: mycart)
            if(item.isChecked())
                checkedList.add(item);
        return checkedList;
    }
    
      
}
