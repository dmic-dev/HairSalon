/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.function_enum;

import java.util.function.Function;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
//@Component
public enum TestFxmlView {
    MAIN(ViewService::mainView),
    APPOINTMENT_VEW(ViewService::mainView),
    CONCTACT_VIEW(ViewService::mainView);
    
    /**
     * BiFunction<ClassName, όρισμα, return_type> 
     * className είναι το ViewService, 
     * όρισμα είναι το object που παίρνει η method mainView,
     * και return_type είναι το object που επιστρέφει η mainView method
     * 
     */
    public Function<ViewService, ViewData> algorithm;
//    private final Function <ViewData, String> title = (t) -> t.getTitle(); //To change body of generated lambdas, choose Tools | Templates.
//    private final Function <ViewData, String> fileURL = (t) -> t.getFileURL(); //To change body of generated lambdas, choose Tools | Templates.

    private TestFxmlView(Function<ViewService, ViewData> algorithm) {
        this.algorithm = algorithm;
    }
    
//    public Function<ViewService, String> getTitle(){
//        return algorithm.andThen(title);
//    }
    
}
