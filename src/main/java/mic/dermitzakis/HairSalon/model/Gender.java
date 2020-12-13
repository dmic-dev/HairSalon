/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import javafx.scene.image.Image;

/**
 *
 * @author mderm
 */
public enum Gender {

    MALE("Άνδρας") {
        @Override
        public Image getImage() {
            return new Image("file:"+getClass().getResource("/images/default-male.jpg").getPath());
        }
    },
    FEMALE("Γυναίκα") {
        @Override
        public Image getImage() {
            return new Image("file:"+getClass().getResource("/images/default-female.jpg").getPath());
        }
    },
    UNSPECIFIED("Αδιευκρίνηστο") {
        @Override
        public Image getImage() {
            return new Image("file:"+getClass().getResource("/images/default-unspecified.jpg").getPath());
        }
    };

    String gender;

    private Gender(String gender){
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }

    public abstract Image getImage();
    
}
