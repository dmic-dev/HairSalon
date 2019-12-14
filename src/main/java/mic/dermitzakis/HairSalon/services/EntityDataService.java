/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.services;

import java.util.LinkedList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mic.dermitzakis.HairSalon.database.DataAccessManager;

/**
 *
 * @author mderm
 */

@Service
public class EntityDataService {
    
    private final DataAccessManager dataAccessManager;
    
    private static final Logger LOG = LoggerFactory.getLogger(EntityDataService.class);

    @Autowired
    public EntityDataService(@Qualifier("h2Database")DataAccessManager dataAccessManager) {
        this.dataAccessManager = dataAccessManager;
    }
    
    public Object create(Object entity) {
        return dataAccessManager.create(entity);
    }

    public List<? extends Object> read(EntityType entityDataType) {
        return dataAccessManager.read(entityDataType);
    }

    public Object update(Object entity) {
        return dataAccessManager.update(entity);
    }

    public void delete(Object entity) {
        dataAccessManager.delete(entity);
    }

   
    public void showContactList(){
        System.out.println("\n*Contact List .....");
        getSampleData(EntityType.CONTACT).forEach(entity -> LOG.info(entity.getDetails()));
        System.out.println("\n");
    }
    
    ////// PRIVATE METHODS //////
    
    private List<Contact> getSampleData(EntityType entityDataType) {
        return (List<Contact>) read(entityDataType).stream()
                .collect(toList());
    }
    
}
