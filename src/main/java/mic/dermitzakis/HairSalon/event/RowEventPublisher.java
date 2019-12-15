/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class RowEventPublisher implements EventPublisher {
    private List<RowEventObserver> observers;
    private UUID selectedItem;
    private UUID focusedItem;
    AppointmentStatus status;

    public RowEventPublisher() {
        observers = new LinkedList<>();
    }

    public void setRowInformation(UUID selectedItem, UUID focusedItem, AppointmentStatus status){
        this.selectedItem = selectedItem;
        this.focusedItem = focusedItem;
        this.status = status;
        notifyObservers();
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
