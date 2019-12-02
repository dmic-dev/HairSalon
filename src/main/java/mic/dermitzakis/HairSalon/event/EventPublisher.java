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
public interface EventPublisher {
    public void registerObserver(Object object);
    public void unregisterObserver(Object object);
    public void notifyObservers();
}
