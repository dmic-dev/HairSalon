/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.time.LocalTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentTimeIndicatorController extends Group implements FxmlController, AutoCloseable{
    @Autowired
    DayOverviewController dayOverviewController;
    
    private static final double Y_TOP_OFFSET = 25.0;
    private static final double Y_BASE_OFFSET = 10.0;
    private static final double X_AXIS_VALUE = 59.00002539157867;

    private final Timeline timeline;
    @FXML private Group indicator;

    public CurrentTimeIndicatorController() {
        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(5), (ActionEvent t) -> {
                    refreshTimeIndicator();
                })
        );
        
    }
    
    @Override
    public void initialize() {
        setΤimeIndicatorBoundListener();
        setTimeIndicatorEventHandler();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private TableView<DayOverviewDto> appointmentTable(){
        return dayOverviewController.getAppointmentTable();
    }
    
    private void refreshTimeIndicator(){
        LocalTime time;
        for (var item : appointmentTable().getItems()){
            time = item.getTimeLabel().getTime();
            if (isCurrentTime(time)) {
                Bounds bounds = dayOverviewController.getBoundsHashMap().get(time);
                if (bounds != null) indicator.relocate(X_AXIS_VALUE, bounds.getCenterY() + Y_TOP_OFFSET);
            }
        }
    }
    
    private boolean isCurrentTime(LocalTime time) {
        LocalTime currentTime =  LocalTime.of(8, 30);//LocalTime.now();
        if (currentTime.getHour() == time.getHour()){
            if (currentTime.getMinute() >= time.getMinute()) return true;
        }
        return false;
    }

    private void setΤimeIndicatorBoundListener() {
        indicator.boundsInParentProperty().addListener((ov, t, newBounds) -> {
            if ((newBounds.getMinY() < appointmentTable().getLayoutY() + Y_TOP_OFFSET) || (newBounds.getMaxY() > appointmentTable().getLayoutY() + appointmentTable().getPrefHeight() - Y_BASE_OFFSET)) {
                indicator.setVisible(false);
            } else {
                indicator.setVisible(true);
            }
        });
    }

    private void setTimeIndicatorEventHandler(){
        indicator.setOnMouseClicked((t) -> {
//            t.copyFor(t, appointmentTable, t.getEventType());
//            timeIndicator.fireEvent(t);
        });
    }
    
    @Override // check if time line implements autoClosable
    public void close() throws Exception {
        timeline.stop();
    }

}
