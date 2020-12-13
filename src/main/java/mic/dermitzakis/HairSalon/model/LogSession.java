/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "log_session")

public class LogSession implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column//(name = "log_session_id")
    private long logSessionId;
    
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime login;
    
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logout;
    
    @ManyToOne
    @JoinColumn(name = "employeeid", referencedColumnName = "contactid")
    private Employee employee;
}
