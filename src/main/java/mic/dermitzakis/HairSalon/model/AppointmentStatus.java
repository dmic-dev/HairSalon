/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mderm
 */

public enum AppointmentStatus {
    COMPLETED("Ολοκληρώθηκε"),
    CANCELED("Ακύρωση"),
    PENDING("Εκκρεμεί");    
 
    private String status;
    private static final Logger LOG = Logger.getLogger(AppointmentStatus.class.getName());
    
    private AppointmentStatus(String status) {
        this.status = status;
    }

    public static AppointmentStatus extractStatus(Appointment appointment){
        AppointmentStatus st = AppointmentStatus.PENDING;
        switch (appointment.getStatus()){
            case "Εκκρεμεί"     : {st = AppointmentStatus.PENDING; break;}
            case "Ολοκληρώθηκε" : {st = AppointmentStatus.COMPLETED; break;}
            case "Ακύρωση"      : {st = AppointmentStatus.CANCELED; break;}
            default: LOG.log(Level.SEVERE, "No such item in AppointmentStatus: {0}", appointment.getStatus());
        } 
        return st;
    }

    @Override
    public String toString(){
        return status;
    }
}
