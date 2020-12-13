/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
//@EqualsAndHashCode
//@ToString
@Entity
@Component
@Scope("prototype")
@Primary
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn( name = "contact_type" )
@NamedEntityGraph(name = "Contact.Load", attributeNodes = {
    @NamedAttributeNode("phones"), 
    @NamedAttributeNode("notes"), 
    @NamedAttributeNode("picture")})

public class Contact implements Serializable{
    
    @Id
    @SequenceGenerator(name = "contact_id_sequence", initialValue = 1001, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_id_sequence")
    @Column//(name="Contact_Id")
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
    private List<Appointment> appointments = new ArrayList<>();
    
    @OneToMany(mappedBy = "contact")
    private List<Phone> phones = new ArrayList<>();
    
    @OneToMany(mappedBy = "contact")
    private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy = "contact")
    private List<Email> emails = new ArrayList<>();
    
    @OneToOne
    private Note notes;
    
    @OneToOne
    private Picture picture;
    
    private boolean deleted;
    
    private Gender gender;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    private String profession;
    
    private String company;
    
    private String position;
    
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
    
    @CreatedDate
    @Column(name="date_created")
    private LocalDateTime dateCreated;
    
    @CreatedBy
    @OneToOne
    private Employee createdBy;
    
    @LastModifiedDate
    @Column(name="last_modified_date")
    private LocalDateTime lastModifiedDate;
    
    @LastModifiedBy
    @OneToOne
    private Employee lastModifiedBy;
    
    ////////  METHODS  ////////
    
    public Contact(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }
    
//    public Contact() {
//        firstName = "Ονοματεπώνυμο";
//        lastName = "";
//        gender = Gender.UNSPECIFIED;
////            
////            LocalDate date = LocalDate.now();
////            LocalDate zeroYearDate = date.withYear(0);
////            LocalDateTime dateTime = LocalDateTime.of(zeroYearDate, LocalTime.now());
////            
//        dateOfBirth = LocalDate.of(0, Month.JANUARY, 1);
//        dateCreated = LocalDateTime.now();
//        lastModified = LocalDateTime.now();
//    }

    public boolean isAvailable() {
        return ! this.isDeleted();
    }
    
    @Override
    public String toString(){
        return getFullName();
    }
    
    public String getFullName(){
        return String.format("%s %s", firstName, lastName);
    }
    
    public String getDetails(){    ////    TEST METHOD
        return String.format("Contact   [ Id = %d,\t  firstName = %s,\t  lastName = %s ]", contactId, firstName, lastName);
    }

    
    public Image getImage(){
        if (picture != null)
            return picture.getImage(); //Picture.byteArraytoImage(picture.getByteArray());
        return gender.getImage();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Contact) {
            Contact test = (Contact) obj;
            if (test.firstName.equals(this.firstName))
                return true;
//          should check each and every property
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.contactId ^ (this.contactId >>> 32));
        hash = 17 * hash + Objects.hashCode(this.firstName);
        hash = 17 * hash + Objects.hashCode(this.lastName);
        return hash;
    }
    
    
}
