/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import java.util.List;
import mic.dermitzakis.HairSalon.model.EntityType;


/**
 *
 * @author mderm
 */

public interface DataAccessService {
    
    public Object create(Object entity);
    
    public List<? extends Object> read(EntityType entityDataType);
    
    public Object update(Object entity);

    public void delete(Object entity);
    
}
