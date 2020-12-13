/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class EventFactory {
    private final ApplicationContext springContext;

    @Autowired
    public EventFactory(ApplicationContext springContext) {
        this.springContext = springContext;
    }
    
    public Event createEvent(String event) {
        var appointmentDetailsEvent = springContext.getBean(AppointmentSideDetailsEvent.class);
        var contactDetailsEvent = springContext.getBean(ContactSideDetailsEvent.class);
        switch (event) {
            case "DayTableLabelEvent": {
                appointmentDetailsEvent.newEvent();
                return appointmentDetailsEvent;
            }
            case "ContactTableLabelEvent": {
                contactDetailsEvent.newEvent();
                return contactDetailsEvent;
            }
            // JDD : Jesus Driven Development
            default : throw new IllegalArgumentException("No such event: "+ event); 
        }
    }
}
