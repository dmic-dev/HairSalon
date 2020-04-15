/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import java.util.UUID;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.controller.AppointmentOverviewController;
import mic.dermitzakis.HairSalon.controller.MainController;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;

/**
 *
 * @author mderm
 */
@Data
@EqualsAndHashCode(callSuper = true)

public class CustomChoiceBox extends ChoiceBox implements RowEventObserver, EventHandler<MouseEvent>{
    
    private long appointmentId = 0;
    
    private UUID identity;
    private UUID selectedItem;
    private UUID focusedItem;
    private AppointmentStatus appointmentStatus;

    @Autowired
    private ApplicationContext springContext;
   
    public CustomChoiceBox(ObservableList<AppointmentStatus> choiceBoxList) {
        super(choiceBoxList);
        setEventHandler();
    }

    private void setEventHandler(){
        setOnMouseEntered(this);
        setOnMouseExited(this);
        setOnAction(this::onAction);
    }
 

    @Override
    public void update(UUID selectedItem, UUID focusedItem, AppointmentStatus status) {
        this.selectedItem = selectedItem;
        this.focusedItem = focusedItem;
    }
    
    private void onAction(Event event){
        RowEventPublisher rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        CustomChoiceBox choiceBox = (CustomChoiceBox)event.getSource();
        rowEventPublisher.setRowInformation(selectedItem, choiceBox.getIdentity(), 
                (AppointmentStatus)choiceBox.getSelectionModel().getSelectedItem());
    }
    
    @Override
    public void handle(MouseEvent event) {
        RowEventPublisher rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        if (event.getEventType() ==  MouseEvent.MOUSE_ENTERED){
            if (event.getSource().getClass() == CustomChoiceBox.class){
                CustomChoiceBox focusedChoiceBox = (CustomChoiceBox)event.getSource();
                rowEventPublisher.setRowInformation(selectedItem, focusedChoiceBox.getIdentity(), 
                        (AppointmentStatus)focusedChoiceBox.getSelectionModel().getSelectedItem());
            }
        } else
        if (event.getEventType() ==  MouseEvent.MOUSE_EXITED){
            if (event.getSource().getClass() == CustomChoiceBox.class){
                CustomChoiceBox unfocusedChoiceBox = (CustomChoiceBox)event.getSource();
                rowEventPublisher.setRowInformation(selectedItem, unfocusedChoiceBox.getIdentity(),
                        (AppointmentStatus)unfocusedChoiceBox.getSelectionModel().getSelectedItem());
            }
        }
    }
}
