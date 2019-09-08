package entity;

import entity.Cart;
import entity.Ordering;
import entity.Producttype;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-01T00:13:57")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, Integer> quantity;
    public static volatile SingularAttribute<Product, Integer> productID;
    public static volatile SingularAttribute<Product, Integer> size;
    public static volatile ListAttribute<Product, Ordering> orderingList;
    public static volatile SingularAttribute<Product, Float> price;
    public static volatile SingularAttribute<Product, String> productDescribe;
    public static volatile SingularAttribute<Product, Producttype> typeID;
    public static volatile SingularAttribute<Product, String> productName;
    public static volatile ListAttribute<Product, Cart> cartList;

}