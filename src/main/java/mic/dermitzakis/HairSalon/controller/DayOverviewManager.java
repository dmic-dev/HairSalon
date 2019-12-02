/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.dto.AppointmentDtoManager;
import mic.dermitzakis.HairSalon.event.LabelManager;
import mic.dermitzakis.HairSalon.dto.RowDtoManager;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import lombok.Data;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.event.ChoiceBoxManager;
import mic.dermitzakis.HairSalon.dto.RowDto;
import mic.dermitzakis.HairSalon.event.LabelManager.ColumnType;
import mic.dermitzakis.HairSalon.event.MyChoiceBox;
import mic.dermitzakis.HairSalon.event.MyLabel;
import mic.dermitzakis.HairSalon.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
public class DayOverviewManager {
    private final ApplicationContext springContext;
    private final RowDtoManager rowDtoManager;
    
    @Autowired
    public DayOverviewManager(ApplicationContext springContext, RowDtoManager rowDtoManager) {
        this.springContext = springContext;
        this.rowDtoManager = springContext.getBean(RowDtoManager.class);
    }
   
    private ObservableList<AppointmentDto> prepareTimeTable(){
        ObservableList<AppointmentDto> timeTable = FXCollections.observableArrayList();
        List<LocalTime> timeList = List.of(
                LocalTime.of(9, 0),LocalTime.of(9, 30),LocalTime.of(10, 0),LocalTime.of(10, 30),
                LocalTime.of(11, 0),LocalTime.of(11, 30),LocalTime.of(12, 0),LocalTime.of(12, 30),
                LocalTime.of(13, 0),LocalTime.of(13, 30),LocalTime.of(14, 0),LocalTime.of(14, 30),
                LocalTime.of(15, 0),LocalTime.of(15, 30),LocalTime.of(16, 0),LocalTime.of(16, 30),
                LocalTime.of(17, 0),LocalTime.of(17, 30),LocalTime.of(18, 0),LocalTime.of(18, 30),
                LocalTime.of(19, 0),LocalTime.of(19, 30),LocalTime.of(20, 0),LocalTime.of(20, 30),
                LocalTime.of(21, 0));
        
        AppointmentDto dto;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        for (LocalTime localTime : timeList){
            dto = springContext.getBean(AppointmentDto.class);
            Label label = new Label();
            label.setPadding(new Insets(4,0,4,2));
            label.setText(timeFormatter.format(localTime));
            dto.setTime(localTime);
            dto.setTimeLabel(label);
            timeTable.add(dto);
        }
        return timeTable;
    }
    
    public ObservableList<AppointmentDto> getAppointmentDtoList(List<Appointment> appointments){
        ObservableList<AppointmentDto> timeTable = prepareTimeTable();
        LocalTime appointmentTime, time, topTime, bottomTime, nextTime;
        int dtoPos = 0;
        for (var appointment : appointments){
            appointmentTime = appointment.getAppointedDateTime().toLocalTime();
            topTime = timeTable.get(0).getTime();
            bottomTime = timeTable.get(timeTable.size() - 1).getTime();
            if (appointmentTime.isBefore(topTime)){
                insertBefore(0, appointment, timeTable);
            } else if (appointmentTime.isAfter(bottomTime)){ 
                insertAfter(appointment, timeTable);
            } else 
                for (var dto : timeTable) {
                    time = dto.getTime();
                    if (appointmentTime.equals(time)) {
                        insertAppointment(dtoPos, appointment, timeTable);
                        break;
                    } else 
                        if (dtoPos != timeTable.size() - 1) {
                            nextTime = timeTable.get(dtoPos + 1).getTime();
                            // if appointment is after current and before next appointment insert between
                            if (appointmentTime.isAfter(time) && appointmentTime.isBefore(nextTime)) {
                                timeTable.add(dtoPos + 1, newAppointmentDto(rowDtoManager.getRowDto(appointment)));
                                break;
                            }
                        }    
                    dtoPos++;
                } // for

            dtoPos = 0;
        } // for

        return timeTable;
    }
    
    private void insertBefore(int pos, Appointment appointment, ObservableList<AppointmentDto> timeTable){
        timeTable.add(pos, newAppointmentDto(rowDtoManager.getRowDto(appointment)));
    }
    
    private void insertAppointment(int pos, Appointment appointment, ObservableList<AppointmentDto> timeTable) {
        AppointmentDto tmpDto = timeTable.get(pos);
        tmpDto.insertAppointmentToDto(appointment);
    }

    private void insertAfter(Appointment appointment, ObservableList<AppointmentDto> timeTable) {
        timeTable.add(newAppointmentDto(rowDtoManager.getRowDto(appointment)));
    }

    private AppointmentDto newAppointmentDto(RowDto rowData){
        AppointmentDtoManager appointmentDtoItem = springContext.getBean(AppointmentDtoManager.class);
        return (AppointmentDto)appointmentDtoItem.getAppointmentDto(rowData);
    }
    
    @Component
    public class TimeTableState extends TableviewStateDetails{
        
    }
}
