/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import mic.dermitzakis.HairSalon.dto.EmailDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import mic.dermitzakis.HairSalon.dto.PhoneDto;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
public class EmailTabController implements FxmlController{
    private final ObservableList<String> typeData = 
            FXCollections.observableArrayList("Σπίτι","Εργασία","Γραφείο","Προσωπικό");
    
    @FXML private VBox emailTab;
    
    /* Table */
    @FXML private TableView<EmailDto> emailTable;
    @FXML private TableColumn<EmailDto, String> email_Column;
    @FXML private TableColumn<EmailDto, String> type_Column;

    /* Fields */
    @FXML private TextField email_TextField;
    @FXML private ChoiceBox type_ChoiceBox;
    
    /* Buttons */
    @FXML Button create_Button;
    @FXML Button delete_Button;

    @Override
    public void initialize() {
        initTable();
        initChoiceBox();
        assignTableColumns();
        setEventHandler();
        setSampleData();
    }
    
    private void initTable(){
        emailTable.setEditable(true);
        emailTable.setPlaceholder(new Label("Κενός Πίνακας"));
    }
    
    protected void assignTableColumns() {
        email_Column.setCellValueFactory(new PropertyValueFactory<>("number"));
        email_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        type_Column.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        type_Column.setCellFactory(ChoiceBoxTableCell.forTableColumn(typeData));
    }
   
    protected void setEventHandler(){
        email_Column.setOnEditCommit((TableColumn.CellEditEvent<EmailDto, String> event) -> {
            EmailDto email = event.getRowValue();
            email.setEmail(event.getNewValue());
        });
        type_Column.setOnEditCommit((TableColumn.CellEditEvent<EmailDto, String> event) -> {
            EmailDto email = event.getRowValue();
            email.setType(event.getNewValue());
        });
    }
    
    private void setSampleData(){
//        ObservableList<AddressDto> data = FXCollections.observableArrayList(
//                newAddress("Μελετίου Μεταξάκη","25","73100","Νέα Χώρα - ΧΑΝΙΑ")
//        );
//        
//        addressTable.getItems().add(newAddress("Μελετίου Μεταξάκη","25","73100","Νέα Χώρα - ΧΑΝΙΑ"));
    }
    
//    private AddressDto newAddress(String street, String number, String postCode, String region){
//        AddressDto address = springContext.getBean(AddressDto.class);
//        address.setStreet(street);
//        address.setNumber(number);
//        address.setPostCode(postCode);
//        address.setRegion(region);
//        
//        return address;
//    }
//
    private void initChoiceBox() {
        type_ChoiceBox.getItems().addAll(typeData);
    }

    public void clearFields() {
    }
    
    
}
