/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
//@ToString
@Entity
@Component
@Scope("prototype")
@Primary
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn( name = "contact_type" )
@NamedEntityGraph(name = "Contact.Overview", attributeNodes = {@NamedAttributeNode("phones"), @NamedAttributeNode("notes")})

public class Contact implements Serializable{
    
    @Id
    @SequenceGenerator(name = "contact_id_sequence", initialValue = 1001, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_id_sequence")
    @Column(name="contact_id")
    private long contactId;
    
    public enum ContactType {
        CONTACT,
        CUSTOMER,
        EMPLOYEE,
        SUPPLIER
    }

    @Column(name="contact_type")
    private ContactType contactType;

    @OneToMany(mappedBy = "contact")
    private List<Appointment> appointments = new LinkedList<>();
    
    @OneToMany(mappedBy = "contact")
    private List<Phone> phones = new LinkedList<>();
    
    @OneToMany(mappedBy = "contact")
    private List<Address> Addresses = new LinkedList<>();
    
    @OneToMany(mappedBy = "contact")
    private List<Email> Emails = new LinkedList<>();
    
    @OneToOne
    private Note notes;
    
    @OneToOne
    private Picture picture;
    
    private boolean deleted;
    
    private String gender;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    private String profession;
    
    private String company;
    
    private String position;
    
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name="date_created")
    private LocalDateTime dateCreated;
    
    @Column(name="last_modified")
    private LocalDateTime lastModified;
    
    ////////  METHODS  ////////
    
    public Contact(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public boolean isAvailable() {
        return ! this.isDeleted();
    }
    
    @Override
    public String toString(){
        return getFullName();
    }
    
    public String getFullName(){
        return firstName+" "+lastName;
    }
    
    public String getDetails(){    ////    TEST METHOD
        return String.format("Contact   [ Id = %d,\t  firstName = %s,\t  lastName = %s ]", contactId, getFirstName(), getLastName());
    }

}
