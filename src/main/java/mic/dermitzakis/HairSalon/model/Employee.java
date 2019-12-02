/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Component
@Scope("prototype")

public class Employee extends Contact implements Serializable{

    public Employee(Employee employee) {
        super.setContactId(employee.getContactId());
        super.setContactType(employee.getContactType());
        super.setFirstName(employee.getFirstName());
        super.setLastName(employee.getLastName());
        super.setAddresses(employee.getAddresses());
        super.setPhones(employee.getPhones());
        super.setEmails(employee.getEmails());
        super.setGender(employee.getGender());
        super.setProfession(employee.getProfession());
        super.setCompany(employee.getCompany());
        super.setPosition(employee.getPosition());
        super.setDeleted(employee.isDeleted());
        super.setAppointments(employee.getAppointments());
        super.setNotes(employee.getNotes());
        super.setPicture(employee.getPicture());
        super.setDateOfBirth(employee.getDateOfBirth());
        super.setDateCreated(employee.getDateCreated());
        super.setLastModified(employee.getLastModified());
        this.type = employee.getType();
        this.log = employee.log;
        this.bookedAppointments = employee.bookedAppointments;
        this.userRole = employee.userRole;
        this.active = employee.isActive();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.pin = employee.pin;
        // ...etc.
    }
    
    public enum EmployeeType {
        ADMINISTRATOR,
        SUPERUSER,
        USER
    }

    @OneToMany(mappedBy = "employee")
    private List<LogSession> log;
    
    @OneToMany(mappedBy = "employee")
    private List<Appointment> bookedAppointments = new ArrayList<>();
    
    @ManyToMany(mappedBy = "employee")
    private List<UserRole> userRole = new LinkedList<>();
    
    private boolean active;
    private EmployeeType type;
    private String username; // unique
    private String password; // not unique
    
    private String pin;

    //////////  METHODS  \\\\\\\\\
    
    @Override
    public String getDetails(){    ////    TEST METHOD
        return String.format("Employee   [ Id = %d,\t  firstName = %s,\t  lastName = %s ]", getContactId(), getFirstName(), getLastName());
    }

}
