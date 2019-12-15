/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import mic.dermitzakis.HairSalon.model.Activity;
import mic.dermitzakis.HairSalon.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
@Scope("prototype")
public class Row{
    private long appointmentId;
    
    private UUID identity;

    private LocalTime time;
    private String name;
    private String operations;
    private String notes;
    private AppointmentStatus appointmentStatus;

}
