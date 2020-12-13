/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javax.annotation.PostConstruct;
import lombok.Data;
import mic.dermitzakis.HairSalon.event.ContactEditEvent;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
@Data
public class GeneralDataController implements FxmlController{
    private final ApplicationContext springContext;
    private final EventBus eventBus;

    @FXML private SplitPane generalData;
    @FXML private TextField firstName_txtf;
    @FXML private TextField lastName_txtf;
    @FXML private TextField profession_txtf;
    @FXML private TextField company_txtf;
    @FXML private TextField position_txtf;


    @Override
    public void initialize() {
    }
    
    @PostConstruct
    public void Init(){
        eventBus.register(this);
    }
    
    @Subscribe
    public void setUserData(ContactEditEvent event) {
        Contact contact = event.getContact();
        firstName_txtf.setText(contact.getFirstName());
        lastName_txtf.setText(contact.getLastName());
        profession_txtf.setText(contact.getProfession());
        company_txtf.setText(contact.getCompany());
        position_txtf.setText(contact.getPosition());
    }

}
