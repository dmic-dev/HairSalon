/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.business;

import javafx.scene.control.Alert;
import mic.dermitzakis.HairSalon.dto.ContactInfoDto;
import org.springframework.stereotype.Service;

/**
 *
 * @author mderm
 */
@Service
public class Validation /*implements Validator*/{
    
    public boolean checkContactInput(ContactInfoDto contactInfoDto){
        String message = "";
        boolean isValid = true;
        String firstName = contactInfoDto.getContact().getFirstName();
        if (firstName == null || firstName.equals("")) {
            message += "    - Όνομα\n";
            isValid = false;
        }
        
        if (firstName == null || firstName.equals("")) {
            message += "    - Τηλέφωνο\n";
            isValid = false;
        }
        
        if ( !isValid ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Παρακαλώ συμπληρώστε τα πεδία :");
            alert.setContentText(message);
            alert.showAndWait();
        }
        
        return isValid;
    }

}
//JOptionPane.showMessageDialog(null, message);