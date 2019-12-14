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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@NoArgsConstructor
@Data
@Component
@Scope("prototype")
public class AppointmentDto{
    @Autowired
    ApplicationContext springContext;
    
    private LocalTime time;
    
    private Label timeLabel;
    private VBox namesVbox = new VBox();
    private VBox operationsVbox = new VBox();
    private VBox notesVbox = new VBox();
    private VBox statusVbox = new VBox();
 
    public void insertRow(Appointment appointment){
        statusVbox.getChildren().add(getChoiceBoxWithProperties(newRow(appointment)));//new Label("ChoiceBox") //getChoiceBoxWithProperties(newRow(appointment))
        namesVbox.getChildren().add(getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.NAME));// new Label("Name")
        operationsVbox.getChildren().add(getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.OPERATIONS));// new Label("Operations")
        notesVbox.getChildren().add(getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.NOTES));//  new Label("Notes")
    }

    public void replaceRow(int pos, Appointment appointment) {
        namesVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.NAME));// new Label("Name")
        operationsVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.OPERATIONS));// new Label("Operations")
        notesVbox.getChildren().set(pos, getLabelWithProperties(newRow(appointment), CustomLabelManager.ColumnType.NOTES));//  new Label("Notes")
        statusVbox.getChildren().set(pos, getChoiceBoxWithProperties(newRow(appointment)));//new Label("ChoiceBox") //getChoiceBoxWithProperties(newRow(appointment))
    }

    public void removeRow() {
        namesVbox.getChildren().remove(0);
        operationsVbox.getChildren().remove(0);
        notesVbox.getChildren().remove(0);
        statusVbox.getChildren().clear();
    }

    private Row newRow(Appointment appointment){
        RowManager rowDtoManager = springContext.getBean(RowManager.class);
        return rowDtoManager.getRow(appointment);
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
    
}
