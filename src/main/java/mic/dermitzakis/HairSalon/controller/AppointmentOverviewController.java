/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import lombok.Data;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
@Data
public class AppointmentOverviewController implements FxmlController{
    
    @FXML private DayOverviewController dayOverviewController;
    @FXML private WeekOverviewController weekOverviewController;
    
    @FXML private Tab dayTab;
    @FXML private Tab weekTab;

    @Override
    public void initialize() {
    }
    
}
