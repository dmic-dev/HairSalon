/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import mic.dermitzakis.HairSalon.controller.DayOverviewController;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.dto.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class CustomLabelManager{
    private final ApplicationContext springContext;
    private final DayOverviewController appointmentViewController;
    private final RowEventPublisher rowEventPublisher;
    
    public enum ColumnType {
        STATUS,
        NAME,
        OPERATIONS,
        NOTES
    }
    
    private ColumnType columnType;
    
    @Autowired
    public CustomLabelManager(ApplicationContext springContext, DayOverviewController appointmentViewController, RowEventPublisher rowEventPublisher) {
        this.springContext = springContext;
        this.appointmentViewController = appointmentViewController;
        this.rowEventPublisher = rowEventPublisher;
    }

    public void setColumnType(ColumnType columnType){
        this.columnType = columnType;
    }

    public CustomLabel getLabelWithProperties(Row row) {
        CustomLabel customLabel = springContext.getBean(CustomLabel.class);
        TableColumn<AppointmentDto, VBox> nameColumn = appointmentViewController.getName_Column();
        switch (columnType){
            case STATUS :       { customLabel.setText(""); break; }
            case NAME :         { customLabel.setText(row.getName()); break; }
            case OPERATIONS :   { customLabel.setText(row.getOperations()); break; }
            case NOTES :        { customLabel.setText(row.getNotes()); break; }
        }
        customLabel.setIdentity(row.getIdentity());
        customLabel.setAppointmentId(row.getAppointmentId());
        customLabel.setAppointmentStatus(row.getAppointmentStatus());
        customLabel.prefWidthProperty().bind(nameColumn.maxWidthProperty());
        customLabel.setPadding(new Insets(4,0,4,3));
//        label.setStyle("-fx-text-fill: red; -fx-background-color: lightblue; -fx-border-color: blue; -fx-border-radius: 7;");
        rowEventPublisher.registerObserver(customLabel);
      
        return customLabel;
    }
    
}
