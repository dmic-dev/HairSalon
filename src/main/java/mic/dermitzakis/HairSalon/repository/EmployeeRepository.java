/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mderm
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    public Optional<Employee> findByUsername(String username);
    
}
