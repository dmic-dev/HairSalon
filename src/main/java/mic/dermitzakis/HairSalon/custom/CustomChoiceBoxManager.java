/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.custom;

import com.google.common.eventbus.EventBus;
import lombok.Data;
import static mic.dermitzakis.HairSalon.custom.CustomChoiceBox.EMPTY_STATUS_LIST;
import static mic.dermitzakis.HairSalon.custom.CustomChoiceBox.STATUS_LIST;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.dto.DayTableRow;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mderm
 */

@Data
@Component
public class CustomChoiceBoxManager{
    private final ApplicationContext springContext;
    private final EventBus eventBus;

    @Autowired
    public CustomChoiceBoxManager(ApplicationContext springContext, EventBus eventBus) {
        this.springContext = springContext;
        this.eventBus = eventBus;
    }

    public CustomChoiceBox getChoiceBoxWithProperties(DayTableRow row) {
        CustomChoiceBox customChoiceBox;
        if (row.getAppointmentStatus() == AppointmentStatus.EMPTY) // works perfectly
            customChoiceBox = springContext.getBean(CustomChoiceBox.class, EMPTY_STATUS_LIST);
        else customChoiceBox = springContext.getBean(CustomChoiceBox.class, STATUS_LIST);

        customChoiceBox.setIdentity(row.getIdentity());
        customChoiceBox.setAppointmentId(row.getAppointmentId());
        customChoiceBox.getSelectionModel().select(row.getAppointmentStatus().ordinal());
        
        eventBus.register(customChoiceBox);
        
        return customChoiceBox;
    }
    
}
