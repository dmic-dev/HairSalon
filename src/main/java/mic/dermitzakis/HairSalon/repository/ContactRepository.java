/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import java.util.List;
import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Contact;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mderm
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
    
    @EntityGraph(value="Contact.Load", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM Contact c")
    Optional<List<Contact>> findAllForOverview();
    
    @EntityGraph(value="Contact.Load", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Contact> findByContactId(Long contactId);
    
    @Query("SELECT c FROM Contact c WHERE UPPER(firstName) LIKE :stringParam% "
            + "AND UPPER(lastName) LIKE :stringParam% "//            + "LIKE ?#{[0].toUpperCase()}% ")
            + "ORDER BY firstName, lastName")  
    Optional<List<Contact>> findWhereNameStartsWith(@Param("stringParam")String stringParam);
    
    Optional<List<Contact>> findByFirstNameStartsWith(String stringParam);
    
    Optional<List<Contact>> findByLastNameStartsWith(String StringParam);
    
    List<Contact> findByFirstNameLikeOrLastNameLikeOrderByFirstName(String firstName, String lastName);
    
    List<Contact> findByFirstNameContainingOrLastNameContainingOrProfessionContainingOrCompanyContainingAllIgnoreCaseOrderByFirstNameAsc(String firstName, String lastName, String profession, String company);
    
    void deleteByContactId(Long id);
    
}
