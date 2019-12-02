/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.config;

import mic.dermitzakis.HairSalon.App;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.ContactBuilder;
import mic.dermitzakis.HairSalon.repository.ContactDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class ContactDAOServiceCommandLineRunner implements CommandLineRunner{
    
    private static final Logger LOG =
            LoggerFactory.getLogger(ContactDAOServiceCommandLineRunner.class);
    
    @Autowired
    private ContactDAOService contactDAOService;

    @Override
    public void run(String... args) throws Exception {
        Contact contact = new Contact("Michael", "Smith");
        long Id = contactDAOService.insert(contact);
        LOG.info("Contact is created : " + contact.getDetails());
    }
    
}
