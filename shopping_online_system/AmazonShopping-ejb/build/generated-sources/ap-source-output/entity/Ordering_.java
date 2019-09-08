package entity;

import entity.OrderRecord;
import entity.OrderingPK;
import entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-01T00:13:57")
@StaticMetamodel(Ordering.class)
public class Ordering_ { 

    public static volatile SingularAttribute<Ordering, Integer> productQuantity;
    public static volatile SingularAttribute<Ordering, Product> product;
    public static volatile SingularAttribute<Ordering, OrderRecord> orderRecord;
    public static volatile SingularAttribute<Ordering, OrderingPK> orderingPK;

}