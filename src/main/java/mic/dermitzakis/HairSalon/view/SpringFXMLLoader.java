/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.view;

/**
 *
 * @author mderm
 */
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Will load the FXML hierarchy as specified in the load method and register
 * Spring as the FXML Controller Factory. Allows Spring and Java FX to coexist
 * once the Spring Application context has been bootstrapped.
 */
@Component
public class SpringFXMLLoader {
    private final ResourceBundle resourceBundle;
    private final ApplicationContext springContext;

    @Autowired
    public SpringFXMLLoader(ApplicationContext springContext, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.springContext = springContext;
    }

    public Parent load(FxmlView fxmlView) throws IOException {      
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlView.getFileURL()));
        loader.setControllerFactory(springContext::getBean);
        loader.setResources(resourceBundle);
        return loader.load();
    }
    
}
//(p) -> springContext.getBean(fxmlView.getControllerName())