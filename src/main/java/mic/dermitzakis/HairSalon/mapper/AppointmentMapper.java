/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Label;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.custom.TimeLabel;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public final class AppointmentMapper implements Mapper<DayOverviewDto> {
    private final ApplicationContext springContext;
    private final DateTimeFormatter dtf;

    private AppointmentMapper(ApplicationContext springContext) {
        this.springContext = springContext;
        this.dtf = DateTimeFormatter.ofPattern("h:mm a");
    }
    public final DayOverviewDto extract(Object object) {
        final DayOverviewDto dayOverviewDto = springContext.getBean(DayOverviewDto.class);
        Appointment appointment = (Appointment) object;

//        dayOverviewDto.setTime();
        LocalTime localTime = appointment.getAppointedDateTime().toLocalTime();
        String labelText = appointment.getAppointedDateTime().toLocalTime().format(dtf);
        TimeLabel timeLabel = springContext.getBean(TimeLabel.class, localTime, labelText);
        dayOverviewDto.setTimeLabel(timeLabel);

        String fullName = appointment.getContact().getFullName();
        DayTableLabel appointmentLabel = springContext.getBean(DayTableLabel.class, fullName);
        dayOverviewDto.getNamesVbox().getChildren().add(appointmentLabel);

//            String operations = appointment.getOperations().stream().collect(Collectors.joining());
        String operations = "Some Operations";
        appointmentLabel = springContext.getBean(DayTableLabel.class, operations);
        dayOverviewDto.getOperationsVbox().getChildren().add(appointmentLabel);

        String notes = "Some Notes"; //appointment.getNotes().getText();
        appointmentLabel = springContext.getBean(DayTableLabel.class, notes);
        dayOverviewDto.getNotesVbox().getChildren().add(appointmentLabel);

//        AppointmentStatus appointmentStatus = AppointmentStatus.extractStatus(appointment);
//        CustomChoiceBox choiceBox= springContext.getBean(CustomChoiceBox.class, appointmentStatus);
//        choiceBox.setAppointmentStatus(appointmentStatus);
//        dayOverviewDto.getStatusVbox().getChildren().add(choiceBox);

        return dayOverviewDto;
    }

}
