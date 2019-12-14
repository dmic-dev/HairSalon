/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.EntityType;
import mic.dermitzakis.HairSalon.repository.AppointmentRepository;
import mic.dermitzakis.HairSalon.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mic.dermitzakis.HairSalon.services.CustomQueryService;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.database.DataAccessManager;
import mic.dermitzakis.HairSalon.model.Employee;
import mic.dermitzakis.HairSalon.repository.EmployeeRepository;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author mderm
 */
@Component("h2Database")
public class H2DataAccessManager implements DataAccessManager{
    
    private final ApplicationContext springContext;
    
    private final ContactRepository contactRepository;
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomQueryService customQueryService;

    public H2DataAccessManager(ApplicationContext springContext) {
        this.springContext = springContext;
        contactRepository = springContext.getBean(ContactRepository.class);
        appointmentRepository = springContext.getBean(AppointmentRepository.class);
        employeeRepository = springContext.getBean(EmployeeRepository.class);
        customQueryService = springContext.getBean(CustomQueryService.class);
    }

    
    @Override // Actually is a lot more sophisticated
    public Object create(Object entity) {
        switch (entity.getClass().getName()) {
            case "model.Contact"    : return contactRepository.save((Contact)entity);
            case "model.Appointment": return appointmentRepository.save((Appointment)entity);
            case "model.Employee"   : return employeeRepository.save((Employee)entity);
            default : return null; // Could not find object
        }
    }
    
    @Override
    public List<? extends Object> read(EntityType entityType) {
        List<Object> list = new ArrayList<>();
        switch (entityType) {
            case CONTACT :{
                Optional<List<Contact>> contactList =
                        customQueryService.getAllContactsForOverview();
                if (contactList.isPresent()) list.addAll(contactList.get());
                break;
            }
            case APPOINTMENT : {
                Optional<List<Appointment>> appointmentList =
                        customQueryService.getAppointmentsBetween(entityType.getStartingDate(), entityType.getEndingDate());
                if (appointmentList.isPresent()) list.addAll(appointmentList.get());
                break;
            }
            default :
        }
        return list;
    }
    
    
    @Override
    public Object update(Object entity) {
        return create(entity);
    }
    
    @Override
    public void delete(Object entity) {
        switch (entity.getClass().getName()) {
            case "model.Contact" : {
                contactRepository.delete((Contact)entity);
                break;
            }
            case "model.Appointment" : {
                appointmentRepository.delete((Appointment)entity);
                break;
            }
            default : break; // Could not find object
        }
    }

}
