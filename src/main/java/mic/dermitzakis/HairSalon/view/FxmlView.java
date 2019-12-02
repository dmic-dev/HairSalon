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
            return getStringFromResourceBundle("main.app.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Main.fxml";
        }

        @Override
        public String getControllerName() {
            return "mainController";
        }

    }, LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }

        @Override
        public String getControllerName() {
            return "loginController";
        }

    }, NEW_APPOINTMENT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("appointment.view.edit.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/NewAppointment.fxml";
        }

        @Override
        public String getControllerName() {
            return "newAppointmentController";
        }
    }, NEW_CONTACT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("contact.view.edit.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/NewContact.fxml";
        }

        @Override
        public String getControllerName() {
            return "newContactController";
        }
    }, CONTACT_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("contact.view.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/ContactOverview.fxml";
        }

        @Override
        public String getControllerName() {
            return "contactOverviewController";
        }
    }, APPOINTMENT_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("appointment.view.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/AppointmentOverview.fxml";
        }

        @Override
        public String getControllerName() {
            return "appointmentOverviewController";
        }
    }, DAY_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("appointment.view.day.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/DayOverview.fxml";
        }

        @Override
        public String getControllerName() {
            return "dayOverviewController";
        }
    }, WEEK_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("appointment.view.week.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/WeekOverview.fxml";
        }

        @Override
        public String getControllerName() {
            return "weekOverviewController";
        }
    };
    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    public abstract String getControllerName();
    
    
    public String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("bundle").getString(key);
    }

}

