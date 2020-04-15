/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.util.List;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.Contact.Gender;
import mic.dermitzakis.HairSalon.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class ContactDtoManager{
    @Autowired
    private ApplicationContext springContext;
    
    public ContactDto getContactDto(Contact contact){
        ContactDto contactDto = springContext.getBean(ContactDto.class);
        contactDto.setId(contact.getContactId());
        contactDto.setGender(contact.getGender());
        contactDto.setFirstName(contact.getFirstName());
        contactDto.setLastName(contact.getLastName());
          List<Phone> phoneList = contact.getPhones();
          String string = "**********";
//            if (phoneList != null)
//             if (phoneList.getContactDto(0) != null)
//                string = phoneList.getContactDto(0).getNumber();
        contactDto.setPhone(string); //   contact.getPhones().getContactDto(0).getNumber();
        contactDto.setNotes(contact.getNotes());
        contactDto.setDob(contact.getDateOfBirth());
        contactDto.setDateCreated(contact.getDateCreated());
        contactDto.setLastModified(contact.getLastModified());
        contactDto.setPicture(contact.getPicture());
        
        return contactDto;
    }
}
