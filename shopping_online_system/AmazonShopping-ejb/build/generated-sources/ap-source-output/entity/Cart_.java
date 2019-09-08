package entity;

import entity.CartPK;
import entity.Customer;
import entity.Product;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-01T00:13:57")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Integer> productQuantity;
    public static volatile SingularAttribute<Cart, Product> product;
    public static volatile SingularAttribute<Cart, CartPK> cartPK;
    public static volatile SingularAttribute<Cart, Date> addDate;
    public static volatile SingularAttribute<Cart, Customer> customer;

}