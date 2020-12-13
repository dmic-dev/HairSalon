/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.custom;

import javafx.geometry.Insets;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author mderm
 */
//@Data
//@Component
//@Scope("prototype")
public final class ContactTableLabel extends AbstractLabel {
    @Getter @Setter
    private long contactId = 0;
 
    public ContactTableLabel() {
        super();
        setLabelProperties();
    }
    
    
    public final void setLabelProperties() {
        prefWidthProperty().bind(widthProperty().add(800.0));
        setPadding(new Insets(4,0,4,3));
    }
    
    @Override
    public boolean equals(Object obj){
//        if (this == obj) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.contactId ^ (this.contactId >>> 32));
        return hash;
    }
    
}


//        label.setStyle("-fx-text-fill: red; -fx-background-color: lightblue; -fx-border-color: blue; -fx-border-radius: 7;");
