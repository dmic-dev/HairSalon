/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
public class WeekOverviewController implements FxmlController{

    @FXML private SplitPane weekOverview;

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
