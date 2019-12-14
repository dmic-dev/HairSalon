/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import mic.dermitzakis.HairSalon.model.Contact;
import java.util.Arrays;
import java.util.List;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import mic.dermitzakis.HairSalon.model.EntityType;
import mic.dermitzakis.HairSalon.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import mic.dermitzakis.HairSalon.services.DataLoaderService;
import mic.dermitzakis.HairSalon.database.DataAccessManager;

/**
 *
 * @author mderm
 * 
 * 
 */
@Service("SampleData")
// methods must return opperation status (Instead of throwing execption)
public class SampleDataAccessManager implements DataAccessManager{

    private final ApplicationContext springContext;
    private final List<Object> contactSampleList;
    private final List<Object> appointmentSampleList;
    private final List<Activity> operationsSampleList;
    
    @Autowired
    public SampleDataAccessManager(ApplicationContext springContext){
        this.springContext = springContext;
        
        contactSampleList = List.of(       //Immutable list
            newContactBean("Martin", "Welsch"),        newContactBean("Jill", "Tompson"), 
            newContactBean("Andreas", "Nicopolidis"),   newContactBean("Michael", "Johnson"), 
            newContactBean("Eva", "Stanich"),           newContactBean("Μιχάλης", "Δερμιτζάκης"), 
            newContactBean("Nick", "Ibrahimovich"),     newContactBean("Joseph", "Johnson"), 
            newContactBean("Linda", "Evangelista"),     newContactBean("Nathan", "Tompson"), 
            newContactBean("Angela", "Tomas"),          newContactBean("William", "Wallace"), 
            newContactBean("Angela", "Caster"),         newContactBean("Angelina", "Bowman"), 
            newContactBean("George", "Stefanopoulos"),  newContactBean("Jenny", "Zeilda"), 
            newContactBean("ΕΛΕΝΗ", "ΚΑΠΑΔΟΥΚΑΚΗ"),     newContactBean("Joshua", "Dekavalas"), 
            newContactBean("Jack", "Nickolson"),        newContactBean("Andrew", "Lloyd"),
            newContactBean("Michael", "Moore"),        newContactBean("Mina", "Martinez")
        );
        
        DataLoaderService dataLoaderService = springContext.getBean(DataLoaderService.class);
        LocalDate date = dataLoaderService.calculateWeekStart().toLocalDate();
        
        appointmentSampleList = Arrays.asList(
            newAppointmentBean(date,LocalTime.of(19, 30), (Contact)contactSampleList.get(0)),
            newAppointmentBean(date.plusDays(1),LocalTime.of(19, 30), (Contact)contactSampleList.get(1)),
            newAppointmentBean(date.plusDays(2),LocalTime.of(19, 30), (Contact)contactSampleList.get(2)),
            newAppointmentBean(date.plusDays(3),LocalTime.of(19, 30), (Contact)contactSampleList.get(3)),
            newAppointmentBean(date.plusDays(4),LocalTime.of(19, 30), (Contact)contactSampleList.get(4)),
            newAppointmentBean(date.plusDays(5),LocalTime.of(11, 30), (Contact)contactSampleList.get(5)),
            newAppointmentBean(date.plusDays(6),LocalTime.of(12, 00), (Contact)contactSampleList.get(6)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(12, 30), (Contact)contactSampleList.get(7)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(21, 00), (Contact)contactSampleList.get(8)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(22, 15), (Contact)contactSampleList.get(9)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(21, 46), (Contact)contactSampleList.get(10)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(21, 46), (Contact)contactSampleList.get(16))
        );
        
        operationsSampleList = List.of(
                newOperationBean("Lousimo"), newOperationBean("Kourema"),
                newOperationBean("Vafi"), newOperationBean("Therapeia")
        );
    }

    private Contact newContactBean(String firstName, String lastName){
        Contact contact = springContext.getBean(Contact.class, firstName, lastName);
        contact.setGender("Γυναίκα");
        contact.setDateOfBirth(LocalDate.now());
        contact.setDateCreated(LocalDateTime.now());
        contact.setLastModified(LocalDateTime.now());
        return contact;
    }
    
    private Appointment newAppointmentBean(LocalDate appointedDate, LocalTime appointedTime, Contact contact){
        Appointment appointment = springContext.getBean(Appointment.class);
//        Note note = springContext.getBean(Note.class);
//        note.setText("Σημείωση");
        appointment.setAppointedDateTime(LocalDateTime.of(appointedDate, appointedTime));
        appointment.setTimeCreated(LocalDateTime.now());
        appointment.setContact(contact);
        appointment.setOperations(operationsSampleList);
//        appointment.setNotes(note);
        appointment.setStatus(AppointmentStatus.PENDING.toString());
        return appointment;
    }

    private Activity newOperationBean(String description){
        Activity operation = springContext.getBean(Activity.class);
        operation.setDescription(description);
        return operation;
    }
    

    @Override
    public Object create(Object entity) {
        boolean ok = contactSampleList.add((Contact)entity);
        return (ok) ? entity : null;
    }

    @Override
    public List<? extends Object> read(EntityType entityDataType) {
        switch (entityDataType) {
            case CONTACT : return contactSampleList;
            case APPOINTMENT : return appointmentSampleList;
            default: return new ArrayList<Object>();
        }
    }
    
    @Override
    public Object update(Object entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
