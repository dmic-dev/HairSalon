/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.event;

import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;

/**
 *
 * @author mderm
 */
public interface RowEventObserver{
    public void update(long selectedItem, long focusedItem, AppointmentStatus status);
}
