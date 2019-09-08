package entity;

import entity.AddressPK;
import entity.Customer;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-01T00:13:57")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> address;
    public static volatile SingularAttribute<Address, AddressPK> addressPK;
    public static volatile SingularAttribute<Address, Customer> customer;

}