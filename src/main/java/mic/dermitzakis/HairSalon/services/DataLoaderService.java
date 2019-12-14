/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import lombok.Data;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.EntityType;
import mic.dermitzakis.HairSalon.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
public class DataLoaderService {
    
    private final EntityDataService entityDataService;
    
    private Optional<List<Contact>> contacts;
    private Optional<List<Appointment>> todaysAppointments;
    private Optional<List<Appointment>> weeksAppointments;

    @Autowired
    public DataLoaderService(EntityDataService entityDataService) {
        this.entityDataService = entityDataService;
    }
    
    public void loadDataFromRepository(){
        contacts = loadContacts();
        todaysAppointments = loadTodaysAppointments();
        weeksAppointments = loadWeeksAppointments();
    }
    
    private Optional<List<Contact>> loadContacts(){
        List<Contact> contactList = (List<Contact>) 
                entityDataService.read(EntityType.CONTACT).stream()
                .collect(toList());
        
        return Optional.of(contactList);
    }
    
    private Optional<List<Appointment>> loadTodaysAppointments(){
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LocalDateTime startingDate = dateTime.minusDays(1);
        LocalDateTime endingDate = dateTime;

        EntityType entityType = EntityType.APPOINTMENT;
        entityType.setStartingDate(startingDate);
        entityType.setEndingDate(endingDate);
        List<Appointment> appointmentList = (List<Appointment>)
                entityDataService.read(entityType).stream()
                .collect(toList());
        return Optional.of(appointmentList);
    }
    
    private Optional<List<Appointment>> loadWeeksAppointments(){
        EntityType entityType = getEntityType();
        List<Appointment> appointmentList = (List<Appointment>)
                entityDataService.read(entityType).stream()
                .collect(toList());
        return Optional.of(appointmentList);
    }
    
    public EntityType getEntityType(){
        LocalDateTime startingDate = calculateWeekStart().minusDays(1);
        LocalDateTime endingDate = calculateWeekStart().plusDays(7);

        EntityType entityType = EntityType.APPOINTMENT;
        entityType.setStartingDate(startingDate);
        entityType.setEndingDate(endingDate);
        return entityType;
    }
    
    public LocalDateTime calculateWeekStart(){
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        // get days offset from Monday
        LocalDateTime startingDateOfWeek = dateTime.minusDays(dayOfWeek.compareTo(DayOfWeek.MONDAY));
        
        return startingDateOfWeek;
    }
}
