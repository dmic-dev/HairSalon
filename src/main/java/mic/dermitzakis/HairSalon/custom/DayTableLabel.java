/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.custom;

import com.google.common.eventbus.Subscribe;
import javafx.geometry.Insets;
import lombok.Getter;
import lombok.Setter;
import mic.dermitzakis.HairSalon.dto.DayTableRow;
import mic.dermitzakis.HairSalon.event.RowChangedEvent;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.CANCELED;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.COMPLETED;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.PENDING;
import org.springframework.context.event.EventListener;

/**
 *
 * @author mderm
 */
//@Data
public class DayTableLabel extends AbstractLabel {
    @Getter
    private AppointmentStatus appointmentStatus;
    @Getter @Setter
    private long appointmentId = 0;
    
    public static enum ColumnType {
        STATUS,
        NAME,
        OPERATIONS,
        NOTES
    }
    
    private ColumnType columnType;
    
    public DayTableLabel() {
        super();
    }
    
    public DayTableLabel(String text) {
        super(text);
    }
    
    @Subscribe
    @Override
    public void rowChangesListener(RowChangedEvent event){
        super.rowChangesListener(event);
        if (rowId == focusedItem) appointmentStatus = event.getData().getStatus();
        setStyle(getLabelStyle());
    }
    
    /* Important!!! DO NOT TOUCH  !!!! */
    public void setAppointmentStatus(AppointmentStatus appointmentStatus){
        this.appointmentStatus = appointmentStatus;
        setStyle(getLabelStyle());
    }
    //***************//
    
    @Override
    protected String setTextFill(){
        super.setTextFill();
        if (appointmentStatus != null)
            switch (appointmentStatus){
                case COMPLETED : return "-fx-text-fill: green;";
                case CANCELED  : return "-fx-text-fill: red;";
                case PENDING   : return "-fx-text-fill: black;";
                case EMPTY     : return "";
                default : return "-fx-text-fill: black;";
            }
        return "-fx-text-fill: black;";
    }
    
    public void setColumnType(ColumnType columnType){
        this.columnType = columnType;
    }

    public void setLabelProperties(DayTableRow row) {
        switch (columnType){
            case STATUS :       { setText(""); break; }
            case NAME :         { setText(row.getName()); break; }
            case OPERATIONS :   { setText(row.getOperations()); break; }
            case NOTES :        { setText(row.getNotes()); break; }
        }
        setRowId(row.getIdentity());
        setAppointmentId(row.getAppointmentId());
        setAppointmentStatus(row.getAppointmentStatus());
        prefWidthProperty().bind(widthProperty().add(800.0));
        setPadding(new Insets(4,0,4,3));
//        label.setStyle("-fx-text-fill: red; -fx-background-color: lightblue; -fx-border-color: blue; -fx-border-radius: 7;");
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (int) (this.appointmentId ^ (this.appointmentId >>> 32));
        return hash;
    }

    
}

