/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;

/**
 *
 * @author mderm
 */
@Component
public class RowManager{
    @Autowired
    private final ApplicationContext springContext;

    public RowManager(ApplicationContext springContext) {
        this.springContext = springContext;
    }
    

    public Row createRow(Appointment appointment) {
        if (appointment.getStatus().equals(AppointmentStatus.EMPTY.toString())) return newEmptyRow();
        return newRow(appointment);
    }
    
    private Row newEmptyRow(){
        Row row = springContext.getBean(Row.class);
        row.setIdentity(UUID.randomUUID());
        row.setAppointmentId(0);
        row.setName("");
        row.setNotes("");
        row.setOperations("");
        row.setAppointmentStatus(AppointmentStatus.EMPTY);
        return row;
    }
    
    private Row newRow(Appointment appointment){
        NotesItemImpl notesItem = springContext.getBean(NotesItemImpl.class);
        OperationsItemImpl operationsItem = springContext.getBean(OperationsItemImpl.class);
        Row rowData = springContext.getBean(Row.class);
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
    public class OperationsItemImpl{
        
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
    public class NotesItemImpl{

        public String get(Appointment appointment) {
            String text = null;
            Note notes = appointment.getNotes();
            if (notes != null) text = notes.getText();

//            return text;
        return "Σημειώσεις";
        }

    }
}
