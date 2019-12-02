/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import java.util.HashMap;
import java.util.Hashtable;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import mic.dermitzakis.HairSalon.controller.AppointmentOverviewController;
import mic.dermitzakis.HairSalon.controller.MainController;
import mic.dermitzakis.HairSalon.controller.MainController.CurrentView;
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

public class MyLabel extends Label implements RowEventObserver, EventHandler<MouseEvent>{
    private long identity;
    private long selectedItem;
    private long focusedItem;
    private AppointmentStatus appointmentStatus;
    
    @Autowired
    private ApplicationContext springContext;
    @Autowired
    private MainController mainController;
    
    public MyLabel() {
        super();
        setEventHandler();
    }
    
    public MyLabel(String text) {
        super(text);
        setEventHandler();
    }
    
    @Override
    public void update(long selectedItem, long focusedItem, AppointmentStatus appointmentStatus) {
        this.selectedItem = selectedItem;
        this.focusedItem = focusedItem;
        if (identity == focusedItem) this.appointmentStatus = appointmentStatus;
        setStyle(getLabelStyle());
        notifyTableStateChange();
    }
 
    private void setEventHandler(){
        setOnMouseEntered(this);
        setOnMouseExited(this);
        setOnMouseClicked(this);
    }
    
    private void notifyTableStateChange(){
        TableviewStateDetails stateDetails = mainController.getCurrentViewState();
        stateDetails.setSelectedItem(selectedItem);
        stateDetails.setFocusedItem(focusedItem);
        CurrentView key = mainController.getCurrentView();
        mainController.getStateDetails().replace(key, stateDetails);
    }
    // do not touch  !!!!
    public void setAppointmentStatus(AppointmentStatus appointmentStatus){
        this.appointmentStatus = appointmentStatus;
        setStyle(getLabelStyle());
    }
    
    private String setTextFill(){
        switch (appointmentStatus){
            case COMPLETED : return "-fx-text-fill: green;";
            case CANCELED  : return  "-fx-text-fill: red;";
            case PENDING   : return  "-fx-text-fill: black;";
            default : return "-fx-text-fill: black;";
        }
    }
    
    private String setBackground(){
        if (this.identity == selectedItem) return "-fx-background-color: lightblue;";
        if (this.identity == focusedItem) return "-fx-background-color: orange;";
        return "";
    }
    
    public String getLabelStyle(){
        return setTextFill()+setBackground();
    }

    private long getSelectedItem(){ // Bullshit
        return mainController.getCurrentViewState().getSelectedItem();
    }
    
    private long getFocusedItem(){
        return mainController.getCurrentViewState().getFocusedItem();
    }
    
    @Override
    public void handle(MouseEvent event) {
        RowEventPublisher rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        if (event.getEventType() ==  MouseEvent.MOUSE_ENTERED){
            if (event.getSource().getClass() == MyLabel.class){
                MyLabel focusedLabel = ((MyLabel)event.getSource());
                rowEventPublisher.setRowInformation(getSelectedItem(), focusedLabel.getIdentity(), focusedLabel.getAppointmentStatus());
            }    
        } else 
        if (event.getEventType() ==  MouseEvent.MOUSE_EXITED){
            if (event.getSource().getClass() == MyLabel.class){
                MyLabel unfocusedLabel = (MyLabel)event.getSource();
                rowEventPublisher.setRowInformation(getSelectedItem(), getFocusedItem(), unfocusedLabel.getAppointmentStatus());
            }    
        } else 
        if (event.getEventType() ==  MouseEvent.MOUSE_CLICKED){
            if (event.getSource().getClass() == MyLabel.class){
                MyLabel selectedLabel = (MyLabel)event.getSource();
                rowEventPublisher.setRowInformation(selectedLabel.getIdentity(), getFocusedItem(), selectedLabel.getAppointmentStatus());
            }
        }
    }

}

