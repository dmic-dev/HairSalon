/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@EqualsAndHashCode
@ToString
@Entity
@Component
@Scope("prototype")
//@Table(name = "appointment")
@NamedEntityGraph(name = "Appointment.Overview", attributeNodes = @NamedAttributeNode("operations"))
public class Appointment implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private long appointmentId;
    
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "contact_id")
    private Employee employee;
    
    @OneToMany(mappedBy = "appointment")
    private List<Activity> operations;
    
    @Column(length = 15)
    private String status;
            
    @Column(name = "appointed_datetime")
    private LocalDateTime appointedDateTime;

    @OneToOne
    private Note notes;
    
    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    public String getDetails(){
        return "Date: "+ appointedDateTime.toLocalDate() +",  Time: "+ appointedDateTime.toLocalTime() +",  "+contact.getFirstName()+",\t"+contact.getLastName()+",\tTime Created: "+timeCreated;
    }
}
