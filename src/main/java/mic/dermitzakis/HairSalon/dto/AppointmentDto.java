/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.NoArgsConstructor;
import mic.dermitzakis.HairSalon.controller.DayOverviewController;
import mic.dermitzakis.HairSalon.event.ChoiceBoxManager;
import mic.dermitzakis.HairSalon.event.LabelManager;
import mic.dermitzakis.HairSalon.event.MyChoiceBox;
import mic.dermitzakis.HairSalon.event.MyLabel;
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
    
    private boolean empty = true;
    private LocalTime time;
    
    private Label timeLabel;
    private VBox namesVbox = new VBox();
    private VBox operationsVbox = new VBox();
    private VBox notesVbox = new VBox();
    private VBox statusVbox = new VBox();
 
    public void insertAppointmentToDto(Appointment appointment){
        setEmpty(false);
        getStatusVbox().getChildren().add(getChoiceBoxWithProperties(newRowDataItem(appointment)));//new Label("ChoiceBox")
        getNamesVbox().getChildren().add(getLabelWithProperties(newRowDataItem(appointment), LabelManager.ColumnType.NAME));// new Label("Name")
        getOperationsVbox().getChildren().add(getLabelWithProperties(newRowDataItem(appointment), LabelManager.ColumnType.OPERATIONS));// new Label("Operations")
        getNotesVbox().getChildren().add(getLabelWithProperties(newRowDataItem(appointment), LabelManager.ColumnType.NOTES));//  new Label("Notes")
    }

    private RowDto newRowDataItem(Appointment appointment){
        RowDtoManager rowDtoManager = springContext.getBean(RowDtoManager.class);
        return rowDtoManager.getRowDto(appointment);
    }
    
    private MyLabel getLabelWithProperties(RowDto rowData, LabelManager.ColumnType columnType){
        LabelManager labelManager = springContext.getBean(LabelManager.class);
        labelManager.setColumnType(columnType);
        return (MyLabel)labelManager.getLabelWithProperties(rowData);
    }

    private MyChoiceBox getChoiceBoxWithProperties(RowDto rowData){
        ChoiceBoxManager choiceBoxWithProperties = springContext.getBean(ChoiceBoxManager.class, this);
        return (MyChoiceBox)choiceBoxWithProperties.getChoiceBoxWithProperties(rowData);
    }
    
}
