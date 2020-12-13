/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.view;

import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public enum FxmlView {

    MAIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("main.view.title");
        }

        @Override
        public String getFileURL() {
            return "/fxml/Main.fxml";
        }

    }, LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFileURL() {
            return "/fxml/Login.fxml";
        }

    }, EDIT_APPOINTMENT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("appointment.view.edit.title");
        }

        @Override
        public String getFileURL() {
            return "/fxml/appointment/edit/EditAppointment.fxml";
        }

    }, EDIT_CONTACT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("contact.view.edit.title");
        }

        @Override
        public String getFileURL() {
            return "/fxml/contact/edit/EditContact.fxml";
        }

    }, CONTACT_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("contact.view.title");
        }

        @Override
        public String getFileURL() {
            return "/fxml/contact/overview/ContactOverview.fxml";
        }

    }, APPOINTMENT_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("appointment.view.title");
        }

        @Override
        public String getFileURL() {
            return "/fxml/appointment/overview/AppointmentOverview.fxml";
        }

    };
    
    public abstract String getTitle();
    public abstract String getFileURL();
    
    
    public String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("bundle").getString(key);
    }

}

