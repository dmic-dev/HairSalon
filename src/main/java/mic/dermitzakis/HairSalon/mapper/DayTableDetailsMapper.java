/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import mic.dermitzakis.HairSalon.dto.DayTableRow;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
import mic.dermitzakis.HairSalon.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public final class DayTableDetailsMapper implements Mapper<DayTableRow> {
    private final ApplicationContext springContext;

    @Autowired
    public DayTableDetailsMapper(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public final DayTableRow extract(Object object) {
        final Appointment appointment = (Appointment)object;
        if (appointment.getStatus().equals(AppointmentStatus.EMPTY.toString())) 
            return newEmptyRow();
        return newRow(appointment);
    }
    
    private DayTableRow newEmptyRow(){
        final DayTableRow row = springContext.getBean(DayTableRow.class);
        row.setIdentity(UUID.randomUUID());
        row.setAppointmentId(0);
        row.setName("");
        row.setNotes("");
        row.setOperations("");
        row.setAppointmentStatus(AppointmentStatus.EMPTY);
        return row;
    }
    
    private DayTableRow newRow(Appointment appointment){
        final NotesItemImpl notesItem = springContext.getBean(NotesItemImpl.class);
        final OperationsItemImpl operationsItem = springContext.getBean(OperationsItemImpl.class);
        final DayTableRow rowData = springContext.getBean(DayTableRow.class);
        rowData.setIdentity(UUID.randomUUID());
        rowData.setAppointmentId(appointment.getAppointmentId());
        rowData.setTime(appointment.getAppointedDateTime().toLocalTime());
        rowData.setName(appointment.getContact().getFullName());
        rowData.setOperations((String)operationsItem.get(appointment));
        rowData.setNotes((String)notesItem.get(appointment));
        rowData.setAppointmentStatus(extractStatus(appointment));
        return rowData;
    }
    
    private AppointmentStatus extractStatus(Appointment appointment){
        AppointmentStatus status;
        switch (appointment.getStatus()){
            case "Εκκρεμεί" : {status = AppointmentStatus.PENDING; break;}
            case "Ολοκληρώθηκε" : {status = AppointmentStatus.COMPLETED; break;}
            case "Ακύρωση" : {status = AppointmentStatus.CANCELED; break;}
            case "-" : {status = AppointmentStatus.EMPTY; break;}
            default : status = AppointmentStatus.PENDING;
        }
        return status;
    }
    
    @Component
    public final class OperationsItemImpl{
        
        public String get(Appointment appointment) {
            String operationString = null;
//            List<Labor> operations = appointment.getOperations();
            List<String> operations = List.of("Λούσιμο", "Κούρεμα", "Βάψιμο", "Χτένισμα");
            if (operations != null)
                operationString = operations.stream()
//                        .map((operation) -> operation.getDescription())
                        .collect(Collectors.joining(", "));

            return operationString;
        }

    }
    
    @Component
    public final class NotesItemImpl{

        public String get(Appointment appointment) {
            String text = null;
            Note notes = appointment.getNotes();
            if (notes != null) text = notes.getText();

//            return text;
        return "Σημειώσεις";
        }

    }

}
