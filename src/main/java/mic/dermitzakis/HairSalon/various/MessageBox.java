/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.various;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class MessageBox {

    public static void showInformation(String header, String body) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Μήνυμα");
            alert.setHeaderText(header);
            alert.setContentText(body);
            alert.showAndWait();
    }

}
