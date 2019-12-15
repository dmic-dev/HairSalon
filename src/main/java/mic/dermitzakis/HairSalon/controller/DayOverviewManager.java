/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.dto.AppointmentDtoManager;
import mic.dermitzakis.HairSalon.event.CustomLabelManager;
import mic.dermitzakis.HairSalon.dto.RowManager;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Data;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.event.CustomChoiceBoxManager;
import mic.dermitzakis.HairSalon.dto.Row;
import mic.dermitzakis.HairSalon.event.CustomLabelManager.ColumnType;
import mic.dermitzakis.HairSalon.event.CustomChoiceBox;
import mic.dermitzakis.HairSalon.event.CustomLabel;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
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
    private final RowManager rowManager;
    
    @Autowired
    public DayOverviewManager(ApplicationContext springContext, RowManager rowDtoManager) {
        this.springContext = springContext;
        this.rowManager = springContext.getBean(RowManager.class);
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
            dto.insertRow(getEmptyAppointment());
            timeTable.add(dto);
        }
        return timeTable;
    }
    
    private Appointment getEmptyAppointment(){ 
        Appointment appointment = springContext.getBean(Appointment.class);
        appointment.setAppointmentId(0);
        appointment.setStatus(AppointmentStatus.EMPTY.toString()); // !!!
        return appointment;
    }
    
    public ObservableList<AppointmentDto> getAppointmentDtoList(List<Appointment> appointments){
        ObservableList<AppointmentDto> timeTable = prepareTimeTable();
        LocalTime appointmentTime, time, topTime, bottomTime, nextTime;
        int dtoPos = 0;
        if (!appointments.isEmpty()){
            for (var appointment : appointments){
                appointmentTime = appointment.getAppointedDateTime().toLocalTime();
                topTime = timeTable.get(0).getTime();
                bottomTime = timeTable.get(timeTable.size() - 1).getTime();
                if (appointmentTime.isBefore(topTime)){
                    insertBefore(appointment, timeTable);
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
                                    timeTable.add(dtoPos + 1, newAppointmentDto(rowManager.createRow(appointment)));
                                    break;
                                }
                            }    
                        dtoPos++;
                    }
                dtoPos = 0;
            }
        }
        return timeTable;
    }
    
    private void insertBefore(Appointment appointment, ObservableList<AppointmentDto> timeTable){
        timeTable.add(0, newAppointmentDto(rowManager.createRow(appointment)));
    }
    
    private void insertAppointment(int pos, Appointment appointment, ObservableList<AppointmentDto> timeTable) {
        AppointmentDto appointmentDto = timeTable.get(pos);
        if (((CustomLabel)getNamesVBoxList(appointmentDto).get(0)).getText().equals("")) 
            appointmentDto.removeRow();
        appointmentDto.insertRow(appointment);
        timeTable.set(pos, appointmentDto);
    }

    private void insertAfter(Appointment appointment, ObservableList<AppointmentDto> timeTable) {
        timeTable.add(newAppointmentDto(rowManager.createRow(appointment)));
    }

    private ObservableList<Node> getNamesVBoxList(AppointmentDto appointmentDto){
        return appointmentDto.getNamesVbox().getChildren();
    }
    private AppointmentDto newAppointmentDto(Row row){
        AppointmentDtoManager appointmentDtoManager = springContext.getBean(AppointmentDtoManager.class);
        return (AppointmentDto)appointmentDtoManager.getAppointmentDto(row);
    }

}
