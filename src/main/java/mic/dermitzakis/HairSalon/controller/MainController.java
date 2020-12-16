/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mic.dermitzakis.HairSalon.dto.ContactOverviewDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.repository.CacheService;
import mic.dermitzakis.HairSalon.view.FxmlView;
import mic.dermitzakis.HairSalon.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 * 
 * Main view, (BorderPane) skeleton container 
 * that hosts (TableView) containers for data display
 */
//FxmlController

@Controller
public class MainController implements Initializable{
 
    private final ApplicationContext springContext;
    private final StageManager stageManager;
    private final CacheService dataLoaderService;
    private final DayOverviewController dayOverviewController;
    private final ContactOverviewController contactOverviewController;
    private final EditAppointmentController editAppointmentController;
    
    private static final Logger LOG = Logger.getLogger(MainController.class.getName());
    
    @FXML private BorderPane homeStage;
    @FXML private Accordion mainAccordion;
    @FXML private BorderPane userProfile;
    @FXML private BorderPane mainContentArea;
    
    @Autowired
    @Lazy // lazy since Stage for StageManager not available yet at initialization time 
    public MainController(ApplicationContext springContext, StageManager stageManager, CacheService dataLoaderService, DayOverviewController dayOverviewController, ContactOverviewController contactOverviewController, EditAppointmentController newAppointmentController) {
        this.springContext = springContext;
        this.stageManager = stageManager;
        this.dataLoaderService = dataLoaderService;
        this.dayOverviewController = dayOverviewController;
        this.contactOverviewController = contactOverviewController;
        this.editAppointmentController = newAppointmentController;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataLoaderService.loadDataFromRepository();
    }

    @FXML
    public void newAppointmentButtonHandler(){
        stageManager.newStage(FxmlView.EDIT_APPOINTMENT).showAndWait();
    }

    @FXML
    public void newAppointmentByContactsButtonHandler(){
        Stage newStage = stageManager.newStage(FxmlView.EDIT_APPOINTMENT);
        ContactOverviewDto selectedItem = contactOverviewController.getContactTable().getSelectionModel().getSelectedItem();          
        setAppointmentData(selectedItem.getFirstName().getText(), selectedItem.getLastName().getText());
        newStage.showAndWait();
    }
    
    @FXML
    public void nextAppointmentButtonHandler(){
        
    }
    
    @FXML
    public void newContactButtonHandler(){
        stageManager.newStage(FxmlView.EDIT_CONTACT).showAndWait();
    }
    
    private void setAppointmentData(String firstName, String lastName){
        Appointment appointment = springContext.getBean(Appointment.class);
        Contact contact = springContext.getBean(Contact.class);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        appointment.setContact(contact);
        editAppointmentController.setUserData(appointment);
//        LocalTime localTime = LocalTime.now();
//        newAppointmentController.getHourSpinner().getValueFactory().setValue(localTime.getHour());
//        newAppointmentController.getMinuteSpinner().getValueFactory().setValue(localTime.getMinute());
    }

    /**
     * Displays content
     */
    @FXML
    private void showContent(){
        if (mainAccordion.getExpandedPane() != null) {
            switch (mainAccordion.getExpandedPane().getText()) {
                case "Ραντεβού" : { showAppointmentsPerDay(); break; }
                case "Πελάτες"   : { showContactsTable(); break; }
                case "Κάδος ανακύκλωσης" : { showRecycleBin(); break; }
                default : LOG.log(Level.SEVERE, "No such Accordion title: {0}",mainAccordion.getExpandedPane().getText());
            }
        } else showEmptyContentLabel();
    }

    /**
     * Displays Appointments per day
     */
    private void showAppointmentsPerDay() {
        setContent(FxmlView.APPOINTMENT_VIEW);
    }
    
    /**
     * Displays contacts in TableView
     */
    private void showContactsTable() {
        setContent(FxmlView.CONTACT_VIEW);
    }
    
    /**
     * Displays recycle bin content
     * Not yet implemented
     */
    private void showRecycleBin() {
        showValidContent();
    }

    /**
     * 
     * @param fxmlView 
     * 
     * Displays user data content, like customers and appointments, 
     * in the center of a BorderPane.
     * 
     * Notes: Should probably store (Parent root) in a data structure
     * for keeping track of the view history, in case i want to show 
     * previus state of data when returning in the same view from another view.
     * I dont know if it works...
     * -- worths to give it a try! --
     */
    private void setContent(FxmlView fxmlView) {
        Parent root = stageManager.loadViewNodeHierarchy(fxmlView);
        if (root == null) System.out.println("\nRoot is NULL\n");
        mainContentArea.setCenter(root);  // check! "root" becomes "content" check OK!
//        ((Region)root).prefWidthProperty().bind(mainContentArea.widthProperty());
//        ((Region)root).prefHeightProperty().bind(mainContentArea.heightProperty());
    }

    
    
    /**
     * TEST METHODS    
    */
    private void showEmptyContentLabel() { 
        Label label = new Label("<Εμφάνιση περιεχομένων>");
        label.setFont(new Font(12));
        label.setTextFill(Paint.valueOf("#a0acd0"));
        mainContentArea.setCenter(label);
    }
    
    private void showValidContent(){
        Label label = new Label("<View Selected>");
        label.setFont(new Font(12));
        label.setTextFill(Paint.valueOf("red"));
        mainContentArea.setCenter(label);
    }

}


