/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
//@ToString
@Entity
@Component
@Scope("prototype")
@DiscriminatorValue("EMPLOYEE")
public class Worker extends AbstractContact implements Serializable{
    
    public enum EmployeeType {
        ADMINISTRATOR,
        SUPERUSER,
        USER
    }

//    @OneToMany(mappedBy = "employee")
//    private List<LogSession> log;
//    
//    @OneToMany(mappedBy = "employee")
//    private List<Appointment> bookedAppointments = new ArrayList<>();
//    
//    @ManyToMany(mappedBy = "employee")
//    private List<UserRole> userRole = new LinkedList<>();
    
    private boolean active;
    private EmployeeType type;
    private String username; // unique
    private String password; // not unique
    
    private String pin;

    //////////  METHODS  \\\\\\\\\
    
    @Override
    public String getDetails(){    ////    TEST METHOD
        return String.format("Employee   [ Id = %d,\t  firstName = %s,\t  lastName = %s ]", getAbstractContactId(), getFirstName(), getLastName());
    }

    
}
