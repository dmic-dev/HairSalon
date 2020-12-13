/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import mic.dermitzakis.HairSalon.model.Note;
import mic.dermitzakis.HairSalon.services.NotesService;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * FXML Controller class
 *
 * @author mderm
 */
@Controller
public class ContactDataController implements FxmlController {
    private final ApplicationContext springContext;
    private final NotesService notesService;

    @FXML private PhoneTabController phoneTabController;
    @FXML private AddressTabController addressTabController;
    @FXML private EmailTabController emailTabController;
    
    @FXML private VBox contactData;
    @FXML private DatePicker birthday_picker;
    @FXML private TextArea notes_txtArea;

    public ContactDataController(ApplicationContext springContext, NotesService notesService) {
        this.springContext = springContext;
        this.notesService = notesService;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        // TODO
    }    
    
/**
 * check this method for return type.
 * is it Optional of Note or is it Note
 * 
 * @return 
 */
    public Note persistNotes() {
        Note notes = springContext.getBean(Note.class);
        if (notes_txtArea.getText() != null) {
            notes.setText(notes_txtArea.getText());
            Optional<Note> savedNotes = notesService.save(notes);
            return savedNotes.orElseThrow();
        }
        return null;
    }

}
