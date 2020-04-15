/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
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
    
    @FXML Tab dayTab;
    @FXML Tab weekTab;

    @Autowired
    @Lazy // StageManager
    public AppointmentOverviewController(StageManager stageManager) {
        this.stageManager = stageManager;
    }
    
    @Override
    public void initialize() {
        setContent(FxmlView.DAY_VIEW, dayTab);
        setContent(FxmlView.WEEK_VIEW, weekTab);
    }
    
    private void setContent(FxmlView fxmlView, Tab tab) {
        Parent root = stageManager.loadViewNodeHierarchy(fxmlView);
        tab.setContent(root);
    }
    
}
