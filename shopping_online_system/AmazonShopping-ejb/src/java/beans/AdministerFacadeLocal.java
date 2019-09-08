/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Administer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface AdministerFacadeLocal {

    void create(Administer administer);

    void edit(Administer administer);

    void remove(Administer administer);

    Administer find(Object id);

    List<Administer> findAll();

    List<Administer> findRange(int[] range);

    int count();
    
    public int getAdministerID(String administername,String password);

    public boolean isAdministerExist(String username);
    
    public boolean isValideAdmin(String username, String password);
    
    public void setAdminName(String username);
    
}
