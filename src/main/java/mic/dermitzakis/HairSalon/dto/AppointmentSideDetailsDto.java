/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import javafx.scene.image.Image;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
public class AppointmentSideDetailsDto {
    
    private String appCreator;
    private String appDateCreated;
    private String name;
    private String id;
    private String gender;
    private String dob;
    private String dateCreated;
    private String lastModified;
    private Image  picture;
    
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
    
//    public void setPicture(Contact contact){
//        if (contact.getPicture() != null) {
//            Image image = Picture.toImage(contact.getPicture().getByteArray());
//            this.picture = image;// next move // 
//        } else 
//            switch (contact.getGender()) {
//                case MALE: {this.picture = defaultImages.getImage(Contact.Gender.MALE); break;}
//                case FEMALE: {this.picture = defaultImages.getImage(Contact.Gender.FEMALE); break;}
//                case UNSPECIFIED: {this.picture = defaultImages.getImage(Contact.Gender.UNSPECIFIED); break;}
//                default: this.picture = defaultImages.getImage(Contact.Gender.UNSPECIFIED);
//            }
//    }
//
}

