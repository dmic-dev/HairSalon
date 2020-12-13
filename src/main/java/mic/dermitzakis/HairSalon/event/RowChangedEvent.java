/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import lombok.Data;
import mic.dermitzakis.HairSalon.dto.RowDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
public class RowChangedEvent {
    private RowDetailsDto data;

    @Autowired
    public RowChangedEvent(RowDetailsDto data) {
        this.data = data;
    }
        
}
