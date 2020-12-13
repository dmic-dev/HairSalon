/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.custom;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.util.UUID;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.dto.RowDetailsDto;
import mic.dermitzakis.HairSalon.event.EventFactory;
import mic.dermitzakis.HairSalon.event.RowChangedEvent;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mderm
 * @param <C>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AbstractLabel extends Label implements EventHandler<MouseEvent>{
    @Autowired
    private EventBus eventBus;
    @Autowired
    private RowDetailsDto rowDetailsDto;
    @Autowired
    private RowChangedEvent rowChangedEvent;
    @Autowired
    private EventFactory eventFactory;

    
    protected UUID rowId;
    protected UUID selectedItem;
    protected UUID focusedItem;

    @Autowired
    public AbstractLabel() {
        super();
        setEventHandler();
    }
    
    protected AbstractLabel(String text){
        super(text);
        setEventHandler();
    }

    @PostConstruct
    private void init(){
        eventBus.register(this);
    }
    
    @Subscribe
    public void rowChangesListener(RowChangedEvent event){
        RowDetailsDto data = event.getData();
        selectedItem = data.getSelectedItem();
        focusedItem = data.getFocusedItem();
        setStyle(getLabelStyle());
    }
    
    protected final void  setEventHandler(){
        setOnMouseEntered(this);
        setOnMouseExited(this);
        setOnMouseClicked(this);
    }
    
    protected String setTextFill(){
        return "-fx-text-fill: black;";
    }
    
    protected String setBackground(){
        if (rowId == selectedItem) return "-fx-background-color: rgb(224, 246, 255);";
        if (rowId == focusedItem) return "-fx-background-color: papayawhip;";
        return "";
    }
    
    protected String getLabelStyle(){
        return setTextFill() + setBackground();
    }

    protected AppointmentStatus getAppointmentStatus(){
        return AppointmentStatus.PENDING;
    }
    
    public void postSideDetails(){
        eventBus.post(eventFactory.createEvent(this.getClass().getSimpleName()+"Event"));
    }

    private Class<? extends AbstractLabel> subClass(){
        return this.getClass();
    }

    // Το rowInformation έχει ενημερωθεί - αρχικοποιηθεί απο το notifyTableRows του controller
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() ==  MouseEvent.MOUSE_ENTERED){
            if (event.getSource().getClass() == subClass()){
                var focusedLabel = subClass().cast(event.getSource());
                postRowDetails(selectedItem, focusedLabel.getRowId(), focusedLabel.getAppointmentStatus());
            }    
        } else 
        if (event.getEventType() ==  MouseEvent.MOUSE_EXITED){
            if (event.getSource().getClass() == subClass()){
                var unfocusedLabel = subClass().cast(event.getSource());
                postRowDetails(selectedItem, focusedItem, unfocusedLabel.getAppointmentStatus());
            }    
        } else 
        if (event.getEventType() ==  MouseEvent.MOUSE_CLICKED){
            if (event.getSource().getClass() == subClass()){
                var selectedLabel = subClass().cast(event.getSource());
                postRowDetails(selectedLabel.getRowId(), focusedItem, selectedLabel.getAppointmentStatus());
                
                postSideDetails();
            }
        }
    }
    
    private void postRowDetails(UUID selectedItem, UUID focusedItem, AppointmentStatus appointmentStatus){
        rowDetailsDto.setDetails(selectedItem, focusedItem, appointmentStatus);
        rowChangedEvent.setData(rowDetailsDto);
        eventBus.post(rowChangedEvent);
    }            
    

}
