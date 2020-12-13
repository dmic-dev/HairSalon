/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.controller.DayOverviewController;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.dto.AppointmentSideDetailsDto;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import mic.dermitzakis.HairSalon.mapper.DaySideDetailsMapper;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.services.AppointmentService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
public final class AppointmentSideDetailsEvent implements Event{
    private final ApplicationContext springContext;
    private final AppointmentService appointmentService;
    private final DayOverviewController dayOverviewController;
    private final DaySideDetailsMapper daySideDetails;
    private AppointmentSideDetailsDto data;

    public AppointmentSideDetailsEvent(ApplicationContext springContext, AppointmentService appointmentService, DayOverviewController dayOverviewController, DaySideDetailsMapper daySideDetails) {
        this.springContext = springContext;
        this.appointmentService = appointmentService;
        this.dayOverviewController = dayOverviewController;
        this.daySideDetails = daySideDetails;
    }
    

    @Override
    public void newEvent(){
        var sideDetailsDto = springContext.getBean(AppointmentSideDetailsDto.class);
        sideDetailsDto.init();
        DayOverviewDto appointmentDto = getTableSelectedItem();
        if (isNamesVBoxNotEmpty(appointmentDto)) {
            ObservableList<Node> names = getNamesVBoxChildren(appointmentDto);
            DayTableLabel nameLabel =  getSelectedLabel(names);
            long appointmentId = getLabelId(nameLabel);
            if (isNotEmptyLabel(appointmentId)){ /* if isNotEmptyLabel => emptyRow */
                Appointment appointment = getAppointmentFromRepository(appointmentId);
                AppointmentSideDetailsDto sideDetails = extractSideDetails(appointment);
                sideDetailsDto = sideDetails;
            }
        }
        setAppointmentDetailsForDispatch(sideDetailsDto);
    }

    private void setAppointmentDetailsForDispatch(AppointmentSideDetailsDto data) {
        this.setData(data);
    }

    private AppointmentSideDetailsDto extractSideDetails(Appointment appointment) {
        return daySideDetails.extract(appointment);
    }

    private Appointment getAppointmentFromRepository(long appointmentId) {
        return appointmentService.findAppointmentById(appointmentId).orElseThrow();// !!!
    }

    private static boolean isNotEmptyLabel(long appointmentId) {
        return appointmentId != 0;
    }

    private long getLabelId(DayTableLabel nameLabel) {
        return nameLabel.getAppointmentId();
    }

    private DayTableLabel getSelectedLabel(ObservableList<Node> names) {
        return (DayTableLabel) names.stream()
                .filter((label) -> ((DayTableLabel)label).getRowId() == dayOverviewController.getSelectedItem())
                .findFirst().orElseThrow();
    }

    private ObservableList<Node> getNamesVBoxChildren(DayOverviewDto appointmentDto) {
        return appointmentDto.getNamesVbox().getChildrenUnmodifiable();
    }

    private static boolean isNamesVBoxNotEmpty(DayOverviewDto appointmentDto) {
        if (appointmentDto == null) return false;
        return !appointmentDto.getNamesVbox().getChildrenUnmodifiable().isEmpty();
    }

    private DayOverviewDto getTableSelectedItem() {
        return dayOverviewController.getAppointmentTable().getSelectionModel().getSelectedItem();
    }

    
}
