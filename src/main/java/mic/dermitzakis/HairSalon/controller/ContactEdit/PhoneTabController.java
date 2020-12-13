/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import mic.dermitzakis.HairSalon.dto.PhoneDto;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
public class PhoneTabController implements FxmlController{
    private final ObservableList<String> typeData = 
            FXCollections.observableArrayList("Κινητό","Σταθερό","Σπίτι","Εργασία","Γραφείο","Εξοχικό");
    
    @FXML private SplitPane phoneTab;
    
    /* Table */
    @FXML private TableView<PhoneDto> phonesTable;
    @FXML private TableColumn<PhoneDto, String> number_Column;
    @FXML private TableColumn<PhoneDto, String> type_Column;

    /* Fields */
    @FXML private TextField number_TextField;
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
        phonesTable.setEditable(true);
        phonesTable.setPlaceholder(new Label("Κενός Πίνακας"));
    }
    
    protected void assignTableColumns() {
        number_Column.setCellValueFactory(new PropertyValueFactory<>("number"));
        number_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        type_Column.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        type_Column.setCellFactory(ChoiceBoxTableCell.forTableColumn(typeData));
    }
   
    protected void setEventHandler(){
        number_Column.setOnEditCommit((TableColumn.CellEditEvent<PhoneDto, String> event) -> {
            PhoneDto phone = event.getRowValue();
            phone.setNumber(event.getNewValue());
        });
        type_Column.setOnEditCommit((TableColumn.CellEditEvent<PhoneDto, String> event) -> {
            PhoneDto phone = event.getRowValue();
            phone.setType(event.getNewValue());
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
