/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.controller.ContactOverviewController;
import mic.dermitzakis.HairSalon.dto.ContactOverviewDto;
import mic.dermitzakis.HairSalon.dto.ContactSideDetailsDto;
import mic.dermitzakis.HairSalon.mapper.ContactSideDetailsMapper;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
@Lazy
public final class ContactSideDetailsEvent implements Event{
    private final ApplicationContext springContext;
    private final ContactService contactService;
    private final ContactOverviewController contactOverviewController;
    private final ContactSideDetailsMapper contactSideDetails;
    private ContactSideDetailsDto data;
    
    @Autowired
    public ContactSideDetailsEvent(ApplicationContext springContext, ContactService contactService, ContactOverviewController contactOverviewController, ContactSideDetailsMapper contactSideDetails){
        this.springContext = springContext;
        this.contactService = contactService;
        this.contactOverviewController = contactOverviewController;
        this.contactSideDetails = contactSideDetails;
    }
    
    @Override
    public void newEvent() {
        var contactOverviewDto = getSelectedItemFromTable();
        long contactId = getSelectedItemId(contactOverviewDto);
        Contact contact = getContactFromRepository(contactId);
        ContactSideDetailsDto sideDetailsDto = extractSideDetails(contact);
        setContactDetailsForDispatch(sideDetailsDto);
    }

    private void setContactDetailsForDispatch(ContactSideDetailsDto sideDetailsDto) {
        this.setData(sideDetailsDto);
    }

    private ContactSideDetailsDto extractSideDetails(Contact contact) {
        return contactSideDetails.extract(contact);
    }

    private Contact getContactFromRepository(long contactId) {
        return contactService.findContactById(contactId).orElseThrow();
    }

    private long getSelectedItemId(ContactOverviewDto contactOverviewDto) {
        return contactOverviewDto.getContactId();
    }

    private ContactOverviewDto getSelectedItemFromTable() {
        return contactOverviewController.getContactTable().getSelectionModel().getSelectedItem();
    }
    
}
