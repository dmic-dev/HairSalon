/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import mic.dermitzakis.HairSalon.custom.CustomChoiceBoxManager;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.custom.DayTableLabel.ColumnType;
import mic.dermitzakis.HairSalon.custom.TimeLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 *
 * @author mderm
 */
@Component
public class AppointmentDtoManager{
    @Autowired
    private ApplicationContext springContext;

    public DayOverviewDto getAppointmentDto(DayTableRow row) {
        DayOverviewDto appointmentDto = springContext.getBean(DayOverviewDto.class);
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        TimeLabel lbl = new TimeLabel(row.getTime(), dateTimeFormatter.format(row.getTime()));
        lbl.setPadding(new Insets(4, 0, 4, 2));
        
//        appointmentDto.setTime(row.getTime());
        appointmentDto.setTimeLabel(lbl);
        CustomChoiceBoxManager customChoiceBoxManager = springContext.getBean(CustomChoiceBoxManager.class, appointmentDto);
//        DayAppointmentLabelManager customLabelManager = springContext.getBean(DayAppointmentLabelManager.class);

        appointmentDto.getStatusVbox().getChildren().add(customChoiceBoxManager.getChoiceBoxWithProperties(row));
        
        appointmentDto.getNamesVbox().getChildren().add(
                getLabelWithProperties(row, ColumnType.NAME));
        
        appointmentDto.getOperationsVbox().getChildren().add(
                getLabelWithProperties(row,ColumnType.OPERATIONS));
        
        appointmentDto.getNotesVbox().getChildren().add(
                getLabelWithProperties(row, ColumnType.NOTES));

        return appointmentDto;
    }
    
    private DayTableLabel getLabelWithProperties(DayTableRow row, ColumnType columnType){
        DayTableLabel label = springContext.getBean(DayTableLabel.class);
        label.setColumnType(columnType);
        label.setLabelProperties(row);
        return label;
    }
}
