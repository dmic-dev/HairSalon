/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import mic.dermitzakis.HairSalon.event.CustomChoiceBoxManager;
import mic.dermitzakis.HairSalon.event.CustomChoiceBox;
import mic.dermitzakis.HairSalon.event.CustomLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.event.CustomLabelManager;
import mic.dermitzakis.HairSalon.event.CustomLabelManager.ColumnType;

/**
 *
 * @author mderm
 */
@Component
public class AppointmentDtoManager{
    @Autowired
    private ApplicationContext springContext;

    public AppointmentDto getAppointmentDto(Row rowData) {
        AppointmentDto appointmentDto = springContext.getBean(AppointmentDto.class);
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        Label lbl = new Label(dateTimeFormatter.format(rowData.getTime()));
        lbl.setPadding(new Insets(4, 0, 4, 2));
        
//        for (Row rowDataItem : rowDataItemList){
            appointmentDto.setTime(rowData.getTime());
            appointmentDto.setTimeLabel(lbl);
            CustomChoiceBoxManager customChoiceBoxManager = springContext.getBean(CustomChoiceBoxManager.class, appointmentDto);
            CustomLabelManager customLabelManager = springContext.getBean(CustomLabelManager.class);

            appointmentDto.getStatusVbox().getChildren()
                    .add((CustomChoiceBox)customChoiceBoxManager.getChoiceBoxWithProperties(rowData));
            customLabelManager.setColumnType(ColumnType.NAME);
            appointmentDto.getNamesVbox().getChildren()
                    .add((CustomLabel)customLabelManager.getLabelWithProperties(rowData));
            customLabelManager.setColumnType(ColumnType.OPERATIONS);
            appointmentDto.getOperationsVbox().getChildren()
                    .add((CustomLabel)customLabelManager.getLabelWithProperties(rowData));
            customLabelManager.setColumnType(ColumnType.NOTES);
            appointmentDto.getNotesVbox().getChildren()
                    .add((CustomLabel)customLabelManager.getLabelWithProperties(rowData));
//        }

        return appointmentDto;
    }
    
}
