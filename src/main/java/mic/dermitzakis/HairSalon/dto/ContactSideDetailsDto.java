/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.scene.image.Image;
import lombok.Data;
import mic.dermitzakis.HairSalon.model.Gender;
import mic.dermitzakis.HairSalon.model.Note;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
//@Scope("prototype")
public class ContactSideDetailsDto {
    private long id;
    
    private Gender gender;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
    private String phone;
    private Note notes;
    private Image picture;
    
    public String getFullName(){
        return String.format("%s %s", firstName, lastName);
    }
}
