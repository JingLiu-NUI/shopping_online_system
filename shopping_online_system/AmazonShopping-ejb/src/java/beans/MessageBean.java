/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Lenovo
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/dest")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageBean implements MessageListener {
    
    public MessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        TextMessage tmsg = null;
        tmsg = (TextMessage)message;
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        TextMessage textMessage;
        textMessage = null;
        textMessage = (TextMessage)message;
        try{
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException ex) {
                Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            //MylogFile.log
            int limit = 1000000; //1 Mb
            fh = new FileHandler("D://LogRecord.log", limit, 1);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            try {
                logger.info(textMessage.getText());
            } catch (JMSException ex) {
                Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (SecurityException | IOException ex) {
                Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
    
    
}
