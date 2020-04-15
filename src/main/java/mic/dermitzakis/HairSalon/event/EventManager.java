/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.Contact.Gender;
import mic.dermitzakis.HairSalon.repository.AppointmentRepository;
import org.springframework.context.ApplicationContext;
import mic.dermitzakis.HairSalon.controller.DayOverviewController;
import mic.dermitzakis.HairSalon.dto.AppointmentViewDetailsDto;
import mic.dermitzakis.HairSalon.model.Picture;
import mic.dermitzakis.HairSalon.repository.DefaultImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class EventManager {
    private final ApplicationContext springContext;
    private final DayOverviewController dayOverviewController;
    private final DefaultImages defaultImages;
    private final DateTimeFormatter dtf;
    
    @Autowired
    public EventManager(ApplicationContext springContext) {
        this.springContext = springContext;
        this.dayOverviewController = springContext.getBean(DayOverviewController.class);
        this.defaultImages = springContext.getBean(DefaultImages.class);
        this.dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    }
    
    public EventRowSelected getEventRowChanged(){
        AppointmentViewDetailsDto details = springContext.getBean(AppointmentViewDetailsDto.class);
        
        EventRowSelected eventRowSelected = springContext.getBean(EventRowSelected.class);
        AppointmentDto appointmentDto = 
                dayOverviewController.getAppointmentTable().getSelectionModel().getSelectedItem();
        if (!appointmentDto.getNamesVbox().getChildrenUnmodifiable().isEmpty()) {
            ObservableList<Node> names = appointmentDto.getNamesVbox().getChildrenUnmodifiable();
            CustomLabel nameLabel =  (CustomLabel)names.stream()
                .filter((t) -> {return ((CustomLabel)t).getIdentity() == dayOverviewController.getSelectedItem();})
                .findFirst().orElse(new CustomLabel());
            long appointmentId = nameLabel.getAppointmentId();
            if (appointmentId != 0) {
                AppointmentRepository appointmentRepository = springContext.getBean(AppointmentRepository.class);
                Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);// !!!
                Contact contact = appointment.getContact();
                details.setAppCreator("getEmployee().getFullName()"); //appointment.getEmployee().getFullName()
                details.setAppDateCreated(appointment.getTimeCreated().format(dtf));
                details.setName(contact.getFullName());
                details.setId(String.valueOf(contact.getContactId()));
                details.setGender(contact.getGender().toString());
                if (contact.getPicture() != null) {
                    Image image = Picture.toImage(contact.getPicture().getImage());
                    details.setPicture(image);// next move // 
                } else 
                    switch (contact.getGender()) {
                        case MALE: {details.setPicture(defaultImages.getImage(Gender.MALE)); break;}
                        case FEMALE: {details.setPicture(defaultImages.getImage(Gender.FEMALE)); break;}
                        case UNSPECIFIED: {details.setPicture(defaultImages.getImage(Gender.UNSPECIFIED)); break;}
                        default: details.setPicture(defaultImages.getImage(Gender.UNSPECIFIED));
                    }
                details.setDob(contact.getDateOfBirth().format(dtf));
                details.setDateCreated(contact.getDateCreated().format(dtf));
                details.setLastModified(contact.getLastModified().format(dtf));
            } else details.init();
        }
        eventRowSelected.setDetails(details);
        return eventRowSelected;
    }
    
}
