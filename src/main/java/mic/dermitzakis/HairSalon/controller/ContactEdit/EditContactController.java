/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import com.google.common.eventbus.EventBus;
import java.util.List;
import mic.dermitzakis.HairSalon.view.FxmlController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.business.Validation;
import mic.dermitzakis.HairSalon.controller.ContactOverviewController;
import mic.dermitzakis.HairSalon.event.ContactEditEvent;
import mic.dermitzakis.HairSalon.model.Gender;
import mic.dermitzakis.HairSalon.services.ContactService;
import mic.dermitzakis.HairSalon.various.ListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * FXML Controller class
 *
 * @author mderm
 */

/**
 * a pile of bull
 * 
 * @author mderm
 */

@Controller
@Data
public class EditContactController implements FxmlController {
    private final ApplicationContext springContext;
    private final EventBus eventBus;
    private final ContactEditEvent contactEditEvent;
    private final ContactService contactService;
    private final ContactOverviewController contactOverviewController;
    private final List<Contact> contactPool;
    private final ListIterator<Contact> listIterator;
    
    @FXML private final MainContentController mainContentController;
    
    @FXML private AnchorPane container;
    @FXML private HBox saveCancelControls;
    @FXML private Button saveAndExitButton;
    @FXML private Button cancelButton;
    @FXML private Label dialogueLabel;
    @FXML private Label messageLabel;
    @FXML private Hyperlink invertHyperlink;
    @FXML private Pane messageContainer;
    
    
    @Autowired
    public EditContactController(ApplicationContext springContext, EventBus eventBus, ContactEditEvent contactEditEvent, ContactService contactService, MainContentController mainContentController, ContactOverviewController contactOverviewController) {
        this.springContext = springContext;
        this.eventBus = eventBus;
        this.contactEditEvent = contactEditEvent;
        this.contactService = contactService;
        this.mainContentController = mainContentController;
        this.contactOverviewController = contactOverviewController;
        this.contactPool = mainContentController.getContactPool();
        this.listIterator = mainContentController.getListIterator();

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        setEventHandlers();
    }

/**
 * This is a bunch of bull // maybe better now
 * @param event 
 */
    private void saveAndExitButtonHandler(ActionEvent event) {
        Contact contact = mainContentController.getUserInput();
        boolean validInput = Validation.checkContactInput(contact);
        if (validInput) { 
            if (listIterator.isExist(contact)) {
                setContact(contact);
            } else {
                addContact(contact);
            }
        }
        persistData();
        resetContactPool();
        exitEditContactForm();
    }

    private void resetContactPool() {
        contactPool.clear();
        listIterator.reset();
    }

    private void cancelButtonHandler(ActionEvent event) { // Sorted !!!
        ButtonType confirm = new ButtonType("Ναι", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Όχι", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType result = confirm;
        Alert alert = new Alert(Alert.AlertType.WARNING, "    Να γίνει έξοδος ;\n\n\n", confirm, cancel);
        alert.setHeaderText("    Οι επαφές που καταχωρίσατε θα χαθούν.");
        if (!contactPool.isEmpty()){
            alert.showAndWait();
            result = alert.getResult();
        }
        if (result == confirm) {
            resetContactPool();
            exitEditContactForm();
        }
    }

//    private void invertDeleteEventListener(ActionEvent event){
//        AddressTabController addressTabController = springContext.getBean(AddressTabController.class);
//        addressTabController.invertDelete();
//    }
//    
    private void clearFields() { // Perhaps Should remove FXML Button from view
        Contact contact = springContext.getBean(Contact.class);
        contact.setGender(Gender.FEMALE);
        contactEditEvent.setContact(contact);
        eventBus.post(contactEditEvent);
//      picture_Area;
    }

    private void persistData() {
        contactPool.forEach(contactService::save);
        if (!contactPool.isEmpty()) {
            contactOverviewController.insertDataIntoTable();
            mainContentController.setLastContact(contactPool.get(contactPool.size()-1));
        }
    }

    private void addContact(Contact contact) {
       listIterator.add(contact);
    }

    private void setContact(Contact contact) {
        if (!contactPool.isEmpty()) {
            listIterator.set(contact);
        }
    }

    /**
     * to atCursor Fixed.
     *
     * @return
     */
    private void exitEditContactForm() {
        Stage stage = (Stage) dialogueLabel.getScene().getWindow();
        stage.close();
    }

//    private boolean isNotEmptyContact(Contact contact) {
//        String firstName = contact.getFirstName();
//        return firstName != null && !firstName.isBlank();
//    }

    private void setEventHandlers() {
        saveAndExitButton.setOnAction(this::saveAndExitButtonHandler);
        cancelButton.setOnAction(this::cancelButtonHandler);
//        invertHyperlink.setOnAction(this::invertDeleteEventListener);
    }
    
}
