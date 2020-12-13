/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.LocalTime;
import java.util.Objects;
import javafx.scene.layout.VBox;
import lombok.Data;
import mic.dermitzakis.HairSalon.custom.CustomChoiceBoxManager;
import mic.dermitzakis.HairSalon.custom.CustomChoiceBox;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.custom.DayTableLabel.ColumnType;
import mic.dermitzakis.HairSalon.custom.TimeLabel;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.mapper.DayTableDetailsMapper;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
//@NoArgsConstructor
@Data
@Component
@Scope("prototype")
public class DayOverviewDto{
    @Autowired
    ApplicationContext springContext;
    
    private TimeLabel timeLabel;
    private VBox namesVbox;
    private VBox statusVbox;
    private VBox operationsVbox;
    private VBox notesVbox;

    public DayOverviewDto() {
        initializeFields();
    }

    public void insertRow(Appointment appointment){
        // if empty row ...
        if (((DayTableLabel)namesVbox.getChildrenUnmodifiable().get(0)).getText().equals("")) 
            initializeFields();
        insertValues(appointment);
    }

//    public void insertRow(DayOverviewDto dto, int rowPos) {
//        this.time = dto.getTime();
//        this.timeLabel = dto.getTimeLabel();
//        this.namesVbox.getChildren().add((DayTableLabel)dto.namesVbox.getChildrenUnmodifiable().get(rowPos));
//        this.operationsVbox.getChildren().add((DayTableLabel)dto.operationsVbox.getChildrenUnmodifiable().get(rowPos));
//        this.notesVbox.getChildren().add((DayTableLabel)dto.notesVbox.getChildrenUnmodifiable().get(rowPos));
//        this.statusVbox.getChildren().add((CustomChoiceBox)dto.statusVbox.getChildrenUnmodifiable().get(rowPos));
//    }
//
    public void removeRow(Appointment appointment) { // TO BE Implemented
    
    // // Use stack method
    // {
    //      create new fields
    //      Copy elements except row to be removed to new row list
    //      set fields to new fields;
    // }
    }    
//    public void removeRow() {
//        namesVbox.getChildren().remove(0);
//        operationsVbox.getChildren().remove(0);
//        notesVbox.getChildren().remove(0);
//        statusVbox.getChildren().remove(0);
//        namesVbox.setVisible(false);
//        operationsVbox.setVisible(false);
//        notesVbox.setVisible(false);
//        statusVbox.setVisible(false);
//        namesVbox = new VBox();
//        operationsVbox = new VBox();
//        notesVbox = new VBox();
//        statusVbox = new VBox();


    public void insertEmptyRow(){
        Appointment appointment = springContext.getBean(Appointment.class);
        appointment.setAppointmentId(0);
        appointment.setStatus(AppointmentStatus.EMPTY.toString()); // !!!
        insertValues(appointment);
    }
//    public void replaceRow(int pos, Appointment appointment) {
//        namesVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), DayAppointmentLabelManager.ColumnType.NAME));// new Label("Name")
//        operationsVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), DayAppointmentLabelManager.ColumnType.OPERATIONS));// new Label("Operations")
//        notesVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), DayAppointmentLabelManager.ColumnType.NOTES));//  new Label("Notes")
//        statusVbox.getChildren().set(pos, getChoiceBoxWithProperties(newRow(appointment)));//new Label("ChoiceBox") //getChoiceBoxWithProperties(newRow(appointment))
//    }
//
    private DayTableRow newRow(Appointment appointment){
        DayTableDetailsMapper tableDetails = springContext.getBean(DayTableDetailsMapper.class);
        return tableDetails.extract(appointment);
    }
    
    private DayTableLabel getLabelWithProperties(DayTableRow row, ColumnType columnType){
        DayTableLabel label = springContext.getBean(DayTableLabel.class);
        label.setColumnType(columnType);
        label.setLabelProperties(row);
        return label;
    }

    private CustomChoiceBox getChoiceBoxWithProperties(DayTableRow row){
        CustomChoiceBoxManager choiceBoxManager = springContext.getBean(CustomChoiceBoxManager.class);
        return choiceBoxManager.getChoiceBoxWithProperties(row);
    }

    private void initializeFields() {
        statusVbox = new VBox();
        notesVbox = new VBox();
        operationsVbox = new VBox();
        namesVbox = new VBox();
    }

    private void insertValues(Appointment appointment) {
//        appointments.add(appointment);
        DayTableRow row = newRow(appointment);// checked; row.AppointmentStatus == AppointmentStatus.EMPTY
        namesVbox.getChildren().add(getLabelWithProperties(row, ColumnType.NAME));// new Label("Name")
        operationsVbox.getChildren().add(getLabelWithProperties(row, ColumnType.OPERATIONS));// new Label("Operations")
        notesVbox.getChildren().add(getLabelWithProperties(row, ColumnType.NOTES));//  new Label("Notes")
        if (appointment.getStatus().equals(AppointmentStatus.EMPTY.toString()))
            statusVbox.getChildren().add(getLabelWithProperties(row, ColumnType.STATUS));//new Label("ChoiceBox") //getChoiceBoxWithProperties(row)
        else 
            statusVbox.getChildren().add(getChoiceBoxWithProperties(row));
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.timeLabel);
        return hash;
    }


}
