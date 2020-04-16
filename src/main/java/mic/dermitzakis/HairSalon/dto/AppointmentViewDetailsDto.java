/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import javafx.scene.image.Image;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
@Scope("prototype")
public class AppointmentViewDetailsDto {
    private String appCreator;
    private String appDateCreated;
    private String name;
    private String id;
    private Image picture;
    private String gender;
    private String dob;
    private String dateCreated;
    private String lastModified;
    
    public void init(){
        appCreator = "";
        appDateCreated = "";
        name = "Ονοματεπώνυμο";
        id = "0";
        gender = "";
        picture = new Image("file:"+getClass().getResource("/images/default-unspecified.jpg").getPath());
        dob = "";
        dateCreated  = "";
        lastModified = "";
    }

}

