/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.dto.AppointmentDtoManager;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.Data;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.custom.TimeLabel;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import mic.dermitzakis.HairSalon.dto.DayTableRow;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.mapper.DayTableDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
public class DayOverviewTableLoader {
    private final ApplicationContext springContext;
    private final DayTableDetailsMapper tableDetails;
    private ObservableList<DayOverviewDto> timeTable;
    
    @Autowired
    public DayOverviewTableLoader(ApplicationContext springContext) {
        this.springContext = springContext;
        this.tableDetails = springContext.getBean(DayTableDetailsMapper.class);
    }
   
    private ObservableList<DayOverviewDto> prepareTimeTable(){
        ObservableList<DayOverviewDto> table = FXCollections.observableArrayList();
        List<LocalTime> timeList = List.of(
                LocalTime.of(9, 0),LocalTime.of(9, 30),LocalTime.of(10, 0),LocalTime.of(10, 30),
                LocalTime.of(11, 0),LocalTime.of(11, 30),LocalTime.of(12, 0),LocalTime.of(12, 30),
                LocalTime.of(13, 0),LocalTime.of(13, 30),LocalTime.of(14, 0),LocalTime.of(14, 30),
                LocalTime.of(15, 0),LocalTime.of(15, 30),LocalTime.of(16, 0),LocalTime.of(16, 30),
                LocalTime.of(17, 0),LocalTime.of(17, 30),LocalTime.of(18, 0),LocalTime.of(18, 30),
                LocalTime.of(19, 0),LocalTime.of(19, 30),LocalTime.of(20, 0),LocalTime.of(20, 30),
                LocalTime.of(21, 0));
        
        DayOverviewDto dto;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        for (LocalTime localTime : timeList){
            dto = springContext.getBean(DayOverviewDto.class);
            TimeLabel timeLabel = new TimeLabel(localTime, timeFormatter.format(localTime));
            timeLabel.setPadding(new Insets(4,0,4,2));
            dto.setTimeLabel(timeLabel);
            dto.insertEmptyRow();
            table.add(dto);
        }
        return table;
    }
    
    public ObservableList<DayOverviewDto> getTableItems(Optional<List<Appointment>> appointments){
        timeTable = prepareTimeTable();
        LocalTime appointmentTime, time, topTime, bottomTime, nextTime;
        int rowCount = 0;
        if (appointments.isEmpty()) return timeTable;
        if (!appointments.get().isEmpty()){
            for (var appointment : appointments.get()){
                appointmentTime = appointment.getAppointedDateTime().toLocalTime();
                topTime = timeTable.get(0).getTimeLabel().getTime();
                bottomTime = timeTable.get(timeTable.size() - 1).getTimeLabel().getTime();
                if (appointmentTime.isBefore(topTime)){
                    insertBefore(appointment);
                } else if (appointmentTime.isAfter(bottomTime)){ 
                    insertAfter(appointment);
                } else 
                    for (var dto : timeTable) {
                        time = dto.getTimeLabel().getTime();
                        if (appointmentTime.equals(time)) {
                            insertAppointment(rowCount, appointment); //check method
                            break;
                        } else 
                            if (rowCount != timeTable.size() - 1) {
                                nextTime = timeTable.get(rowCount + 1).getTimeLabel().getTime();
                                // if appointment is after current and before next appointment insert between
                                if (appointmentTime.isAfter(time) && appointmentTime.isBefore(nextTime)) {
                                    timeTable.add(rowCount + 1, newAppointmentDto(tableDetails.extract(appointment)));
                                    break;
                                }
                            }    
                        rowCount++;
                    }
                rowCount = 0;
            }
        }
        return timeTable;
    }
    
    private void insertBefore(Appointment appointment){
        timeTable.add(0, newAppointmentDto(tableDetails.extract(appointment)));
    }
    
    private void insertAppointment(int pos, Appointment appointment) {
        DayOverviewDto appointmentDto = timeTable.get(pos);
        appointmentDto.insertRow(appointment);
        timeTable.set(pos, appointmentDto);
    }

    private void insertAfter(Appointment appointment) {
        timeTable.add(newAppointmentDto(tableDetails.extract(appointment)));
    }

    private DayOverviewDto newAppointmentDto(DayTableRow row){
        AppointmentDtoManager appointmentDtoManager = springContext.getBean(AppointmentDtoManager.class);
        return (DayOverviewDto)appointmentDtoManager.getAppointmentDto(row);
    }

    public ObservableList<DayOverviewDto> getDayOverviewDataList(FilteredList<Appointment> filteredList){
        List<Appointment> appointments = filteredList
                .parallelStream()
                .collect(Collectors.toList());
        return DayOverviewTableLoader.this.getTableItems(Optional.of(appointments)); // overloaded
    }

    public ObservableList<DayOverviewDto> getFilteredData(ObservableList<DayOverviewDto> data, String searchString) {
        FilteredList<DayOverviewDto> filteredData = new FilteredList<>(data, (DayOverviewDto e) -> {
            Node node = e.getNamesVbox().getChildrenUnmodifiable().get(0);
            Label time = e.getTimeLabel();
            return time.getText().contains(searchString) || !((DayTableLabel)node).getText().isEmpty();
        });
        return FXCollections.observableArrayList(filteredData);   
    }
}
