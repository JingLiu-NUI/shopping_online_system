/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import beans.CartFacadeLocal; 
import java.util.*; 
import javax.faces.bean.*; 
import entity.*;
import beans.ProductFacadeLocal;
import beans.ProducttypeFacadeLocal;
import java.sql.SQLException;
import java.text.SimpleDateFormat; 
import javax.ejb.EJB;  
import java.util.Date; 
import java.text.ParseException; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import entity.Producttype;
import security.*; 




/**
 *
 * @author Natalija
 */

@ManagedBean(name = "productBean")
@SessionScoped
public class productBean   { 


    @Resource(mappedName = "jms/dest")
    private javax.jms.Queue dest;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    @EJB
    private  ProductFacadeLocal pro;
    
    @EJB
    private CartFacadeLocal cartSB; 
	
    private int productID ; 
    public String productName;
    private int typeID;
    private int quantity;
    private float price;
    private float totalPrice;
    private int size;
    private String productMSG;
    private int quantitytoCart;
    private List<Product> productsAll; 
    private List<Product> ProductbyID;
    private String UserName;
    private int changedQuantity; 
    
    private boolean canEdit ;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }  
     
   public void removeProduct(Product product){
           pro.remove(product);
           sendJMSMessageToDest("You have removed a product: " + product.getProductName());
     }
   
    public void editProduct(){
        this.setCanEdit(true);
        System.err.print(this.isCanEdit());
    }
    public void saveProducts(){
        this.canEdit = false;
        for(Product p : productsAll){
            pro.edit(p);
        }
    }
    
    public productBean(){  }

    public productBean( int quantitytoCart,Integer productID, String productName, int typeID, int quantity, float price, int size, String productMSG) throws SQLException {
        this.productID = productID;
        this.productName = productName;
        this.typeID = typeID;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.productMSG = productMSG; 
    } 
	
    public List<Product> findProductAll(){
        productsAll = pro.findAll();
        return productsAll;
    } 
    
    public List<Product > getProductByID(int productID) {
          List<Product> ProductbyId = pro.getProductByID(productID);
          if(ProductbyId.isEmpty( )){
            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                              "Incorrect  productID, re-entry it again",
                                              "Please enter correct productID"));
           } 
          return ProductbyId;
     }
     
    public List<Product > getProductByName(String productname) { 
          List<Product> ProductbyName = pro.getProductsByName(productname);
          if(ProductbyName.isEmpty()){
              FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                              "Incorrect Name, re-entry it again",
                                              "Please enter correct Name"));
             } 
          return ProductbyName;
    } 
    
    public void addNewProduct(){ 
        if( !pro.getProductByID(this.productID).isEmpty()){
               FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                              "productID :"+productID+" already exists in database.",
                              "re-creat record with same primary key"));
          return;
        } 
        // JDNI Lookup for get Producttype EJB
        InitialContext context = null;
        Producttype producttype = null;
        ProducttypeFacadeLocal productTypeSB = null ; 
        try {
            context = new InitialContext();
            productTypeSB = (ProducttypeFacadeLocal) context.lookup("java:global/AmazonShopping/AmazonShopping-ejb/ProducttypeFacade!beans.ProducttypeFacadeLocal");
        } catch (NamingException ex) {
            Logger.getLogger(productBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(productTypeSB == null ){
            System.err.println("cannot find the producttype EJB in file productBean.java");
            return;
        }  
        Product product=new Product();
        producttype = productTypeSB.find(typeID); 
        if(producttype == null){    //if it is a new product type
            producttype = new Producttype();
            producttype.setTypeID(typeID);
            producttype.setTypeText( Producttype.PRODUCTTYE_ARR[ (int) (Math.random() * Producttype.PRODUCTTYE_ARR.length)]);   // randomly assign the product type describe
            productTypeSB.create(producttype);
        }
        Integer maxID = pro.FindmaxId();
        Integer ProductID =  maxID + 1;
        product.setProductID(ProductID);
        product.setProductName(productName); 
        product.setTypeID(producttype); 
        product.setQuantity(0);
        product.setSize(size);
        product.setProductDescribe(productMSG);
        product.setPrice(price);
        pro.create(product); 
        sendJMSMessageToDest("You hava a new product: " + productName );
    }
 
    public String checkIfAddedIntoDB(){
        if(productName == null || "".equals(productName))
            return "Please add new product";
        List<Product> pList = pro.getProductsByName(productName);
        if(pList.isEmpty()){
            return "  is not exist in database now";
        }
        else{
            return " is exist in the database now, please click 'go to the update' for updating the quantity. ";
        }
    }

    public void increasequantity(Product product) throws SQLException{
        int qutity_cart    =  product.getQuantitytoCart();
        int qutity_product = product.getQuantity();
        if(qutity_cart <= qutity_product){ 
            Product new_product = new Product(product);        
            qutity_cart++;
            new_product.setQuantity( qutity_product - 1 );
            new_product.setQuantitytoCart( qutity_cart );
            new_product.setTotalPrice(qutity_cart * product.getPrice());
            pro.remove(product); 
            System.err.println("test for product: "+new_product.toString());
            pro.create(new_product);
        } 
    }  
	
    public void decreasequantity(Product product) throws SQLException{ 
        int qutity_cart = product.getQuantitytoCart();
        if(qutity_cart > 0){ 
            Product new_product = new Product(product);        
            qutity_cart--;
            new_product.setQuantity( product.getQuantity() + 1 );
            new_product.setQuantitytoCart( qutity_cart );
            new_product.setTotalPrice(qutity_cart * product.getPrice());
            pro.edit(product); 
        } 
    }
    
    // username useless
    public void addProductIntoCart(int product_ID, int quantitytoCart,Product product) throws ParseException{
        
        if(pro.count() <= 0)
            return;
        
       Date currentTime = new Date();
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       String dateString = formatter.format(currentTime); 
       Date date = formatter.parse(dateString);
        

        // get session
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true); 
        String username = (String)session.getAttribute("username");
        //get up session attribute here    
 
        System.err.println("username="+ username );
        System.err.println("password after encode is :"+ (String)session.getAttribute("password") );
       
        List<Cart> cartList = cartSB.findByName(username);
        Cart cartPrevious = null;
        if( !cartList.isEmpty()){
           for(Cart cart: cartList){
               if(cart.getCartPK().getProductID() == product_ID){
                    cartPrevious = cart;
                    break;
               }
           }
       }
        
       if(cartPrevious == null) {
           //no data find in the database
           System.err.println("username = $"+username +"$");
           System.err.println("product id = $"+product_ID+"@"); 
           System.err.println("quantitytoCart = $"+quantitytoCart+"@"); 
           System.err.println("date= $"+date+"@"); 
           
           CartPK cartPK = new CartPK(username, product_ID);
           //Cart cart=new Cart();
           Cart cart = new Cart();
           cart.setCartPK(cartPK);
           cart.setProductQuantity(quantitytoCart);
           cart.setAddDate(date);
           cart.setProduct(product);
           cartSB.create(cart); 
       } 
    }  
    
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getProductMSG() {
        return productMSG;
    }

    public void setProductMSG(String productMSG) {
        this.productMSG = productMSG;
    }

    public int getQuantitytoCart() {
        return quantitytoCart;
    }

    public void setQuantitytoCart(int quantitytoCart) {
        this.quantitytoCart = quantitytoCart;
    }

    public List<Product> getProductsAll() {
        return productsAll;
    }

    public void setProductsAll(List<Product> productsAll) {
        this.productsAll = productsAll;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public int getChangedQuantity() {
        return changedQuantity;
    }
    
       public void setChangedQuantity(int changedQuantity) {
        this.changedQuantity = changedQuantity;
    }

    private void sendJMSMessageToDest(String messageData) {
        context.createProducer().send(dest, messageData);
    }

} 