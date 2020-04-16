/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mic.dermitzakis.HairSalon.view.FxmlController;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mic.dermitzakis.HairSalon.dto.ContactViewDetailsDto;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.repository.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
public class ContactOverviewController implements FxmlController, EventHandler<MouseEvent> {

    private final ApplicationContext springContext;
    private final DataLoader dataLoaderService;

    @FXML
    public TableView<ContactViewDetailsDto> contactTable;
    @FXML
    private TableColumn<ContactViewDetailsDto, Long> id_Column;
    @FXML
    private TableColumn<ContactViewDetailsDto, String> firstName_Column;
    @FXML
    private TableColumn<ContactViewDetailsDto, String> lastName_Column;
    @FXML
    private TableColumn<ContactViewDetailsDto, String> phone_Column;
    @FXML
    private TableColumn<ContactViewDetailsDto, String> notes_Column;

    @FXML
    private Text name_txt;
    @FXML
    private Text id_txt;
    @FXML
    private StackPane picture_area;
    @FXML
    private Text gender_txt;
    @FXML
    private Text dob_txt;
    @FXML
    private Text date_created_txt;
    @FXML
    private Text last_modified_txt;

    @Autowired
    public ContactOverviewController(ApplicationContext springContext) {
        this.springContext = springContext;
        this.dataLoaderService = springContext.getBean(DataLoader.class);
    }

    /**
     * Initializes the controller class.
     */
    @Override // FxmlController
    public void initialize() { 
        initializeTableColumns();
        loadContacts();
        setEventHandler();
        setDetailsArea(0);
    }

    private void initializeTableColumns() {
        id_Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName_Column.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName_Column.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phone_Column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        notes_Column.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    @Override  // Called to display contact details
    public void handle(MouseEvent event) {
        if (event.getSource() == contactTable) {
            setDetailsArea(this.getSelectedItem().getId());
        }
    }

    public void setDetailsArea(long id) { // todo -> load picture
        if (!contactTable.getItems().isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            if (id == 0) {
                contactTable.getSelectionModel().select((int) id);
            }
            ContactViewDetailsDto details = this.getSelectedItem();
//                    ContactList.getTestData().get(id);
            name_txt.setText(details.getFullName());
            id_txt.setText(String.valueOf(details.getId()));
//            gender_txt.setText(details.getGender().toString());
//            dob_txt.setText(dtf.format(LocalDate.now()));//contactDto.getDob()
//            date_created_txt.setText(dtf.format(LocalDateTime.now()));//contactDto.getDateCreated()
//            last_modified_txt.setText(dtf.format(LocalDateTime.now()));//contactDto.getLastModified())
        }
    }

    public ContactViewDetailsDto getSelectedItem() {
        return contactTable.getSelectionModel().getSelectedItem();
    }

    public String getSelectedItemFullName() {
        return getSelectedItem().getFirstName() + " " + getSelectedItem().getLastName();
    }

    private ObservableList<ContactViewDetailsDto> getContactTable(List<Contact> contacts) {
        ContactOverviewManager contactTableManager = springContext.getBean(ContactOverviewManager.class);
        return contactTableManager.getContactDtoList(contacts);
    }

    private void loadContacts() {
        contactTable.setItems(getContactTable(dataLoaderService.getContacts().orElse(new ArrayList<>())));// get - forget
    }

    private void setEventHandler() {
        contactTable.setOnMouseClicked(this);
    }

}
