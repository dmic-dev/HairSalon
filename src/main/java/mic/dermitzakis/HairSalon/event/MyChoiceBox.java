/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

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

public class MyChoiceBox extends ChoiceBox implements RowEventObserver, EventHandler<MouseEvent>{
     
    private long identity;
    private long selectedItem;
    private long focusedItem;
    private AppointmentStatus appointmentStatus;

    @Autowired
    private ApplicationContext springContext;
   
    public MyChoiceBox(ObservableList<AppointmentStatus> choiceBoxList) {
        super(choiceBoxList);
        setEventHandler();
    }

    private void setEventHandler(){
        setOnMouseEntered(this);
        setOnMouseExited(this);
        setOnAction(this::onAction);
    }
 

    @Override
    public void update(long selectedItem, long focusedItem, AppointmentStatus status) {
        this.selectedItem = selectedItem;
        this.focusedItem = focusedItem;
    }
    private void onAction(Event t){
        RowEventPublisher rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        MyChoiceBox focusedChoiceBox = (MyChoiceBox)t.getSource();
        rowEventPublisher.setRowInformation(selectedItem, focusedChoiceBox.getIdentity(), (AppointmentStatus)focusedChoiceBox.getSelectionModel().getSelectedItem());
    }
    
    @Override
    public void handle(MouseEvent event) {
        RowEventPublisher rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        if (event.getEventType() ==  MouseEvent.MOUSE_ENTERED){
            if (event.getSource().getClass() == MyChoiceBox.class){
                MyChoiceBox focusedChoiceBox = (MyChoiceBox)event.getSource();
                rowEventPublisher.setRowInformation(selectedItem, focusedChoiceBox.getIdentity(), (AppointmentStatus)focusedChoiceBox.getSelectionModel().getSelectedItem());
            }
        } else
        if (event.getEventType() ==  MouseEvent.MOUSE_EXITED){
            if (event.getSource().getClass() == MyChoiceBox.class){
                MyChoiceBox unfocusedChoiceBox = (MyChoiceBox)event.getSource();
                rowEventPublisher.setRowInformation(selectedItem, unfocusedChoiceBox.getIdentity(),
                        (AppointmentStatus)unfocusedChoiceBox.getSelectionModel().getSelectedItem());
            }
        }
    }
}
