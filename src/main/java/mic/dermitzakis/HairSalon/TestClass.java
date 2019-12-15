/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import mic.dermitzakis.HairSalon.services.EntityDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.services.EntityDataService;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.EntityType;
import mic.dermitzakis.HairSalon.repository.ContactRepository;
import mic.dermitzakis.HairSalon.services.DataLoaderService;


/**
 *
 * @author mderm
 */
@Component
public class TestClass {
//    
//    private final ApplicationContext springContext;
//    private final EntityDataService entityDataService;
//    
//    private static final Logger LOG = LoggerFactory.getLogger(TestClass.class);
//    
//    @Autowired
//    public TestClass(ApplicationContext springContext) {
//        this.springContext = springContext;
//        entityDataService = springContext.getBean(EntityDataService.class);
//    }
    
    
    public void show() throws ParseException, UnsupportedEncodingException{
//        EntityDataService entityDataService = springContext.getBean(EntityDataService.class);
//        entityDataService.showContactList();
//        List<Contact> contacts = (List<Contact>)
//                entityDataService.read(EntityType.CONTACT).stream()
//                .collect(toList());
//        Map<String, List<Contact>> byName = contacts.stream()
//                .collect(groupingBy(Contact::getFirstName));
//        Set<String> keySet = contacts.stream()
//                .map(Contact::getFirstName)
//                .collect(toSet());
//        for (String key : keySet){
//            List<Contact> contactsList = byName.get(key);
//            contactsList.forEach(System.out::println);
//        }
//        
//        byte ptext[] = "Michael Dermitzakis Owes 30".getBytes(StandardCharsets.UTF_8);
//        String inputString = new String(ptext, StandardCharsets.UTF_8);
//        System.out.println(inputString);
//        Scanner sc = new Scanner(inputString);
//        List<String> stringList = new LinkedList<>();
//        while (sc.hasNext()) stringList.add(sc.next());
//        String s = stringList.stream().collect(joining(", ","(",")"));
//        System.out.println("s: "+s);
    }

//    private void calculateStartingDate(){
//        var dataLoaderService =springContext.getBean(DataLoaderService.class);
//        dataLoaderService.calculateWeekStart();
//    }
    
}
