/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.view;

import mic.dermitzakis.HairSalon.function_enum.ViewService;
import mic.dermitzakis.HairSalon.function_enum.TestFxmlView;
import java.io.IOException;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {
    private final SpringFXMLLoader springFXMLLoader;
    @Autowired
    private ViewService viewImpl;
    
    @Getter 
    private boolean isPrimaryStage = true;
    private Stage primaryStage;
      
    private static final Logger LOG = getLogger(StageManager.class);
   
    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        primaryStage = stage;
    }
    
    public StageManager(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }
    
    public Stage newStage(FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view);
        
        Scene scene = new Scene(viewRootNodeHierarchy);
        String title = view.getTitle();
        Stage stage = (isPrimaryStage) ? primaryStage : new Stage(StageStyle.DECORATED);
        
        stage.setTitle(title);
        stage.setScene(scene);
//        stage.sizeToScene();
        
        return showStage(stage, view);
    }
    
    private Stage showStage(Stage stage, FxmlView view){
        try {
            if (isPrimaryStage){
                stage.setMaximized(true);
                stage.show();
                isPrimaryStage = !isPrimaryStage;
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                return stage;
            }
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title: " + view.getTitle(),  exception);
        }
        return null;
    }
    
    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @param fxmlView
     * @return Parent root node of the FXML document hierarchy
     */
    public Parent loadViewNodeHierarchy(FxmlView fxmlView) {
        Parent rootNode = null;
        String fxmlFilePath = fxmlView.getFileURL();
        try {
            rootNode = springFXMLLoader.load(fxmlView);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (IOException exception) {
            logAndExit("Unable to load FXML view: "+fxmlFilePath, exception);
        }
        return rootNode;
    }
    
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

}
