/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.stereotype.Controller;

/**
 * FXML Controller class
 *
 * @author mderm
 */
@Controller
public class ContactSideDetailsController implements FxmlController {

    @FXML
    private Text name_txt;
    @FXML
    private Text id_txt;
    @FXML
    private StackPane picture_area;
    @FXML
    private Text gender_txt;
    @FXML
    private Text dob_txt;
    @FXML
    private Text date_created_txt;
    @FXML
    private Text last_modified_txt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        // TODO
    }    
    
    private void asdsf(){
        
    }
    
}
