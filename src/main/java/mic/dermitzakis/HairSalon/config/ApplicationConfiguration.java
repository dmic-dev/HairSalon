/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.config;

//import com.mvp.java.logging.ExceptionWriter;
import com.google.common.eventbus.EventBus;
import mic.dermitzakis.HairSalon.view.SpringFXMLLoader;
import mic.dermitzakis.HairSalon.view.StageManager;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import mic.dermitzakis.HairSalon.custom.ContactTableLabel;
import mic.dermitzakis.HairSalon.custom.CustomChoiceBox;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.custom.TimeLabel;
import mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus;
//import mic.dermitzakis.HairSalon.services.AuthenticationManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;

@Configuration
//@ComponentScan(basePackages = {mic.dermitzakis.HairSalon})
//@EnableJpaRepositories
public class ApplicationConfiguration {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

//    /**
//     * Useful when dumping stack trace to a string for logging.
//     * @return ExceptionWriter contains logging utility methods
//     */
//    @Bean
//    @Scope("prototype")
//    public ExceptionWriter exceptionWriter() {
//        return new ExceptionWriter(new StringWriter());
//    }

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("bundle");
    }
    
    @Bean
    @Lazy(value = true) //Stage is created after Application Context initialized
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(springFXMLLoader, stage);
    }
    
    @Bean
    @Lazy
    public StageManager stageManager() throws IOException {
        return new StageManager(springFXMLLoader);
    }
    
//    @Bean
//    public AuthenticationManager authenticationManager(){
//        return new AuthenticationManagerImpl();
//    }
    
    @Bean
    @Scope("prototype")
    public DayTableLabel dayTableLabel() {
        return new DayTableLabel();
    }
    
    @Bean
    @Scope("prototype")
    public TimeLabel timeLabel(LocalTime localTime, String text) {
        return new TimeLabel(localTime, text);
    }
    
    @Bean
    @Scope("prototype")
    public DayTableLabel dayTableLabel(String text) {
        return new DayTableLabel(text);
    }
    
    @Bean
    @Scope("prototype")
    public ContactTableLabel contactTableLabel() {
        return new ContactTableLabel();
    }
    
    @Bean
    @Scope("prototype")
    public CustomChoiceBox customChoiceBox(ObservableList<AppointmentStatus> list) {
        return new CustomChoiceBox(list);
    }
    
    @Bean
//    @Scope("singleton")
    public EventBus eventBus() {
        return new EventBus("mainEventBus");
    }    
}
