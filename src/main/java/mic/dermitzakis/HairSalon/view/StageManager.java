/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.view;

import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

    private final ApplicationContext springContext;
    private final SpringFXMLLoader springFXMLLoader;
    
    private static final Logger LOG = getLogger(StageManager.class);
    private Stage primaryStage;
    private boolean isPrimaryStage = true;
            
    @Autowired
    public StageManager(ApplicationContext springContext, SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springContext = springContext;
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }
    
    @Autowired
    public StageManager(ApplicationContext springContext, SpringFXMLLoader springFXMLLoader) {
        this.springContext = springContext;
        this.springFXMLLoader = springFXMLLoader;
    }
    
    public void newStage(final FxmlView view){
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view);
        Scene scene = new Scene(viewRootNodeHierarchy);
        String title = view.getTitle();
        Stage stage = (isPrimaryStage) ? primaryStage : new Stage(StageStyle.DECORATED);
        
        stage.setTitle(title);
        stage.setScene(scene);
        stage.sizeToScene();
        
        showStage(stage, view);
    }
    
    private void showStage(Stage stage, FxmlView view){
        try {
            if (isPrimaryStage){ // list.isFirst()
                stage.show();
                isPrimaryStage = !isPrimaryStage;
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.showAndWait();
            }
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + view.getTitle(),  exception);
        }
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
        String fxmlFilePath = fxmlView.getFxmlFile();
        try {
            rootNode = springFXMLLoader.load(fxmlView);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view: " + fxmlFilePath, exception);
        }
        return rootNode;
    }
    
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }
}
