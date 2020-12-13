/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@NoArgsConstructor
//@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
//@ToString
@Entity
@Table(name = "customer")
@DiscriminatorValue(value = "CUSTOMER")
@Component
@Scope("prototype")
public class Customer extends AbstractContact implements Serializable {
        
//    @Id
//    @SequenceGenerator(name = "acustomer_Id_sequence", initialValue = 1001, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_Id_sequence")
//    @Column
//    private long customerId;
//    
    public Customer(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

}
