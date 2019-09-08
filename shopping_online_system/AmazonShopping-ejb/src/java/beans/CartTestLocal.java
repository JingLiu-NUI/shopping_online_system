/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Cart;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface CartTestLocal {
    void createNewCart(Cart cart);
}
