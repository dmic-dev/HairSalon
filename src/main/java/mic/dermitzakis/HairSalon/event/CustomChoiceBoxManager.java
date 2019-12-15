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
import lombok.NoArgsConstructor;
import mic.dermitzakis.HairSalon.controller.DayOverviewController;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.dto.Row;

/**
 *
 * @author mderm
 */
@NoArgsConstructor
@Data
@Component
public class CustomChoiceBoxManager{
    @Autowired
    private ApplicationContext springContext;
    @Autowired
    private RowEventPublisher rowEventPublisher;
    
    public CustomChoiceBox getChoiceBoxWithProperties(Row row) {
        ObservableList<AppointmentStatus> statusList = 
                FXCollections.observableArrayList(AppointmentStatus.COMPLETED,AppointmentStatus.CANCELED,AppointmentStatus.PENDING);
        ObservableList<AppointmentStatus> emptyStatusList = 
                FXCollections.observableArrayList(AppointmentStatus.EMPTY);
        CustomChoiceBox customChoiceBox;
        if (row.getAppointmentStatus() == AppointmentStatus.EMPTY) // works perfectly
            customChoiceBox = springContext.getBean(CustomChoiceBox.class, emptyStatusList);
        else customChoiceBox = springContext.getBean(CustomChoiceBox.class, statusList);

        customChoiceBox.setIdentity(row.getIdentity());
        customChoiceBox.setAppointmentId(row.getAppointmentId());
        customChoiceBox.getSelectionModel().select(row.getAppointmentStatus().ordinal());
        rowEventPublisher.registerObserver(customChoiceBox);
        
        return customChoiceBox;
    }
    
}