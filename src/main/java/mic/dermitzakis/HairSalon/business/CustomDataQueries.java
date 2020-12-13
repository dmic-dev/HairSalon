/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.business;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mic.dermitzakis.HairSalon.repository.ContactRepository;
import mic.dermitzakis.HairSalon.repository.PictureRepository;

/**
 *
 * @author mderm
 */
@Service
public class CustomDataQueries {
    
    @Autowired
    private ContactRepository contactRepo;
    @Autowired
    private AppointmentRepository appointmentRepo;
    @Autowired
    private PictureRepository pictureRepository;
    
    /*==== CONTACT ======================*/
    
//    public List<Contact> getAllContacts(){
//        return contactRepo.getAllContacts();
//    }
    public List<Object> runQuery(String query){
        return null;
    }
    
    public Optional<List<Contact>> getAllContactsForOverview(){
        return contactRepo.findAllForOverview();
    }
    
    public Optional<List<Contact>> findAllWhereNameStartsWith(String stringParam){
        return contactRepo.findWhereNameStartsWith(stringParam);
    }
    
    public Optional<List<Contact>> findByFirstNameStartsWith(String stringParam){
        return contactRepo.findByFirstNameStartsWith(stringParam);
    }
    
    public Optional<List<Contact>> getByLastNameStartsWith(String stringParam){
        return contactRepo.findByLastNameStartsWith(stringParam);
    }
    
    public List<Contact> getByAllClassStringsContaining(String stringParam){ //getByAllPropertiesContaining
        return contactRepo.findByFirstNameContainingOrLastNameContainingOrProfessionContainingOrCompanyContainingAllIgnoreCaseOrderByFirstNameAsc(stringParam, stringParam, stringParam, stringParam);
    }
    
    /*==== APPOINTMENT ==================*/
    
    public Optional<List<Appointment>> getAppointmentsBetween(LocalDateTime startingDate, LocalDateTime endingDate){
        return appointmentRepo.findByAppointedDateBetween(startingDate, endingDate);
    }
}
