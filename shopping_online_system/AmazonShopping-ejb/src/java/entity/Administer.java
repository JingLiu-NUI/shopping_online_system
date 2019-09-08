/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Transient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "administer")
@XmlRootElement
@NamedQueries({   @NamedQuery(name = "Administer.getAdminID", query = "SELECT a.id FROM Administer a WHERE a.username = :username AND a.password = :password")
    , @NamedQuery(name = "Administer.validate", query = "SELECT COUNT(a) FROM Administer a WHERE a.username = :username AND a.password = :password")
    , @NamedQuery(name = "Administer.findAll", query = "SELECT a FROM Administer a")
    , @NamedQuery(name = "Administer.findById", query = "SELECT a FROM Administer a WHERE a.id = :id")
    , @NamedQuery(name = "Administer.findByUsername", query = "SELECT a FROM Administer a WHERE a.username = :username")
    , @NamedQuery(name = "Administer.findByPassword", query = "SELECT a FROM Administer a WHERE a.password = :password")
    , @NamedQuery(name = "Administer.isAdministerExists", query = "SELECT COUNT(a) FROM Administer a WHERE a.username = :username")})
public class Administer implements Serializable {

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
    @Transient
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public Administer() {
    }

    public Administer(Integer id) {
        this.id = id;
    }

    public Administer(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administer)) {
            return false;
        }
        Administer other = (Administer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Administer[ id=" + id + " ]";
    }
    
}
