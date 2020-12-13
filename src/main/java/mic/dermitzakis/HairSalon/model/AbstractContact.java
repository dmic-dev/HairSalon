/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author mderm
 */
@Data
@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Contact_Type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractContact implements Serializable {
    
    @Id
    @SequenceGenerator(name = "abstract_ContactId_sequence", initialValue = 1001, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abstract_ContactId_sequence")
    @Column
    private long abstractContactId;
    
    public enum Gender {
        MALE("Άνδρας"),
        FEMALE("Γυναίκα"),
        UNSPECIFIED("Απροσδιόριστο");
        
        String gender;
        
        private Gender(String gender){
            this.gender = gender;
        }

        @Override
        public String toString() {
            return gender;
        }
    }
    
//    @Column(name="contact_type")
//    protected Contact.ContactType contactType;
//
//    @OneToMany(mappedBy = "AbstractContact")
//    protected List<Appointment> appointments = new ArrayList<>();
//    
//    @OneToMany(mappedBy = "AbstractContact")
//    protected List<Phone> phones = new ArrayList<>();
//    
//    @OneToMany(mappedBy = "AbstractContact")
//    protected List<Address> addresses = new ArrayList<>();
//    
//    @OneToMany(mappedBy = "AbstractContact")
//    protected List<Email> emails = new ArrayList<>();
//    
    @OneToOne
    protected Note notes;
    
    @OneToOne
    protected Picture picture;
    
    protected boolean deleted;
    
    protected Gender gender;
    
    @Column(name="first_name")
    protected String firstName;
    
    @Column(name="last_name")
    protected String lastName;
    
    protected String profession;
    
    protected String company;
    
    protected String position;
    
    @Column(name="date_of_birth")
    protected LocalDate dateOfBirth;
    
    @Column(name="date_created")
    protected LocalDateTime dateCreated;
    
    @Column(name="last_modified")
    @LastModifiedDate
    protected LocalDateTime lastModified;
    
    /* Methods */
    
    protected boolean isAvailable() {
        return ! this.isDeleted();
    }
    
    @Override
    public String toString(){
        return getFullName();
    }
    
    protected String getFullName(){
        return String.format("%s %s",firstName, lastName);
    }
    
    protected String getDetails(){    ////    TEST METHOD
        return String.format("Customer   [ Id = %d,\t  firstName = %s,\t  lastName = %s ]", abstractContactId, firstName, lastName);
    }

}
