/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import mic.dermitzakis.HairSalon.function_enum.TestFxmlView;
import org.springframework.stereotype.Component;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.repository.H2DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


/**
 *
 * @author mderm
 */
@Component
public class TestClass {
    
    private final ApplicationContext springContext;
    private final H2DataService entityDataService;
    
    private static final Logger LOG = LoggerFactory.getLogger(TestClass.class);
    
    @Autowired
    public TestClass(ApplicationContext springContext) {
        this.springContext = springContext;
        entityDataService = springContext.getBean(H2DataService.class);
    }
    
    
    public void show() /*throws ParseException, UnsupportedEncodingException*/{
//        entityDataService.showContactList();
//        entityDataService.read(EntityType.CONTACT).stream()
//        .collect(toList())
//        .forEach(System.out::println);

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
        
//        System.out.println("\n\n\n");
//        Search search = springContext.getBean(Search.class);
//        List<Contact> contacts = search.getContacts("M");
//        contacts.forEach(System.out::println);
//        System.out.println("\n\n\n");

//          Regular Expression
//        String str = "-123.45";
//        String numberRegEx = "-?\\d+(\\.\\d+)?";
//        ;
//        boolean b = str.matches(numberRegEx);
//        System.out.println(b);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d-M-yyyy");
        System.out.println("Date : " + LocalDate.parse("20-12-2017", dtf));

        Type CompositeType = new TypeToken<ArrayList<Contact>>(){}.getType();
//        String response = json.toJson(CompositeType);
//        List<Contact> contacts = json.fromJson(response, CompositeType.class);

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        LOG.info("Number of System proccesor cores : " + numberOfCores);
        
    }

//    private void calculateStartingDate(){
//        var dataLoaderService =springContext.getBean(DataLoader.class);
//        dataLoaderService.calculateWeekStart();
//    }
    
}
