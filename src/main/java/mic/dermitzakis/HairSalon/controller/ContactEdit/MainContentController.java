/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import mic.dermitzakis.HairSalon.various.MessageBox;
import com.google.common.eventbus.EventBus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mic.dermitzakis.HairSalon.business.Validation;
import mic.dermitzakis.HairSalon.event.ContactEditEvent;
import mic.dermitzakis.HairSalon.model.Address;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.ContactBuilder;
import mic.dermitzakis.HairSalon.model.Email;
import mic.dermitzakis.HairSalon.model.Employee;
import mic.dermitzakis.HairSalon.model.Gender;
import mic.dermitzakis.HairSalon.model.Phone;
import mic.dermitzakis.HairSalon.various.ListIterator;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * FXML Controller class
 *
 * @author mderm
 */
@Controller
@Data
public class MainContentController implements FxmlController {

    private final ApplicationContext springContext;
    private final EventBus eventBus;
    private final ContactEditEvent contactEditEvent;
    private final List<Contact> contactPool;
    private final ListIterator<Contact> listIterator;
    
    @Getter @Setter private Contact lastContact;
    private final double MESSAGE_DURATION = 4000;
    private boolean firstTime = true;
    private FadeTransition fadeOutMessage;    
    
    @FXML private final ContactDataController contactDataController;
    @FXML private GenderRadioController genderRadioController;
    @FXML private GeneralDataController generalDataController;
    
    @FXML private AnchorPane mainContent;
    @FXML private StackPane picture_Area;
    @FXML private Button clearFieldsButton;
    @FXML private Button saveButton;
    @FXML private Button previousRecordButton;
    @FXML private Button nextRecordButton;
    @FXML private Button firstRecordButton;
    @FXML private Button lastRecordButton;
    @FXML private Label message_lbl;

    public MainContentController(ApplicationContext springContext, EventBus eventBus, ContactEditEvent contactEditEvent, ContactDataController contactDataController) {
        this.springContext = springContext;
        this.eventBus = eventBus;
        this.contactEditEvent = contactEditEvent;
        this.contactDataController = contactDataController;
        contactPool = new ArrayList<>();
        listIterator = springContext.getBean(ListIterator.class, contactPool);
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        setEventHandlers();
        lastContact = null;
    }    
    
    private void clearFieldsButtonHandler(ActionEvent event){
        clearFields();
    }

    private void saveButtonHandler(ActionEvent event) {
        Contact contact = getUserInput();
        boolean validInput = Validation.checkContactInput(contact);
        if (validInput) {
            if (!listIterator.isExist(contact)) {
                listIterator.add(contact);
                clearFields();
                firstName().requestFocus();
            } else MessageBox.showInformation("Τα στοιχεία που προσπαθείτε να αποθηκεύσετε υπάρχουν ήδη", getMessageBody());
        } else showMessage();
    }

    private FadeTransition showMessage() {
        EditContactController editContactController = springContext.getBean(EditContactController.class);
        Node node = editContactController.getMessageContainer();
        editContactController.getMessageLabel().setText("Παρακαλώ συμπληρώστε το πεδίο: (Όνομα)");
        editContactController.getInvertHyperlink().setText("OK");
        editContactController.getInvertHyperlink().setOnAction(this::hyperlinkActionHandler);
        setMessageVisible(true);
        fadeOutMessage = new FadeTransition(Duration.millis(MESSAGE_DURATION), node);
        fadeOutMessage.setOnFinished((t) -> node.setOpacity(0));
        fadeOutMessage.setFromValue(1);
        fadeOutMessage.setToValue(0);
        fadeOutMessage.play();
        return fadeOutMessage;
    }

    public void hyperlinkActionHandler(ActionEvent event) {
        fadeOutMessage.jumpTo(Duration.millis(MESSAGE_DURATION));
        setMessageVisible(false);
    }
    
    private void setMessageVisible(boolean visible) {
        EditContactController editContactController = springContext.getBean(EditContactController.class);
        editContactController.getMessageContainer().setVisible(visible);
    }

    private TextField firstName() {
        return generalDataController.getFirstName_txtf();
    }

    private void previousRecordButtonHandler(ActionEvent event) {
        firstTime = true;
        Contact userInput = getUserInput();
        if (userInput.getFirstName().isBlank()){
            Optional<Contact> contactOpt = listIterator.last();
            if (contactOpt.isPresent()) setFields(contactOpt.get());
        } else {
            Optional<Contact> contactOpt = listIterator.previous();
            if (contactOpt.isPresent()) setFields(contactOpt.get());
        }
    }

    private void nextRecordButtonHandler(ActionEvent event) {
        firstTime = true;
        Optional<Contact> contact = listIterator.next();
        if (contact.isPresent()) setFields(contact.get());
        else clearFields();
    }

    private void firstRecordButtonHandler(ActionEvent event) {
        firstTime = true;
        Optional<Contact> contact = listIterator.first();
        if (contact.isPresent()) 
            setFields(contact.get());
    }

/**
 * a pile of bull.
 * cannot swhitch permenantlly
 * @param event 
 */
    private void lastRecordButtonHandler(ActionEvent event) {
        Optional<Contact> last = listIterator.last();
        if (last.isPresent()){
            if (isUserInputEquals(last.get())){
                firstTime = !firstTime;
                clearFields();
            } else if (firstTime) setFields(last.get());
        }
    }

    private boolean isUserInputEquals(Contact contact) {
        return getUserInput().getFirstName().equals(contact.getFirstName());
    }

    public void setFields(Contact contact){
        contactEditEvent.setContact(contact);
        eventBus.post(contactEditEvent);
    }
    
    private void clearFields() {
        Contact contact = getEmptyContact();
        setFields(contact);
    }

    private Contact getEmptyContact() throws BeansException {
        Contact contact = springContext.getBean(Contact.class);
        contact.setGender(Gender.FEMALE);
        return contact;
    }

    public Contact getUserInput() {
        ContactBuilder contactBuilder = springContext.getBean(ContactBuilder.class);
        String selectedGender = getGenderId();
        List<Phone> phones = null;
        List<Address> addresses = null;
        List<Email> emails = null;
        Employee createdBy = null;
        Employee lastModifiedBy = null;
        // etc

        Contact contact = contactBuilder
                .setPicture(null)
                .setGender(Gender.valueOf(selectedGender))
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setProfession(getProfession())
                .setCompany(getCompany())
                .setPosition(getPosition())
                .setDateOfBirth(LocalDate.EPOCH)
                .setDateCreated(LocalDateTime.now())
                .setCreatedBy(createdBy)
                .setLastModifiedDate(LocalDateTime.now())
                .setLastModifiedBy(lastModifiedBy)
                .setNotes(contactDataController.persistNotes())
                .setPhones(phones)
                .setAddresses(addresses)
                .setEmails(emails)
                .build();

        return contact;
    }

    private String getGenderId() {
        return ((RadioButton) genderRadioController.getGenderToggleGroup().getSelectedToggle()).getId();
    }

    private String getPosition() {
        String positionText = generalDataController.getPosition_txtf().getText();
        return (positionText != null) ? positionText.strip() : "";
    }

    private String getCompany() {
        String companyText = generalDataController.getCompany_txtf().getText();
        return (companyText != null) ? companyText.strip() : "";
    }

    private String getProfession() {
        String professionText = generalDataController.getProfession_txtf().getText();
        return (professionText != null) ? professionText.strip() : "";
    }

    private String getLastName() {
        String lastNameText = generalDataController.getLastName_txtf().getText();
        return (lastNameText != null) ? lastNameText.strip() : "";
    }

    private String getFirstName() {
        String firstNameText = generalDataController.getFirstName_txtf().getText();
        return (firstNameText != null) ? firstNameText.strip() : "";
    }

    private void setEventHandlers() {
        clearFieldsButton.setOnAction(this::clearFieldsButtonHandler);
        saveButton.setOnAction(this::saveButtonHandler);
        previousRecordButton.setOnAction(this::previousRecordButtonHandler);
        nextRecordButton.setOnAction(this::nextRecordButtonHandler);
        firstRecordButton.setOnAction(this::firstRecordButtonHandler);
        lastRecordButton.setOnAction(this::lastRecordButtonHandler);
    }

//    private Hyperlink getMessageHyperlink(){
//        EditContactController editContactController = springContext.getBean(EditContactController.class);
//        return editContactController.getInvertHyperlink();
//    }
    
    private String getMessageBody() {
        Contact contact = getUserInput();
        return String.format("\tΟνοματεπώνυμο : %s", contact.getFullName());
    }

}
