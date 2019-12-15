/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import mic.dermitzakis.HairSalon.controller.AppointmentOverviewController;
import mic.dermitzakis.HairSalon.controller.MainController;
import mic.dermitzakis.HairSalon.controller.TableviewStateDetails;
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

public class CustomLabel extends Label implements RowEventObserver, EventHandler<MouseEvent>{
    
    private long appointmentId = 0;
    
    private boolean empty = true;
    private UUID identity;
    private UUID selectedItem;
    private UUID focusedItem;
    private AppointmentStatus appointmentStatus;
    
    @Autowired
    private ApplicationContext springContext;
    
    public CustomLabel() {
        super();
        setEventHandler();
    }
    
    public CustomLabel(String text) {
        super(text);
        setEventHandler();
    }
    
    @Override
    public void update(UUID selectedItem, UUID focusedItem, AppointmentStatus appointmentStatus) {
        this.selectedItem = selectedItem;
        this.focusedItem = focusedItem;
        if (identity == focusedItem) this.appointmentStatus = appointmentStatus;
        setStyle(getLabelStyle());
    }
 
    private void setEventHandler(){
        setOnMouseEntered(this);
        setOnMouseExited(this);
        setOnMouseClicked(this);
    }
    
    // do not touch  !!!!
    public void setAppointmentStatus(AppointmentStatus appointmentStatus){
        this.appointmentStatus = appointmentStatus;
        setStyle(getLabelStyle());
    }
    
    private String setTextFill(){
        switch (appointmentStatus){
            case COMPLETED : return "-fx-text-fill: green;";
            case CANCELED  : return "-fx-text-fill: red;";
            case PENDING   : return "-fx-text-fill: black;";
            case EMPTY     : return "";
            default : return "-fx-text-fill: black;";
        }
    }
    
    private String setBackground(){
        if (identity == selectedItem) return "-fx-background-color: lightblue;";
        if (identity == focusedItem) return "-fx-background-color: orange;";
//        if (empty == true) return "-fx-background-color: orange;";
        return "";
    }
    
    public String getLabelStyle(){
        return setTextFill() + setBackground();
    }

    @Override
    public void handle(MouseEvent event) {
        RowEventPublisher rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        if (event.getEventType() ==  MouseEvent.MOUSE_ENTERED){
            if (event.getSource().getClass() == CustomLabel.class){
                CustomLabel focusedLabel = ((CustomLabel)event.getSource());
                rowEventPublisher.setRowInformation(selectedItem, focusedLabel.getIdentity(), focusedLabel.getAppointmentStatus());
            }    
        } else 
        if (event.getEventType() ==  MouseEvent.MOUSE_EXITED){
            if (event.getSource().getClass() == CustomLabel.class){
                CustomLabel unfocusedLabel = (CustomLabel)event.getSource();
                rowEventPublisher.setRowInformation(selectedItem, focusedItem, unfocusedLabel.getAppointmentStatus());
            }    
        } else 
        if (event.getEventType() ==  MouseEvent.MOUSE_CLICKED){
            if (event.getSource().getClass() == CustomLabel.class){
                CustomLabel selectedLabel = (CustomLabel)event.getSource();
                rowEventPublisher.setRowInformation(selectedLabel.getIdentity(), focusedItem, selectedLabel.getAppointmentStatus());
            }
        }
    }

}

