/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javax.annotation.PostConstruct;
import mic.dermitzakis.HairSalon.dto.AppointmentSideDetailsDto;
import mic.dermitzakis.HairSalon.event.AppointmentSideDetailsEvent;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class DaySideDetailsController implements FxmlController{
    private final EventBus eventBus;
 
    @FXML private SplitPane daySideDetails;
    
/*  Side Details */
    @FXML private Text name_txt;
    @FXML private Text id_txt;
    @FXML private StackPane picture_area;
    @FXML private Text gender_txt;
    @FXML private Text dob_txt;
    @FXML private Text date_created_txt;
    @FXML private Text last_modified_txt;
    @FXML private Text appointment_date_created_txt;
    @FXML private Text emp_name;

    public DaySideDetailsController(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    
    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

//   e @Subscribe
    @EventListener
    public void appointmentDetailsListener(AppointmentSideDetailsEvent event){
        AppointmentSideDetailsDto data = event.getData();
        name_txt.setText(data.getName());
        id_txt.setText(data.getId());
        picture_area.getChildren().add(newImageView(data.getPicture()));
        gender_txt.setText(data.getGender());
        dob_txt.setText(data.getDob());
        date_created_txt.setText(data.getDateCreated());
        last_modified_txt.setText(data.getLastModified());
        appointment_date_created_txt.setText(data.getAppDateCreated());
        emp_name.setText(data.getAppCreator());
    }
    
    @Override
    public void initialize() {
    }

    private ImageView newImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        return imageView;
    }

}
