/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.mapper;

import java.util.UUID;
import mic.dermitzakis.HairSalon.custom.ContactTableLabel;
import mic.dermitzakis.HairSalon.dto.ContactOverviewDto;
import mic.dermitzakis.HairSalon.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public final class ContactTableDetailsMapper implements Mapper<ContactOverviewDto>{
    private final ApplicationContext springContext;
    
    @Autowired
    public ContactTableDetailsMapper(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public final ContactOverviewDto extract(Object object) {
        final ContactOverviewDto dto = springContext.getBean(ContactOverviewDto.class);
        Contact contact = (Contact)object;
        UUID identity = UUID.randomUUID();
//        dto.setIdentity(identity); /// ??????????
        dto.getId().setContactId(contact.getContactId());
        setLabel(identity, dto.getId(), String.valueOf(contact.getContactId()));
        setLabel(identity, dto.getFirstName(), contact.getFirstName());
        setLabel(identity, dto.getLastName(), contact.getLastName());
        setLabel(identity, dto.getPhone(), "SomePhone");
        setLabel(identity, dto.getNotes(), "Some Notes .......");
        
        return dto;
    }
    
    private void setLabel(UUID identity, ContactTableLabel label, String text) {
        label.setText(text);
        label.setRowId(identity);
    }
}
