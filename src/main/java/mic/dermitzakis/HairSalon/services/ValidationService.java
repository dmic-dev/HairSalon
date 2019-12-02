/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.services;

import javafx.scene.control.Alert;
import mic.dermitzakis.HairSalon.dto.ContactDetailsDto;
import org.springframework.stereotype.Service;

/**
 *
 * @author mderm
 */
@Service
public class ValidationService /*implements Validator*/{
    
    private String message;
    private boolean isValid;

    public boolean validate(ContactDetailsDto contactDetailsDto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        message = "";
        isValid = true;
        String firstName = contactDetailsDto.getContact().getFirstName();
        if (firstName == null || firstName.equals("")) {
            message += "    - Όνομα\n";
            isValid = false;
        }
        
        if (firstName == null || firstName.equals("")) {
            message += "    - Τηλέφωνο";
            isValid = false;
        }
        
        if ( ! isValid ) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Παρακαλώ συμπληρώστε τα πεδία :");
            alert.setContentText(message);
            alert.showAndWait();
        }
        
        return isValid;
    }

}
//JOptionPane.showMessageDialog(null, message);