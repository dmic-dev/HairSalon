/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.various;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class TimeIndicator extends Group implements Initializable{
    private final Circle circle;
    private final Line line;

    public TimeIndicator() {
        circle = new Circle();
        line = new Line();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCircle();
        initLine();
        this.getChildren().addAll(circle, line);
    }

    private void initLine() {
        int y = 50;
//        line.setLayoutX(26);
//        line.setLayoutY(y);
        line.setStartX(50);
        line.setStartY(y);
        line.setEndX(100);
        line.setEndY(y+1);
        line.setFill(Paint.valueOf("red"));
    }

    private void initCircle() {
        circle.setRadius(3.5);
        circle.setLayoutX(26);
        circle.setLayoutY(26);
        circle.setFill(Paint.valueOf("red"));
    }
    
    
}