/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import mic.dermitzakis.HairSalon.model.Contact;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mderm
 */
@Repository
@Transactional
public class ContactDAOService {
    
    @PersistenceContext
    private EntityManager entityManager;

    public long insert(Contact contact){
        entityManager.persist(contact);
        return contact.getContactId();
    }
}
