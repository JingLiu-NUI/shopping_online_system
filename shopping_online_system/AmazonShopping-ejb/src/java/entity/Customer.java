/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.Transient;
/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({ 
    @NamedQuery(name = "Customer.getCustomerID", query = "SELECT u.id FROM Customer u WHERE u.username = :username AND u.password = :password")
    , @NamedQuery(name = "Customer.validate", query = "SELECT COUNT(c) FROM Customer c WHERE c.username = :username AND c.password = :password")
    , @NamedQuery(name = "Customer.isCustomerExists", query = "SELECT COUNT(c) FROM Customer c WHERE c.username = :username")
    , @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id")
    , @NamedQuery(name = "Customer.findByUsername", query = "SELECT c FROM Customer c WHERE c.username = :username")
    , @NamedQuery(name = "Customer.findByPassword", query = "SELECT c FROM Customer c WHERE c.password = :password")
    , @NamedQuery(name = "Customer.findByAge", query = "SELECT c FROM Customer c WHERE c.age = :age")
    , @NamedQuery(name = "Customer.findByPhoneNumber", query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Age")
    private int age;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 19)
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Lob
    @Size(max = 65535)
    @Column(name = "UserMSG")
    private String userMSG;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Address> addressList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<OrderRecord> orderRecordList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Cart> cartList;
    
    @Transient
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
    }
  public Customer(Integer id, String username, String password, int age, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public Customer(Integer id, String username, String password, int age, String phoneNumber, String userMSG, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userMSG = userMSG;
        this.email = email;
    }

    public Customer(Customer customer) {
        this.id = customer.id;
        this.username = customer.username;
        this.password = customer.password;
        this.age = customer.age;
        this.phoneNumber = customer.phoneNumber;
        this.userMSG = customer.userMSG;
        this.email = customer.email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserMSG() {
        return userMSG;
    }

    public void setUserMSG(String userMSG) {
        this.userMSG = userMSG;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @XmlTransient
    public List<OrderRecord> getOrderRecordList() {
        return orderRecordList;
    }

    public void setOrderRecordList(List<OrderRecord> orderRecordList) {
        this.orderRecordList = orderRecordList;
    }

    @XmlTransient
    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + id + " ]";
    }
    
}
