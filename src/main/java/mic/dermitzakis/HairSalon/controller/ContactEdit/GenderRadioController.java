/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javax.annotation.PostConstruct;
import lombok.Data;
import mic.dermitzakis.HairSalon.event.ContactEditEvent;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
@Data
public class GenderRadioController implements FxmlController{
    private final EventBus eventBus;
 
    @FXML private SplitPane genderRadio;
    @FXML private ToggleGroup genderToggleGroup;
    @FXML private Toggle maleToggle;
    @FXML private Toggle femaleToggle;
    @FXML private Toggle unspecifiedToggle;

    public GenderRadioController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    private void init(){
        eventBus.register(this);
    }
    
    @Subscribe
    public void setUserData(ContactEditEvent event) {
        Contact contact = event.getContact();
        switch (contact.getGender()) {
            case MALE : {genderToggleGroup.selectToggle(maleToggle); break;}
            case FEMALE : {genderToggleGroup.selectToggle(femaleToggle); break;}
            case UNSPECIFIED : {genderToggleGroup.selectToggle(unspecifiedToggle); break;}
        }
    }

    @Override
    public void initialize() {
    }

}
