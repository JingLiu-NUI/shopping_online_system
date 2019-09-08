package entity;

import entity.Address;
import entity.Cart;
import entity.OrderRecord;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-01T00:13:57")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, String> password;
    public static volatile ListAttribute<Customer, OrderRecord> orderRecordList;
    public static volatile SingularAttribute<Customer, String> phoneNumber;
    public static volatile ListAttribute<Customer, Address> addressList;
    public static volatile SingularAttribute<Customer, Integer> id;
    public static volatile SingularAttribute<Customer, String> userMSG;
    public static volatile SingularAttribute<Customer, Integer> age;
    public static volatile SingularAttribute<Customer, String> email;
    public static volatile ListAttribute<Customer, Cart> cartList;
    public static volatile SingularAttribute<Customer, String> username;

}