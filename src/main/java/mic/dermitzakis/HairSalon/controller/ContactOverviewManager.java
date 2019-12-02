/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mic.dermitzakis.HairSalon.dto.ContactDtoManager;
import mic.dermitzakis.HairSalon.dto.ContactDto;
import mic.dermitzakis.HairSalon.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@Component
public class ContactOverviewManager {
    
    private final ApplicationContext springContext;

    @Autowired
    public ContactOverviewManager(ApplicationContext springContext) {
        this.springContext = springContext;
    }
    
    public ObservableList<ContactDto> getContactDtoList(List<Contact> contacts){
        ContactDtoManager contactDtoManager = springContext.getBean(ContactDtoManager.class);
        ObservableList<ContactDto> observableDtoList = FXCollections.observableArrayList();
        List<ContactDto> contactDtoList = contacts.stream()
                .map(contactDtoManager::getContactDto)
                .collect(toList());
        
        observableDtoList.addAll(contactDtoList);
        
        return observableDtoList;
    }
}
