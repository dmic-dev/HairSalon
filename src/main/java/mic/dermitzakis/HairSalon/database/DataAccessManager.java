/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.database;

import java.util.List;
import mic.dermitzakis.HairSalon.model.EntityType;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.stereotype.Component;


/**
 *
 * @author mderm
 */

@Component
public interface DataAccessManager {
    
    public Object create(Object entity);
    
    public List<? extends Object> read(EntityType entityDataType);
    
    public Object update(Object entity);

    public void delete(Object entity);
    
}
