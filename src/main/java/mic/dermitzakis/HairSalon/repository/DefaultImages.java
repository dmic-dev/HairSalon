/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import javafx.scene.image.Image;
import mic.dermitzakis.HairSalon.model.Contact.Gender;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */

@Component
public class DefaultImages {
    private final Image male;
    private final Image female;
    private final Image unspecified;
  
    public DefaultImages() {
        this.male = new Image("file:"+getClass().getResource("/images/default-male.jpg").getPath());
        this.female = new Image("file:"+getClass().getResource("/images/default-female.jpg").getPath());
        this.unspecified = new Image("file:"+getClass().getResource("/images/default-unspecified.jpg").getPath());
    }
    
    public Image getImage(Gender gender){
        switch (gender) {
            case MALE: return  this.male;
            case FEMALE: return this.female;
            case UNSPECIFIED: return this.unspecified;
            default: return this.unspecified;
        }
    }
}
