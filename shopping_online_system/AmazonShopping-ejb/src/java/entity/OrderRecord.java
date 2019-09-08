/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "order_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderRecord.findAll", query = "SELECT o FROM OrderRecord o")
    , @NamedQuery(name = "OrderRecord.findByOrderID", query = "SELECT o FROM OrderRecord o WHERE o.orderID = :orderID")
    , @NamedQuery(name = "OrderRecord.findByTotalPrice", query = "SELECT o FROM OrderRecord o WHERE o.totalPrice = :totalPrice")
    , @NamedQuery(name = "OrderRecord.findByOrderDate", query = "SELECT o FROM OrderRecord o WHERE o.orderDate = :orderDate")
    , @NamedQuery(name = "OrderRecord.findByStatus", query = "SELECT o FROM OrderRecord o WHERE o.status = :status")
    , @NamedQuery(name = "OrderRecord.findByAddress", query = "SELECT o FROM OrderRecord o WHERE o.address = :address")
    , @NamedQuery(name = "OrderRecord.findByName",query ="SELECT o FROM OrderRecord o WHERE o.username.username = :username")
    , @NamedQuery(name = "OrderRecord.findMaxId",query = "SELECT o.orderID FROM OrderRecord o")})
public class OrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Order_ID")
    private Integer orderID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total_Price")
    private double totalPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Order_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private boolean status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderRecord")
    private List<Ordering> orderingList;
    @JoinColumn(name = "Username", referencedColumnName = "Username")
    @ManyToOne(optional = false)
    private Customer username;

    public OrderRecord() {
    }

    public OrderRecord(Integer orderID) {
        this.orderID = orderID;
    }

    public OrderRecord(Integer orderID, double totalPrice, Date orderDate, boolean status, String address) {
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public List<Ordering> getOrderingList() {
        return orderingList;
    }

    public void setOrderingList(List<Ordering> orderingList) {
        this.orderingList = orderingList;
    }

    public Customer getUsername() {
        return username;
    }

    public void setUsername(Customer username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderRecord)) {
            return false;
        }
        OrderRecord other = (OrderRecord) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderRecord[ orderID=" + orderID + " ]";
    }
    
}
