/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import java.util.List;
import static java.util.stream.Collectors.toList;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mderm
 */

@Service
public class H2DataService {
    
    private final DataAccessService dataAccessService;
    
    private static final Logger LOG = LoggerFactory.getLogger(H2DataService.class);

    @Autowired
    public H2DataService(@Qualifier("h2Database")DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }
    
    public Object create(Object entity) {
        return dataAccessService.create(entity);
    }

    public List<? extends Object> read(EntityType entityDataType) {
        return dataAccessService.read(entityDataType);
    }

    public Object update(Object entity) {
        return dataAccessService.update(entity);
    }

    public void delete(Object entity) {
        dataAccessService.delete(entity);
    }

   
    public void showContactList(){
        System.out.println("\n*Contact List .....");
        getSampleData(EntityType.CONTACT).forEach(entity -> LOG.info(entity.getDetails()));
        System.out.println("\n");
    }
    
    ////// PRIVATE METHODS //////
    
    private List<Contact> getSampleData(EntityType entityDataType) {
        return (List<Contact>) read(entityDataType)
                .stream()
                .collect(toList());
    }
    
}
