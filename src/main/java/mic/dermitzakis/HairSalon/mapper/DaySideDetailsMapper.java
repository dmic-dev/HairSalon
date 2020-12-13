/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.mapper;

import java.time.format.DateTimeFormatter;
import mic.dermitzakis.HairSalon.dto.AppointmentSideDetailsDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DaySideDetailsMapper implements Mapper<AppointmentSideDetailsDto> {
    private final ApplicationContext springContext;
    private final DateTimeFormatter dtf;

    @Autowired
    public DaySideDetailsMapper(ApplicationContext springContext) {
        this.springContext = springContext;
        this.dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
    
    @Override
    public AppointmentSideDetailsDto extract(Object object) {
        AppointmentSideDetailsDto details = springContext.getBean(AppointmentSideDetailsDto.class);
        Appointment appointment = (Appointment)object;
        Contact contact = appointment.getContact();
    //                details.setAppCreator(appointment.getEmployee().getFullName());
        details.setAppCreator("");
        details.setAppDateCreated(appointment.getTimeCreated().format(dtf));
        details.setName(contact.getFullName());
        details.setId(String.valueOf(contact.getContactId()));
        details.setGender(contact.getGender().toString());
        details.setPicture(contact.getImage());
        details.setDob(contact.getDateOfBirth().format(dtf));
        details.setDateCreated(contact.getDateCreated().format(dtf));
        details.setLastModified(contact.getLastModifiedDate().format(dtf));
        
        return details;
    }
    
}
