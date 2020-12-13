/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.services;

import java.util.List;
import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mderm
 */
@Service
//@CacheConfig(cacheNames = "contactCache")
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    
//    @Cacheable(cacheNames = "contactCache")
    public Optional<List<Contact>> findAll() {
        return contactRepository.findAllForOverview();
    }
    
//    @Cacheable(cacheNames = "contactCache", key = "#id")
    public Optional<Contact> findContactById(Long id) {
        return contactRepository.findById(id);
    }
    
//    @CachePut(cacheNames = "contactCache", key = "#contact.contactId")
    public Optional<Contact> save(Contact contact) {
        return Optional.of(contactRepository.save(contact));
    }
    
//    @CachePut(cacheNames = "contactCache", key = "#contact.contactId")
    public Optional<Contact> update(Contact contact) {
        return Optional.of(contactRepository.save(contact));
    }
    
//    @CacheEvict(cacheNames = "contactCache", key = "#contact.contactId")
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }
    
//    @CacheEvict(cacheNames = "contactCache", key = "#id")
    public void deleteById(Long id) {
        contactRepository.deleteByContactId(id);
    }
    
}
