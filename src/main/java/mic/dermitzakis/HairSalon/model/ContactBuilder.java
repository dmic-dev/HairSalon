/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
@Scope("prototype")
public class ContactBuilder {
    
    private final ApplicationContext springContext;
    
    private long contact_id;
    private List<Appointment> appointments;
    private boolean deleted;
    private String gender;
    private String firstName;
    private String lastName;
    private String profession;
    private String company;
    private String position;
    private LocalDate dateOfBirth;
    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
//    private Note notes;
//    private Picture picture;

    public ContactBuilder(ApplicationContext springContext) {
        this.springContext = springContext;
    }
    
    public ContactBuilder setAppointments(List<Appointment> appointments){
        this.appointments = appointments;
        return this;
    }
    
    public ContactBuilder setDeleted(boolean deleted){
        this.deleted = deleted;
        return this;
    }
    
    public ContactBuilder setGender(String gender){
        this.gender = gender;
        return this;
    }
    
    public ContactBuilder setFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    
    public ContactBuilder setLasttName(String lastName){
        this.lastName = lastName;
        return this;
    }
    
    public ContactBuilder setProfession(String profession){
        this.profession = profession;
        return this;
    }
    
    public ContactBuilder setCompany(String company){
        this.company = company;
        return this;
    }
    
    public ContactBuilder setPosition(String position){
        this.position = position;
        return this;
    }
    
    public ContactBuilder setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        return this;
    }
    
    public ContactBuilder setDateCreated(LocalDateTime dateCreated){
        this.dateCreated = dateCreated;
        return this;
    }
    
    public ContactBuilder setLastModified(LocalDateTime lastModified){
        this.lastModified = lastModified;
        return this;
    }
    
//    public ContactBuilder setNotes(String notes){
//        this.notes = notes;
//        return this;
//    }
//    
//    public ContactBuilder setPicture(byte[] picture){
//        this.picture = picture;
//        return this;
//    }
//    
    public Contact build(){
        Contact contact = springContext.getBean(Contact.class);
        contact.setAppointments(appointments);
        contact.setDeleted(deleted);
        contact.setGender(gender);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setProfession(profession);
        contact.setCompany(company);
        contact.setPosition(position);
        contact.setDateOfBirth(dateOfBirth);
        contact.setDateCreated(dateCreated);
        contact.setLastModified(lastModified);
//        contact.setNotes(notes);
//        contact.setPicture(picture);
        
        return contact;
    }
}
