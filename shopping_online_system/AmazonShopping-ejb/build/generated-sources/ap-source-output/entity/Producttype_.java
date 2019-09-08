package entity;

import entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-01T00:13:57")
@StaticMetamodel(Producttype.class)
public class Producttype_ { 

    public static volatile SingularAttribute<Producttype, String> typeText;
    public static volatile SingularAttribute<Producttype, Integer> typeID;
    public static volatile ListAttribute<Producttype, Product> productList;

}