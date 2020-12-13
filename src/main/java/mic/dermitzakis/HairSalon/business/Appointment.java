/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.business;

import java.time.LocalDateTime;

/**
 *
 * @author mderm
 */
public class Appointment {
    
    public LocalDateTime whenIsNext(){return null;}
    public LocalDateTime whenWasLast(){return null;}
    public double averageTimesPerMonth(){return 0.0;}
    public double averageMoneyPerMonth(){return 0.0;}
    public PaymentMethod paymentMethod(){return null;}
    
    public enum PaymentMethod {
        CASH,
        CARD;
    }

}
