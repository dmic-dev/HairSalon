/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

/**
 *
 * @author mderm
 */
@FunctionalInterface
public interface EventObserver {
    
    public void update(Object object);
    
}
