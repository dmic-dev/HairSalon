/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import lombok.Data;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
public class CacheService {
    
    private final H2DataService h2DataService;
    private Optional<List<Contact>> contacts;
    private Optional<List<Appointment>> todaysAppointments;
    private Optional<List<Appointment>> weeksAppointments;
    private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    public CacheService(H2DataService entityDataService) {
        this.h2DataService = entityDataService;
    }
    
    public void loadDataFromRepository(){
        contacts = loadContacts();
        todaysAppointments = loadTodaysAppointments();
        weeksAppointments = loadWeeksAppointments();
    }
    
    private Optional<List<Contact>> loadContacts(){
        LOG.info("Loading Contacts...");
        List<Contact> contactList = (List<Contact>) h2DataService
            .read(EntityType.CONTACT)
            .stream()
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
        
        LOG.info("Loading Todays Appointments...");
        List<Appointment> appointmentList = (List<Appointment>) h2DataService
                .read(entityType)
                .stream()
                .parallel()
                .collect(toList());
        
        return Optional.of(appointmentList);
    }
    
    private Optional<List<Appointment>> loadWeeksAppointments(){
        EntityType entityType = getEntityType();

        LOG.info("Loading Weeks Appointments...");
        List<Appointment> appointmentList = (List<Appointment>) h2DataService
                .read(entityType)
                .stream()
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
