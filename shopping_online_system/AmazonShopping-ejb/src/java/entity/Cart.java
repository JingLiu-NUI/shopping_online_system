/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Transient;
/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "cart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c")
    , @NamedQuery(name = "Cart.findByUsername", query = "SELECT c FROM Cart c WHERE c.cartPK.username = :username")
    , @NamedQuery(name = "Cart.findByProductID", query = "SELECT c FROM Cart c WHERE c.cartPK.productID = :productID")
    , @NamedQuery(name = "Cart.findByProductQuantity", query = "SELECT c FROM Cart c WHERE c.productQuantity = :productQuantity")
    , @NamedQuery(name = "Cart.findByAddDate", query = "SELECT c FROM Cart c WHERE c.addDate = :addDate")})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CartPK cartPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Product_Quantity")
    private int productQuantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Add_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addDate;
    @JoinColumn(name = "Username", referencedColumnName = "Username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Customer customer;
    @JoinColumn(name = "Product_ID", referencedColumnName = "ProductID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @Transient
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public Cart() {
    }

    public Cart(CartPK cartPK) {
        this.cartPK = cartPK;
    }

    public Cart(CartPK cartPK, int productQuantity, Date addDate) {
        this.cartPK = cartPK;
        this.productQuantity = productQuantity;
        this.addDate = addDate;
    }
    
    public Cart(CartPK cartPK, int productQuantity, Date addDate, Product product) {
        this.cartPK = cartPK;
        this.productQuantity = productQuantity;
        this.addDate = addDate;
        this.product = product;
    }

    public Cart(String username, int productID) {
        this.cartPK = new CartPK(username, productID);
    }

    public CartPK getCartPK() {
        return cartPK;
    }

    public void setCartPK(CartPK cartPK) {
        this.cartPK = cartPK;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartPK != null ? cartPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cartPK == null && other.cartPK != null) || (this.cartPK != null && !this.cartPK.equals(other.cartPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cart[ cartPK=" + cartPK + " ]";
    }
    
}
