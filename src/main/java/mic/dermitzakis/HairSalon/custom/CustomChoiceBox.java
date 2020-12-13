/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.custom;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.dto.RowDetailsDto;
import mic.dermitzakis.HairSalon.event.RowChangedEvent;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.CANCELED;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.COMPLETED;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.EMPTY;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.PENDING;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

/**
 *
 * @author mderm
 */
@Data
@EqualsAndHashCode(callSuper = true)

public class CustomChoiceBox extends ChoiceBox implements EventHandler<MouseEvent>{
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private ApplicationContext springContext;
    @Autowired
    private EventBus eventBus;
    @Autowired
    private RowChangedEvent rowChangedEvent;
    @Autowired
    private RowDetailsDto rowDetailsDto;
    
    private long appointmentId = 0;
    
    private UUID identity;
    private UUID selectedItem;
    private UUID focusedItem;
    private AppointmentStatus appointmentStatus;

    public static final ObservableList<AppointmentStatus> STATUS_LIST = 
                FXCollections.observableArrayList(COMPLETED, CANCELED, PENDING);
    public static final ObservableList<AppointmentStatus> EMPTY_STATUS_LIST = 
                FXCollections.observableArrayList(EMPTY);
        
    public CustomChoiceBox(ObservableList<AppointmentStatus> choiceBoxList) {
        super(choiceBoxList);
        setEventHandler();
    }
    
    public CustomChoiceBox (){
        super(STATUS_LIST);
        setEventHandler();
    }

    private void setEventHandler(){
        setOnMouseEntered(this);
        setOnMouseExited(this);
        setOnAction(this::onAction);
    }
 

    @Subscribe
    public void rowChangesListener(RowChangedEvent event){
        RowDetailsDto data = event.getData();
        this.selectedItem = data.getSelectedItem();
        this.focusedItem = data.getFocusedItem();
    }
    
    private void onAction(Event event){
        postChoiceBoxValueChangedEvent(event);
        showPaymentReportDialogue();
    }
    
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() ==  MouseEvent.MOUSE_ENTERED){
            if (event.getSource().getClass() == CustomChoiceBox.class){
                CustomChoiceBox focusedChoiceBox = (CustomChoiceBox)event.getSource();
                rowDetailsDto.setDetails(selectedItem, focusedChoiceBox.getIdentity(), (AppointmentStatus)focusedChoiceBox.getSelectionModel().getSelectedItem());
                rowChangedEvent.setData(rowDetailsDto);
                eventBus.post(rowChangedEvent);
            }
        } else
        if (event.getEventType() ==  MouseEvent.MOUSE_EXITED){
            if (event.getSource().getClass() == CustomChoiceBox.class){
                CustomChoiceBox unfocusedChoiceBox = (CustomChoiceBox)event.getSource();
                rowDetailsDto.setDetails(selectedItem, unfocusedChoiceBox.getIdentity(), (AppointmentStatus)unfocusedChoiceBox.getSelectionModel().getSelectedItem());
                rowChangedEvent.setData(rowDetailsDto);
                eventBus.post(rowChangedEvent);
            }
        }
    }

    private void postChoiceBoxValueChangedEvent(Event event) {
        CustomChoiceBox choiceBox = (CustomChoiceBox)event.getSource();
        rowDetailsDto.setDetails(selectedItem, choiceBox.getIdentity(), (AppointmentStatus)choiceBox.getSelectionModel().getSelectedItem());
        rowChangedEvent.setData(rowDetailsDto);
        eventBus.post(rowChangedEvent);
    }
    
    private void showPaymentReportDialogue() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
