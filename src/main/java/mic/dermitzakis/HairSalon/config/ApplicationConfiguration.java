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
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import mic.dermitzakis.HairSalon.event.CustomChoiceBox;
import mic.dermitzakis.HairSalon.event.CustomLabel;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
//import mic.dermitzakis.HairSalon.services.AuthenticationManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    SpringFXMLLoader springFXMLLoader;
    @Autowired 
    ApplicationContext springContext;
//    /**
//     * Useful when dumping stack trace to a string for logging.
//     * @return ExceptionWriter contains logging utility methods
//     */
//    @Bean
//    @Scope("prototype")
//    public ExceptionWriter exceptionWriter() {
//        return new ExceptionWriter(new StringWriter());
//    }
//
    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("bundle");
    }
    
    @Bean
    @Lazy(value = true) //Stage only created after Spring context bootstap
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(springContext, springFXMLLoader, stage);
    }
    
    @Bean
//    @Lazy
    public StageManager stageManager() throws IOException {
        return new StageManager(springContext, springFXMLLoader);
    }
    
//    @Bean
//    public AuthenticationManager authenticationManager(){
//        return new AuthenticationManagerImpl();
//    }
    
    @Bean
    @Scope("prototype")
    public CustomLabel customLabel() {
        return new CustomLabel();
    }
    
    @Bean
    @Scope("prototype")
    public CustomLabel customLabel(String text) {
        return new CustomLabel(text);
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
