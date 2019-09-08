/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lenovo
 */
@Embeddable
public class OrderingPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Order_ID")
    private int orderID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProductID")
    private int productID;

    public OrderingPK() {
    }

    public OrderingPK(int orderID, int productID) {
        this.orderID = orderID;
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderID;
        hash += (int) productID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderingPK)) {
            return false;
        }
        OrderingPK other = (OrderingPK) object;
        if (this.orderID != other.orderID) {
            return false;
        }
        if (this.productID != other.productID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderingPK[ orderID=" + orderID + ", productID=" + productID + " ]";
    }
    
}
