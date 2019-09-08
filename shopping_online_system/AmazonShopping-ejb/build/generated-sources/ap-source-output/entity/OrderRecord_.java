package entity;

import entity.Customer;
import entity.Ordering;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-01T00:13:57")
@StaticMetamodel(OrderRecord.class)
public class OrderRecord_ { 

    public static volatile SingularAttribute<OrderRecord, String> address;
    public static volatile SingularAttribute<OrderRecord, Integer> orderID;
    public static volatile SingularAttribute<OrderRecord, Double> totalPrice;
    public static volatile ListAttribute<OrderRecord, Ordering> orderingList;
    public static volatile SingularAttribute<OrderRecord, Date> orderDate;
    public static volatile SingularAttribute<OrderRecord, Boolean> status;
    public static volatile SingularAttribute<OrderRecord, Customer> username;

}