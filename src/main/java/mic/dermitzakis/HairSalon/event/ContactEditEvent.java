/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import lombok.Data;
import mic.dermitzakis.HairSalon.model.Contact;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
@Data
public class ContactEditEvent {
    private Contact contact;

    public ContactEditEvent(Contact contact) {
        this.contact = contact;
    }
    
}
