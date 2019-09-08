/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;
 
import java.util.regex.Pattern;

 
public class InputValidate { 
    
    public static boolean notNullCheck(String string){
        if( string == null || string.length() > 0 )
            return false;
        else
            return true;
    }
    
    public static boolean isFullDigital(String string) { 
        if( !notNullCheck(string) )
            return false;
        return Pattern.matches("[0-9]{1,}", string) ;
    }
    
    public static boolean isFloatNumber(String string) { 
        if( !notNullCheck(string) )
            return false;
        return Pattern.matches("[0-9.]*", string) ;
    }
    public static boolean isFullAlph(String string) {
        if( !notNullCheck(string) )
            return false;
        return Pattern.matches("[a-zA-Z]{1,}", string) ; 
    }
}
