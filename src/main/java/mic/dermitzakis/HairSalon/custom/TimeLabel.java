/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.custom;

import java.time.LocalTime;
import javafx.scene.control.Label;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author mderm
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TimeLabel extends Label{
    private final LocalTime time;

    public TimeLabel(LocalTime time, String string) {
        super(string);
        this.time = time;
    }
    
    
}
