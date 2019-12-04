/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import lombok.Data;
import mic.dermitzakis.HairSalon.controller.DayOverviewController;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.dto.RowDto;

/**
 *
 * @author mderm
 */
@Data
@Component
public class ChoiceBoxManager{
    @Autowired
    private ApplicationContext springContext;
    @Autowired
    private DayOverviewController appointmentViewController;
    @Autowired
    private RowEventPublisher rowEventPublisher;
    
    private AppointmentDto appointmentDto;

    public ChoiceBoxManager(AppointmentDto appointmentDto) {
        this.appointmentDto = appointmentDto;
    }
            

    public MyChoiceBox getChoiceBoxWithProperties(RowDto rowDto) {
        final ObservableList<AppointmentStatus> statusList = 
                FXCollections.observableArrayList(AppointmentStatus.COMPLETED,AppointmentStatus.CANCELED,AppointmentStatus.PENDING);
        MyChoiceBox myChoiceBox = springContext.getBean(MyChoiceBox.class, statusList);
        
        myChoiceBox.setIdentity(rowDto.getAppointmentId());
        myChoiceBox.setValue(rowDto.getAppointmentStatus());
        rowEventPublisher.registerObserver(myChoiceBox);
        return myChoiceBox;
    }
    
}
