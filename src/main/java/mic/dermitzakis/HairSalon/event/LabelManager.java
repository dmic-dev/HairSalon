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
import mic.dermitzakis.HairSalon.dto.RowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class LabelManager{
    private final ApplicationContext springContext;
    private final DayOverviewController appointmentViewController;
    private final RowEventPublisher rowEventPublisher;
    
    public enum ColumnType {
        NAME,
        OPERATIONS,
        NOTES
    }
    
    private ColumnType columnType;
    
    @Autowired
    public LabelManager(ApplicationContext springContext, DayOverviewController appointmentViewController, RowEventPublisher rowEventPublisher) {
        this.springContext = springContext;
        this.appointmentViewController = appointmentViewController;
        this.rowEventPublisher = rowEventPublisher;
    }

    public void setColumnType(ColumnType columnType){
        this.columnType = columnType;
    }

    public MyLabel getLabelWithProperties(RowDto rowData) {
        MyLabel myLabel = springContext.getBean(MyLabel.class);
        TableColumn<AppointmentDto, VBox> nameColumn = appointmentViewController.getName_Column();
        switch (columnType){
            case NAME :         { myLabel.setText(rowData.getName()); break; }
            case OPERATIONS :   { myLabel.setText(rowData.getOperations()); break; }
            case NOTES :        { myLabel.setText(rowData.getNotes()); break; }
        }
        myLabel.setIdentity(rowData.getAppointmentId());
        myLabel.setAppointmentStatus(rowData.getAppointmentStatus());
        myLabel.prefWidthProperty().bind(nameColumn.maxWidthProperty());
        myLabel.setPadding(new Insets(4,0,4,3));
//        label.setStyle("-fx-text-fill: red; -fx-background-color: lightblue; -fx-border-color: blue; -fx-border-radius: 7;");
        rowEventPublisher.registerObserver(myLabel);
      
        return myLabel;
    }
    
}
