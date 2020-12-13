/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.mapper;

import java.util.List;
import mic.dermitzakis.HairSalon.dto.ContactSideDetailsDto;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.Gender;
import mic.dermitzakis.HairSalon.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class ContactSideDetailsMapper implements Mapper<ContactSideDetailsDto>{
    @Autowired
    private ApplicationContext springContext;
    
    @Override
    public ContactSideDetailsDto extract(Object object) {
        ContactSideDetailsDto contactSideDetailsDto = springContext.getBean(ContactSideDetailsDto.class);
        Contact contact = (Contact)object;
        
        contactSideDetailsDto.setId(contact.getContactId());
        contactSideDetailsDto.setGender(contact.getGender());
        contactSideDetailsDto.setFirstName(contact.getFirstName());
        contactSideDetailsDto.setLastName(contact.getLastName());
          List<Phone> phoneList = contact.getPhones();
          String string = "**********";
//            if (phoneList != null)
//             if (phoneList.getContactDto(0) != null)
//                string = phoneList.getContactDto(0).getNumber();
        contactSideDetailsDto.setPhone(string); //   contact.getPhones().getContactDto(0).getNumber();
        contactSideDetailsDto.setNotes(contact.getNotes());
        contactSideDetailsDto.setDob(contact.getDateOfBirth());
        contactSideDetailsDto.setDateCreated(contact.getDateCreated());
        contactSideDetailsDto.setLastModified(contact.getLastModifiedDate());
        contactSideDetailsDto.setPicture(contact.getImage());
        
        return contactSideDetailsDto;
    }
    
}
