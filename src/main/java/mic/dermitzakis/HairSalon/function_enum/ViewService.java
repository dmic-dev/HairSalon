/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.function_enum;

import org.springframework.stereotype.Service;

@Service
public class ViewService{
//    @Autowired
//    ViewData viewData;
    
    public ViewData mainView(){
        return viewData("title", "/fxml/Main.fxml");
    }

    public ViewData getView(TestFxmlView view) {
        return view.algorithm.apply(this);
    }
    
    public ViewData viewData(String title, String fileURL){
        ViewData viewData = new ViewData();
        viewData.setTitle(title);
        viewData.setFileURL(fileURL);
        return viewData;
    }
    
}
