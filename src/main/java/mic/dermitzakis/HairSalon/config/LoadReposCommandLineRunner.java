/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.config;

import java.util.List;
import static java.util.stream.Collectors.toList;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.repository.ContactRepository;
import mic.dermitzakis.HairSalon.database.SampleDataAccessManager;
import mic.dermitzakis.HairSalon.model.EntityType;
import mic.dermitzakis.HairSalon.model.Picture;
import mic.dermitzakis.HairSalon.repository.PictureRepository;


/**
 *
 * @author mderm
 */
@Component
public class LoadReposCommandLineRunner implements CommandLineRunner{
    
    private final SampleDataAccessManager dataService;
    private final PictureRepository pictureRepository;
    private final ContactRepository contactRepository;
    private final AppointmentRepository appointmentRepository;

    private final List<Appointment> appointmentList;
    private final List<Contact> contactList;
    private final List<Picture> pictureList;
    

    @Autowired
    public LoadReposCommandLineRunner(SampleDataAccessManager dataService, 
            PictureRepository pictureRepository, 
            ContactRepository contactRepository,
            AppointmentRepository appointmentRepository) {
        this.dataService = dataService;
        this.pictureRepository = pictureRepository;
        this.contactRepository = contactRepository;
        this.appointmentRepository = appointmentRepository;
        pictureList = (List<Picture>)this.dataService.read(EntityType.PICTURE).stream().collect(toList());
        contactList = (List<Contact>)this.dataService.read(EntityType.CONTACT).stream().collect(toList());
        appointmentList = (List<Appointment>)this.dataService.read(EntityType.APPOINTMENT).stream().collect(toList());
    }
    
    @Override
    public void run(String... args) throws Exception {
        pictureList.forEach(pictureRepository::save);
        contactList.forEach(contactRepository::save);
        appointmentList.forEach(appointmentRepository::save);
    }
    
}

//    private static final Logger LOG = LoggerFactory.getLogger(LoadReposCommandLineRunner.class);
//            LOG.info("Contact is created : " + contact.getDetails());            
//            LOG.info("Appointment is created : " + appointment.getDetails());            