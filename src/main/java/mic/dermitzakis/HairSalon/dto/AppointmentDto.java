/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.NoArgsConstructor;
import mic.dermitzakis.HairSalon.controller.DayOverviewController;
import mic.dermitzakis.HairSalon.event.CustomChoiceBoxManager;
import mic.dermitzakis.HairSalon.event.CustomLabelManager;
import mic.dermitzakis.HairSalon.event.CustomChoiceBox;
import mic.dermitzakis.HairSalon.event.CustomLabel;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
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
public class AppointmentDto{
    @Autowired
    ApplicationContext springContext;
    
    private List<Appointment> appointments;
    private LocalTime time;
    
    private Label timeLabel;
    private VBox namesVbox;
    private VBox operationsVbox;
    private VBox notesVbox;
    private VBox statusVbox;

    public AppointmentDto() {
        initializeFields();
    }

    public void insertRow(Appointment appointment){
        // if empty row ...
        if (((CustomLabel)namesVbox.getChildrenUnmodifiable().get(0)).getText().equals("")) {
            initializeFields();
            insertValues(appointment);
        } else 
            insertValues(appointment);
    }

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
//        namesVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.NAME));// new Label("Name")
//        operationsVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.OPERATIONS));// new Label("Operations")
//        notesVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.NOTES));//  new Label("Notes")
//        statusVbox.getChildren().set(pos, getChoiceBoxWithProperties(newRow(appointment)));//new Label("ChoiceBox") //getChoiceBoxWithProperties(newRow(appointment))
//    }
//
    private Row newRow(Appointment appointment){
        RowManager rowDtoManager = springContext.getBean(RowManager.class);
        return rowDtoManager.createRow(appointment);
    }
    
    private CustomLabel getLabelWithProperties(Row row, CustomLabelManager.ColumnType columnType){
        CustomLabelManager labelManager = springContext.getBean(CustomLabelManager.class);
        labelManager.setColumnType(columnType);
        return labelManager.getLabelWithProperties(row);
    }

    private CustomChoiceBox getChoiceBoxWithProperties(Row row){
        CustomChoiceBoxManager choiceBoxManager = springContext.getBean(CustomChoiceBoxManager.class);
        return choiceBoxManager.getChoiceBoxWithProperties(row);
    }

    private void initializeFields() {
        appointments = new ArrayList<>();
        statusVbox = new VBox();
        notesVbox = new VBox();
        operationsVbox = new VBox();
        namesVbox = new VBox();
    }

    private void insertValues(Appointment appointment) {
        appointments.add(appointment);
        Row row = newRow(appointment);// checked; row.AppointmentStatus == AppointmentStatus.EMPTY
        namesVbox.getChildren().add(getLabelWithProperties(row, CustomLabelManager.ColumnType.NAME));// new Label("Name")
        operationsVbox.getChildren().add(getLabelWithProperties(row, CustomLabelManager.ColumnType.OPERATIONS));// new Label("Operations")
        notesVbox.getChildren().add(getLabelWithProperties(row, CustomLabelManager.ColumnType.NOTES));//  new Label("Notes")
        if (appointment.getStatus().equals(AppointmentStatus.EMPTY.toString()))
            statusVbox.getChildren().add(getLabelWithProperties(row, CustomLabelManager.ColumnType.STATUS));//new Label("ChoiceBox") //getChoiceBoxWithProperties(row)
        else 
            statusVbox.getChildren().add(getChoiceBoxWithProperties(row));
    }

}
