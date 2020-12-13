/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.util.UUID;
import lombok.Data;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
public class RowDetailsDto {
    private UUID selectedItem;
    private UUID focusedItem;
    private AppointmentStatus status; 

    public void setDetails(UUID selectedItem,  UUID focusedItem, AppointmentStatus status) {
        this.selectedItem = selectedItem;
        this.focusedItem = focusedItem;
        this.status = status;
    }
}
