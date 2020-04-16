/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import mic.dermitzakis.HairSalon.model.Contact;
import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import mic.dermitzakis.HairSalon.model.EntityType;
import mic.dermitzakis.HairSalon.model.Activity;
import mic.dermitzakis.HairSalon.model.Contact.Gender;
import mic.dermitzakis.HairSalon.model.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author mderm
 * 
 * 
 */
@Service("SampleData")
// methods must return opperation status (Instead of throwing execption)
public class SampleData implements DataAccess{

    private final ApplicationContext springContext;
    private final List<Picture> pictureList;
    private final List<Object> contactList;
    private final List<Object> appointmentList;
    private final List<Activity> operationsSampleList;
//    private Picture pic;
    
    @Autowired
    public SampleData(ApplicationContext springContext) throws IOException{
        this.springContext = springContext;
//        this.pic = springContext.getBean(Picture.class);
//        Image image = new Image("file:"+getClass().getResource("/images/Me.jpg").getPath());
//        pic.setImage(Picture.toByteArray(image));
//        image = new Image("file:"+getClass().getResource("/images/Γιώργος-Προσαρμοσμένο.jpg").getPath());
//        pic.setImage(Picture.toByteArray(image));
       
        
        pictureList = List.of(newImage("Me.jpg"), newImage("Γιώργος-Προσαρμοσμένο.jpg"));
        contactList = List.of(       //Immutable list
            newContactBean("Martin", "Welsch", Gender.MALE, null),
            newContactBean("Jill", "Andrews",Gender.FEMALE, null), 
            newContactBean("Γιώργος", "Τζουγκανάκης",Gender.MALE, pictureList.get(1)), //newImage("Γιώργος-Προσαρμοσμένο.jpg"
            newContactBean("Michael", "Johnson",Gender.MALE, null), 
            newContactBean("Eva", "Stanich",Gender.FEMALE, null),
            newContactBean("Μιχάλης", "Δερμιτζάκης",Gender.MALE, pictureList.get(0)), // newImage("Me.jpg")
            newContactBean("Nick", "Ibrahimovich",Gender.MALE, null),
            newContactBean("Joseph", "Johnson",Gender.MALE, null), 
            newContactBean("Linda", "Evangelista",Gender.FEMALE, null),
            newContactBean("Nathan", "Tompson", Gender.MALE, null), 
            newContactBean("Angela", "Tomas",Gender.FEMALE, null),
            newContactBean("William", "Wallace",Gender.MALE, null), 
            newContactBean("Angela", "Caster",Gender.FEMALE, null),
            newContactBean("Angelina", "Bowman",Gender.FEMALE, null), 
            newContactBean("George", "Stefanopoulos",Gender.MALE, null),
            newContactBean("Jenny", "Zeilda",Gender.FEMALE, null), 
            newContactBean("ΕΛΕΝΗ", "ΚΑΠΑΔΟΥΚΑΚΗ",Gender.FEMALE, null),     
            newContactBean("Joshua", "Dekavalas",Gender.MALE, null), 
            newContactBean("Jack", "Nickolson",Gender.MALE, null),
            newContactBean("Andrew", "Lloyd",Gender.MALE, null),
            newContactBean("Michael", "Moore",Gender.MALE, null),
            newContactBean("Mina", "Martinez",Gender.FEMALE, null)
        );
        
        DataLoader dataLoaderService = springContext.getBean(DataLoader.class);
        LocalDate date = dataLoaderService.calculateWeekStart().toLocalDate();
        
        appointmentList = Arrays.asList(
            newAppointmentBean(date,LocalTime.of(19, 30), (Contact)contactList.get(0)),
            newAppointmentBean(date.plusDays(1),LocalTime.of(19, 30), (Contact)contactList.get(1)),
            newAppointmentBean(date.plusDays(2),LocalTime.of(19, 30), (Contact)contactList.get(2)),
            newAppointmentBean(date.plusDays(3),LocalTime.of(19, 30), (Contact)contactList.get(3)),
            newAppointmentBean(date.plusDays(4),LocalTime.of(19, 30), (Contact)contactList.get(4)),
            newAppointmentBean(date.plusDays(5),LocalTime.of(11, 30), (Contact)contactList.get(5)),
            newAppointmentBean(date.plusDays(6),LocalTime.of(12, 00), (Contact)contactList.get(6)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(12, 30), (Contact)contactList.get(7)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(21, 00), (Contact)contactList.get(16)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(22, 15), (Contact)contactList.get(19)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(21, 46), (Contact)contactList.get(17)),
            newAppointmentBean(date.plusDays(7),LocalTime.of(21, 47), (Contact)contactList.get(18)),
            newAppointmentBean(date,LocalTime.of(8, 30), (Contact)contactList.get(21))
        );
        
        operationsSampleList = List.of(
                newOperationBean("Lousimo"), newOperationBean("Kourema"),
                newOperationBean("Vafi"), newOperationBean("Therapeia")
        );
    }

    
    private Picture newImage(String filename) throws IOException {
        Image image = new Image("file:"+getClass().getResource("/images/"+filename).getPath());
        Picture picture = new Picture();
        picture.setImage(Picture.toByteArray(image));
        return picture;
    }
    private Contact newContactBean(String firstName, String lastName, Gender gender, Picture pic){
        Contact contact = springContext.getBean(Contact.class, firstName, lastName);
        contact.setGender(gender);
        contact.setPicture(pic);
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
        boolean ok = contactList.add((Contact)entity);
        return (ok) ? entity : null;
    }

    @Override
    public List<? extends Object> read(EntityType entityDataType) {
        switch (entityDataType) {
            case CONTACT : return contactList;
            case APPOINTMENT : return appointmentList;
            case PICTURE : return pictureList;
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
