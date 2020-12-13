/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.dto;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mderm
 */
public class NewClass {
    private SimpleStringProperty street;
    private SimpleStringProperty number;
    private SimpleStringProperty postCode;
    private SimpleStringProperty region;

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street = new SimpleStringProperty(street);
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String number) {
        this.number = new SimpleStringProperty(number);
    }

    public String getPostCode() {
        return postCode.get();
    }

    public void setPostCode(String postCode) {
        this.postCode = new SimpleStringProperty(postCode);
    }

    public String getRegion() {
        return region.get();
    }

    public void setRegion(String region) {
        this.region = new SimpleStringProperty(region);
    }
    
}
