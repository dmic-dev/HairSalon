/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import mic.dermitzakis.HairSalon.event.ChoiceBoxManager;
import mic.dermitzakis.HairSalon.event.MyChoiceBox;
import mic.dermitzakis.HairSalon.event.MyLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.event.LabelManager;
import mic.dermitzakis.HairSalon.event.LabelManager.ColumnType;

/**
 *
 * @author mderm
 */
@Component
public class AppointmentDtoManager{
    @Autowired
    private ApplicationContext springContext;

    public AppointmentDto getAppointmentDto(RowDto rowData) {
        AppointmentDto appointmentDto = springContext.getBean(AppointmentDto.class);
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        Label lbl = new Label(dateTimeFormatter.format(rowData.getTime()));
        lbl.setPadding(new Insets(4, 0, 4, 2));
        
//        for (RowDto rowDataItem : rowDataItemList){
            appointmentDto.setTime(rowData.getTime());
            appointmentDto.setTimeLabel(lbl);
            ChoiceBoxManager choiceBoxWithProperties = springContext.getBean(ChoiceBoxManager.class, appointmentDto);
            LabelManager labelWithProperties = springContext.getBean(LabelManager.class);

            appointmentDto.getStatusVbox().getChildren()
                    .add((MyChoiceBox)choiceBoxWithProperties.getChoiceBoxWithProperties(rowData));
            labelWithProperties.setColumnType(ColumnType.NAME);
            appointmentDto.getNamesVbox().getChildren()
                    .add((MyLabel)labelWithProperties.getLabelWithProperties(rowData));
            labelWithProperties.setColumnType(ColumnType.OPERATIONS);
            appointmentDto.getOperationsVbox().getChildren()
                    .add((MyLabel)labelWithProperties.getLabelWithProperties(rowData));
            labelWithProperties.setColumnType(ColumnType.NOTES);
            appointmentDto.getNotesVbox().getChildren()
                    .add((MyLabel)labelWithProperties.getLabelWithProperties(rowData));
//        }

        return appointmentDto;
    }
    
}
