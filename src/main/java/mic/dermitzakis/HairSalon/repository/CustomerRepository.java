/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import mic.dermitzakis.HairSalon.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mderm
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}
