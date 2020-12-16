/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.custom.ContactTableLabel;
import mic.dermitzakis.HairSalon.dto.ContactOverviewDto;
import mic.dermitzakis.HairSalon.dto.RowDetailsDto;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.services.ContactService;
import mic.dermitzakis.HairSalon.mapper.ContactTableDetailsMapper;
import mic.dermitzakis.HairSalon.event.RowChangedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Controller
public class ContactOverviewController extends AbstractTableViewController implements EventHandler<MouseEvent> {

    private final ApplicationContext springContext;
    private final ContactService contactService;
    private final EventBus eventBus;
    private final RowChangedEvent rowChangedEvent;
    private final RowDetailsDto rowDetailsDto;
    private final ContactTableIndex tableIndex;
    
    private UUID selectedItem;

    /* Controls */
    @FXML private TextField searchField;
    @FXML private Button clearButton;
    
    /* Table */
    @FXML private TableView<ContactOverviewDto> contactTable;
    @FXML private TableColumn<ContactOverviewDto, ContactTableLabel> id_Column;
    @FXML private TableColumn<ContactOverviewDto, ContactTableLabel> firstName_Column;
    @FXML private TableColumn<ContactOverviewDto, ContactTableLabel> lastName_Column;
    @FXML private TableColumn<ContactOverviewDto, ContactTableLabel> phone_Column;
    @FXML private TableColumn<ContactOverviewDto, ContactTableLabel> notes_Column;

    public ContactOverviewController(ApplicationContext springContext, ContactService contactService, EventBus eventBus, RowChangedEvent rowChangedEvent, RowDetailsDto rowDetailsDto, ContactTableIndex tableIndex) {
        this.springContext = springContext;
        this.contactService = contactService;
        this.eventBus = eventBus;
        this.rowChangedEvent = rowChangedEvent;
        this.rowDetailsDto = rowDetailsDto;
        this.tableIndex = tableIndex;
    }
    

    @PostConstruct
    private void init(){
        eventBus.register(this);
    }

    /**
     * Initializes the controller class.
     * @param event
     */
    
    @Subscribe
//    @EventListener
    public void rowChangesListener(RowChangedEvent event){
        RowDetailsDto data= event.getData();
        this.selectedItem = data.getSelectedItem();
    }
    
    @Override
    protected void setEventHandler() {
        contactTable.setOnMouseExited(this);
        contactTable.setOnMouseClicked(this);
        setTableSearchEventListener();
        setClearButtonHandler();
    }

    @Override
    protected void assignTableColumns() {
        id_Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName_Column.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName_Column.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phone_Column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        notes_Column.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getSource() == contactTable) {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                setTableIndex();
                rowDetailsDto.setSelectedItem(selectedItem);
                rowChangedEvent.setData(rowDetailsDto);
                eventBus.post(rowChangedEvent);
            } else if (event.getEventType() == MouseEvent.MOUSE_EXITED){
                rowDetailsDto.setFocusedItem(null);
                rowChangedEvent.setData(rowDetailsDto);
                eventBus.post(rowChangedEvent);
            }
        }
    }

    private void setTableIndex() {
        tableIndex.setValue(contactTable.getSelectionModel().getSelectedIndex());
    }

    /**
     * 
     * @param contacts
     * @return 
     * 
     * Maybe try ForkJoinPool along with parallel()
     */
    private ObservableList<ContactOverviewDto> getTableItems(List<Contact> contacts){
        final ContactTableDetailsMapper tableDetails  = springContext.getBean(ContactTableDetailsMapper.class);

        List<ContactOverviewDto> collection = contacts
                .stream()
                .parallel() // Maybe responsible for the multiple focused, selected rows
                .map(tableDetails::extract)
                .collect(toList());
        
        return FXCollections.observableArrayList(collection);
    }

    @Override
    public void insertDataIntoTable() {
        List<Contact> contacts = loadDataFromRepository();
        contactTable.setItems(getTableItems(contacts));
    }
    
    private List<Contact> loadDataFromRepository(){
        return contactService.findAll().orElseThrow();
    }
    
    @Override
    protected void restoreState() {
        selectCurrentTableItem();
        contactTable.scrollTo(tableIndex.getValue());
        ContactOverviewDto contactOverviewDto = contactTable.getItems().get(tableIndex.getValue());
        ContactTableLabel label = contactOverviewDto.getFirstName();

        postRowChangedEvent(label);
        postSideDetails(label);
    }

    private void postSideDetails(ContactTableLabel label) {
        label.postSideDetails();
    }

    private void postRowChangedEvent(ContactTableLabel label) {
        rowDetailsDto.setDetails(label.getRowId(), label.getRowId(), null);
        rowChangedEvent.setData(rowDetailsDto);
        eventBus.post(rowChangedEvent);
    }
    
    private void setTableSearchEventListener() {
        ObservableList<ContactOverviewDto> tableData = FXCollections.observableArrayList();
                tableData.addAll(contactTable.getItems());
        FilteredList<ContactOverviewDto> filteredData= new FilteredList<>(tableData, e -> true);
        searchField.setOnKeyReleased( e -> {
            searchField.textProperty().addListener((observbaleValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super ContactOverviewDto>) contactDto -> {
                    String lowerCasefilter = newValue.toLowerCase();
                    if(newValue.isEmpty()) {
                        return true;
                    } else if (String.valueOf(contactDto.getId().getContactId()).contains(newValue)) {
                        return true;
                    } else if (contactDto.getFirstName().getText().toLowerCase().startsWith(lowerCasefilter)) {
                        return true;
                    } else if (contactDto.getLastName().getText().toLowerCase().startsWith(lowerCasefilter)) {
                        return true;
                    } else if (contactDto.getPhone().getText().toLowerCase().contains(lowerCasefilter)) {
                        return true;
                    } else if (contactDto.getNotes().getText().toLowerCase().contains(lowerCasefilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ContactOverviewDto> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(contactTable.comparatorProperty());
            contactTable.setItems(sortedData);
        });
    }

    private void selectCurrentTableItem() {
        contactTable.getSelectionModel().select(tableIndex.getValue());
    }

    private void setClearButtonHandler() {
        clearButton.setOnAction((t) -> searchField.clear());
    }
    
}
