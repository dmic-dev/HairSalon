/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mic.dermitzakis.HairSalon.view.FxmlController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mic.dermitzakis.HairSalon.services.DataLoaderService;
import mic.dermitzakis.HairSalon.view.FxmlView;
import mic.dermitzakis.HairSalon.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */

@Controller // another @Component
public class MainController implements FxmlController{
 
    private final ApplicationContext springContext;
    private final StageManager stageManager;
    private final DataLoaderService dataLoaderService;

    private static final Logger LOG = Logger.getLogger(MainController.class.getName());
    
    @FXML private BorderPane homeStage;
    @FXML private BorderPane content;
    @FXML private Accordion mainAccordion;
    
    private final BorderPane initialContent = content;
    
    @Autowired
    @Lazy // lazy since Stage for StageManager not available yet at initialization time 
    public MainController(ApplicationContext springContext, StageManager stageManager, DataLoaderService dataLoaderService){
        this.springContext = springContext;
        this.stageManager = stageManager;
        this.dataLoaderService = dataLoaderService;
    }

//////////////  INITIALIZATION  ////////////    
    @Override
    public void initialize() {
        dataLoaderService.loadDataFromRepository();
    }
    
/////////////////////////////////////////////
    
    public BorderPane getContent(){
        return content;
    }
    
    @FXML
    public void newAppointment(){
        stageManager.newStage(FxmlView.NEW_APPOINTMENT);
    }
    
    @FXML
    public void newContact(){
        stageManager.newStage(FxmlView.NEW_CONTACT);
    }
    
    @FXML
    private void showContent(){
        if (mainAccordion.getExpandedPane() != null) {
            switch (mainAccordion.getExpandedPane().getText()) {
                case "Ραντεβού" : { showAppointmentsByDay(); break; }
                case "Επαφές"    : { showContactsTable(); break; }
                case "Κάδος ανακύκλωσης" : { showRecycleBin(); break; }
                default : LOG.log(Level.SEVERE, "No such Accordion title: {0}",mainAccordion.getExpandedPane().getText());
            }
        } else showEmptyContent();
    }

    private void showAppointmentsByDay() {
        setContent(FxmlView.APPOINTMENT_VIEW);
    }
    
    private void showAppointments(){
        showAppointmentsByDay();
    }
    
    private void showContactsTable() {
        setContent(FxmlView.CONTACT_VIEW);
    }

    private void showRecycleBin() {
        showValidContent();
    }

    /////////////   TEST METHODS    /////////////////
    
    private void showEmptyContent() { 
        Label label = new Label("Εμφάνιση περιεχομένων");
        label.setFont(new Font(12));
        label.setTextFill(Paint.valueOf("#a0acd0"));
        homeStage.setCenter(label);
    }
    
    private void showValidContent(){
        Label label = new Label("View Selected");
        label.setFont(new Font(12));
        label.setTextFill(Paint.valueOf("red"));
        homeStage.setCenter(label);
    }

    private void setContent(FxmlView fxmlView) {
        Parent root = stageManager.loadViewNodeHierarchy(fxmlView);
        homeStage.setCenter(root);  // check! "root" becomes "content" check OK!
        ((Region)root).prefWidthProperty().bind(content.widthProperty());
        ((Region)root).prefHeightProperty().bind(content.heightProperty());
    }
    
}


