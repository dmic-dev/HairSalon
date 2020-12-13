/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
@Scope("prototype")
public class DayTableRow{
    private long appointmentId;
    
    private UUID identity;

    private LocalTime time;
    private String name;
    private String operations;
    private String notes;
    private AppointmentStatus appointmentStatus;

}
