/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "ordering")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordering.findAll", query = "SELECT o FROM Ordering o")
    , @NamedQuery(name = "Ordering.findByOrderID", query = "SELECT o FROM Ordering o WHERE o.orderingPK.orderID = :orderID")
    , @NamedQuery(name = "Ordering.findByProductID", query = "SELECT o FROM Ordering o WHERE o.orderingPK.productID = :productID")
    , @NamedQuery(name = "Ordering.findByProductQuantity", query = "SELECT o FROM Ordering o WHERE o.productQuantity = :productQuantity")})
public class Ordering implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderingPK orderingPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Product_Quantity")
    private int productQuantity;
    @JoinColumn(name = "Order_ID", referencedColumnName = "Order_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private OrderRecord orderRecord;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public Ordering() {
    }

    public Ordering(OrderingPK orderingPK) {
        this.orderingPK = orderingPK;
    }

    public Ordering(OrderingPK orderingPK, int productQuantity) {
        this.orderingPK = orderingPK;
        this.productQuantity = productQuantity;
    }

    public Ordering(int orderID, int productID) {
        this.orderingPK = new OrderingPK(orderID, productID);
    }

    public OrderingPK getOrderingPK() {
        return orderingPK;
    }

    public void setOrderingPK(OrderingPK orderingPK) {
        this.orderingPK = orderingPK;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public OrderRecord getOrderRecord() {
        return orderRecord;
    }

    public void setOrderRecord(OrderRecord orderRecord) {
        this.orderRecord = orderRecord;
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
        hash += (orderingPK != null ? orderingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordering)) {
            return false;
        }
        Ordering other = (Ordering) object;
        if ((this.orderingPK == null && other.orderingPK != null) || (this.orderingPK != null && !this.orderingPK.equals(other.orderingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ordering[ orderingPK=" + orderingPK + " ]";
    }
    
}
