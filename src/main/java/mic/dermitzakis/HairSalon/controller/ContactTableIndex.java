/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class ContactTableIndex implements TableIndex {
    private int value = ZERO_INDEX;

    @Override
    public void increase() {
        value++;
    }

    @Override
    public void decrease() {
        value--;
    }
    
}
