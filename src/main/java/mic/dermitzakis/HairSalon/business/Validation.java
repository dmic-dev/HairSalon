/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.business;

import mic.dermitzakis.HairSalon.model.Contact;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class Validation /*implements Validator*/{

    
    public static boolean checkAppointmentInput(Appointment appointment){
        return false;
    }
    
    public static boolean checkContactInput(Contact contact){
        String firstName = contact.getFirstName();
        return !(firstName == null || firstName.isBlank());
    }
    
}
