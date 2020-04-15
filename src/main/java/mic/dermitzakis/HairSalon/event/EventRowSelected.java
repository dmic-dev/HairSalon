/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import mic.dermitzakis.HairSalon.dto.AppointmentViewDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class EventRowSelected {
    private AppointmentViewDetailsDto details;
    
    @Autowired
    public EventRowSelected(AppointmentViewDetailsDto details){
        this.details = details;
    }
    
    public AppointmentViewDetailsDto getDetails(){
        return details;
    }
    
    public void setDetails(AppointmentViewDetailsDto details){
        this.details = details;
    }
    
}
