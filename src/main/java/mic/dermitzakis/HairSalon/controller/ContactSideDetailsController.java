/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javax.annotation.PostConstruct;
import mic.dermitzakis.HairSalon.dto.ContactSideDetailsDto;
import mic.dermitzakis.HairSalon.event.ContactSideDetailsEvent;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
public class ContactSideDetailsController implements FxmlController{
    private final EventBus eventBus;
    /* SideDetails */
    @FXML private Text name_txt;
    @FXML private Text id_txt;
    @FXML private StackPane picture_area;
    @FXML private Text gender_txt;
    @FXML private Text dob_txt;
    @FXML private Text date_created_txt;
    @FXML private Text last_modified_txt;

    public ContactSideDetailsController(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    
    @PostConstruct
    private void init(){
        eventBus.register(this);
    }
    
    @Override
    public void initialize() {
    }

    @Subscribe
    public void contactDetailsListener(ContactSideDetailsEvent event){
        ContactSideDetailsDto data = event.getData();
        name_txt.setText(data.getFullName());/*contactDetailsDto.getName()*/
        id_txt.setText(String.valueOf(data.getId()));/*contactDetailsDto.getId()*/
        picture_area.getChildren().add(newImageView(data.getPicture()));
        gender_txt.setText(data.getGender().toString());
        dob_txt.setText(data.getDob().format(DateTimeFormatter.ISO_DATE));
        date_created_txt.setText(data.getDateCreated().format(DateTimeFormatter.ISO_DATE));
        last_modified_txt.setText(data.getLastModified().format(DateTimeFormatter.ISO_DATE));
        // details.getPhone
        // details.getNotes
    }
    
    private ImageView newImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        return imageView;
    }

}
