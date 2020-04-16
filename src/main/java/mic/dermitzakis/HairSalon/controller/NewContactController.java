/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.view.FxmlController;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import mic.dermitzakis.HairSalon.dto.ContactInfoDto;
import mic.dermitzakis.HairSalon.model.Address;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.ContactBuilder;
import mic.dermitzakis.HairSalon.model.Email;
import mic.dermitzakis.HairSalon.model.Phone;
import mic.dermitzakis.HairSalon.business.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * FXML Controller class
 *
 * @author mderm
 */
@Controller
public class NewContactController implements FxmlController {

    private ApplicationContext springContext;
    
    @FXML
    private Label title_lbl;
    @FXML
    private TextArea notes_txtArea;
    @FXML
    private TextField firstName_txtf;
    @FXML
    private TextField lastName_txtf;
    @FXML
    private TextField profession_txtf;
    @FXML
    private TextField company_txtf;
    @FXML
    private TextField position_txtf;
    @FXML
    private ToggleGroup gender_tglb;
    @FXML
    private Label message_lbl;
    
    
    @Autowired
    public NewContactController(ApplicationContext springContext) {
        this.springContext = springContext;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        firstName_txtf.setText(null);
        lastName_txtf.setText(null);
        profession_txtf.setText(null);
        company_txtf.setText(null);
        position_txtf.setText(null);
        notes_txtArea.setText(null);
    }    
    
    @FXML
    private void save(){
        ContactBuilder contactBuilder = springContext.getBean(ContactBuilder.class);
        ContactInfoDto contactInfoDto = springContext.getBean(ContactInfoDto.class);
        List<Phone> phones = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        List<Email> emails = new ArrayList<>();
        // etc
        Contact contact = contactBuilder
            .setFirstName((firstName_txtf.getText() != null) ? firstName_txtf.getText().strip() : null)
            .setLasttName(lastName_txtf.getText())
            .setProfession(profession_txtf.getText())
            .setCompany(company_txtf.getText())
            .setPosition(position_txtf.getText())
//            .setNotes(notes_txtArea.getText())
            .build();
        contactInfoDto.setContact(contact);
        contactInfoDto.setPhones(phones);
        contactInfoDto.setAddresses(addresses);
        contactInfoDto.setEmails(emails);
        Validation validation = springContext.getBean(Validation.class);
        validation.checkContactInput(contactInfoDto);
    }
    
    @FXML
    private void saveAndExit(){
        
    }
    
}
