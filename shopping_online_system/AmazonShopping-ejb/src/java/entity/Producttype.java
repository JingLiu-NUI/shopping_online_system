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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "producttype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producttype.findAll", query = "SELECT p FROM Producttype p")
    , @NamedQuery(name = "Producttype.findByTypeID", query = "SELECT p FROM Producttype p WHERE p.typeID = :typeID")
    , @NamedQuery(name = "Producttype.findByTypeText", query = "SELECT p FROM Producttype p WHERE p.typeText = :typeText")})
public class Producttype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TypeID")
    private Integer typeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TypeText")
    private String typeText;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeID")
    private List<Product> productList;
    
    public static final String PRODUCTTYE_ARR[] = {"food","Meat","Vagetable","Snack","Seasoning","Drink Powder","Drink" ,"Powder"};
 

    public Producttype() {
    }

    public Producttype(Integer typeID) {
        this.typeID = typeID;
    }

    public Producttype(Integer typeID, String typeText) {
        this.typeID = typeID;
        this.typeText = typeText;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeID != null ? typeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producttype)) {
            return false;
        }
        Producttype other = (Producttype) object;
        if ((this.typeID == null && other.typeID != null) || (this.typeID != null && !this.typeID.equals(other.typeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Producttype[ typeID=" + typeID + " ]";
    }
    
}
