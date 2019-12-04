/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class RowEventPublisher implements EventPublisher {
    List<RowEventObserver> observers;
    long selectedItem;
    long focusedItem;
    AppointmentStatus status;

    public RowEventPublisher() {
        this.observers = new LinkedList<>();
    }

    public void setRowInformation(long selectedItem, long focusedItem, AppointmentStatus status){
        this.selectedItem = selectedItem;
        this.focusedItem = focusedItem;
        this.status = status;
        this.notifyObservers();
    }
    
    @Override
    public void registerObserver(Object object) {
        observers.add((RowEventObserver)object);
    }

    @Override
    public void unregisterObserver(Object object) {
        observers.remove((RowEventObserver)object);
    }

    @Override
    public void notifyObservers() {
        observers.stream()
                .forEach((observer) ->  observer.update(selectedItem, focusedItem, status));
    }
    
}
