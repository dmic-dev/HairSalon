/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import lombok.Data;
import mic.dermitzakis.HairSalon.view.FxmlController;
import mic.dermitzakis.HairSalon.view.FxmlView;
import mic.dermitzakis.HairSalon.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
@Data
public class AppointmentOverviewController implements FxmlController{
    
    private final StageManager stageManager;
    
//    private MyLabel selectedLabel, focusedLabel, unfocusedLabel;
//    private MyChoiceBox focusedChoiceBox, unfocusedChoiceBox;
    
    @FXML
    BorderPane day_tab;
    @FXML
    BorderPane week_tab;

    @Autowired
    @Lazy // StageManager
    public AppointmentOverviewController(StageManager stageManager) {
        this.stageManager = stageManager;
    }
    
    
    
    @Override
    public void initialize() {
        setContent(FxmlView.DAY_VIEW, day_tab);
        setContent(FxmlView.WEEK_VIEW, week_tab);
    }
    
    private void setContent(FxmlView fxmlView, BorderPane tabView) {
        Parent root = stageManager.loadViewNodeHierarchy(fxmlView);
        tabView.setCenter(root);  // check! "root" becomes "content" check OK!
        ((Region)root).prefWidthProperty().bind(tabView.widthProperty());
        ((Region)root).prefHeightProperty().bind(tabView.heightProperty());
    }
    
}
