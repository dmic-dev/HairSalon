/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import java.util.UUID;
import lombok.Data;
import mic.dermitzakis.HairSalon.custom.ContactTableLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Data
@Component
@Scope("prototype")
public class ContactOverviewDto {
    private final ApplicationContext springContext;
    private long contactId;
    
    private ContactTableLabel id;
    private ContactTableLabel firstName;
    private ContactTableLabel lastName;
    private ContactTableLabel phone;
    private ContactTableLabel notes;
    
    @Autowired
    public ContactOverviewDto(ApplicationContext springContext){
        this.springContext = springContext;
        initializeFields();
    }
    
    private void initializeFields(){
        id        = springContext.getBean(ContactTableLabel.class);
        firstName = springContext.getBean(ContactTableLabel.class);
        lastName  = springContext.getBean(ContactTableLabel.class);
        phone     = springContext.getBean(ContactTableLabel.class);
        notes     = springContext.getBean(ContactTableLabel.class);
    }
    
    public String getFullName(){
        return String.format("%s %s", firstName.getText(), lastName.getText());
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.contactId ^ (this.contactId >>> 32));
        return hash;
    }
}
