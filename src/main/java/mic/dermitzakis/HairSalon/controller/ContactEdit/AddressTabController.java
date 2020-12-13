/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller.ContactEdit;

import java.util.function.Predicate;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.Data;
import mic.dermitzakis.HairSalon.dto.AddressDto;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Controller
@Data
public class AddressTabController implements FxmlController {

    private final ApplicationContext springContext;
    private FadeTransition fadeOutMessage;
    private AddressDto addressDtoBuffer;
    private AddressDto editableAddress;
    private static final int MESSAGE_DURATION = 6000;;

    @FXML
    private VBox addressTab;
    /* Table */
    @FXML
    private TableView<AddressDto> addressTable;
    @FXML
    private TableColumn<AddressDto, String> street_Column;
    @FXML
    private TableColumn<AddressDto, String> number_Column;
    @FXML
    private TableColumn<AddressDto, String> postCode_Column;
    @FXML
    private TableColumn<AddressDto, String> region_Column;

    /* Text Fields */
    @FXML
    private TextField street_TextField;
    @FXML
    private TextField number_TextField;
    @FXML
    private TextField postCode_TextField;
    @FXML
    private TextField region_TextField;

    /* Buttons */
    @FXML
    Button create_Button;
    @FXML
    Button delete_Button;

    public AddressTabController(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public void initialize() {
        initTable();
        assignTableColumns();
        setEventHandler();
        setSampleData();
    }

    private void initTable() {
        addressTable.setEditable(true);
        addressTable.setPlaceholder(new Label("Κενός Πίνακας"));
    }

    protected void assignTableColumns() {
        street_Column.setCellValueFactory(new PropertyValueFactory<>("street"));
        street_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        number_Column.setCellValueFactory(new PropertyValueFactory<>("number"));
        number_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        postCode_Column.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        postCode_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        region_Column.setCellValueFactory(new PropertyValueFactory<>("region"));
        region_Column.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    protected void setEventHandler() {
        setCellEventHandler();
        setButtonEventHandler();
    }

    private void setCellEventHandler() {
        street_Column.setOnEditCommit((CellEditEvent<AddressDto, String> event) -> {
            AddressDto address = event.getRowValue();
            address.setStreet(event.getNewValue());
        });
        number_Column.setOnEditCommit((CellEditEvent<AddressDto, String> event) -> {
            AddressDto address = event.getRowValue();
            address.setNumber(event.getNewValue());
        });
        postCode_Column.setOnEditCommit((CellEditEvent<AddressDto, String> event) -> {
            AddressDto address = event.getRowValue();
            address.setPostCode(event.getNewValue());
        });
        region_Column.setOnEditCommit((CellEditEvent<AddressDto, String> event) -> {
            AddressDto address = event.getRowValue();
            address.setRegion(event.getNewValue());
        });
    }

    private void setButtonEventHandler() {
        createButtonEventHandler();
        deleteButtonEventHandler();
    }

    private void createButtonEventHandler() {
        create_Button.setOnAction((event) -> {
            AddressDto address = newAddress(street_TextField.getText().strip(), number_TextField.getText().strip(), postCode_TextField.getText().strip(), region_TextField.getText().strip());
            if (! isAddressEmpty(address)) {
                addressTable.getItems().add(address);
                clearFields();
            }
            street_TextField.requestFocus();
        });
    }

    private void deleteButtonEventHandler() {
        delete_Button.setOnAction((event) -> {
            if (isItemSelected()) {
                int rowIndex = getSelectedIndex();
                if (isRowSelected(rowIndex)) {
                    removeRow(rowIndex);
                }
                addressTable.getSelectionModel().clearSelection();
                showMessage();
            }
        });
    }

    private boolean isItemSelected() {
        return getSelectedIndex() != -1;
    }

    private int getSelectedIndex() {
        return addressTable.getSelectionModel().getSelectedIndex();
    }

    private FadeTransition showMessage() {
        EditContactController editContactController = springContext.getBean(EditContactController.class);
        Node node = editContactController.getMessageContainer();
        editContactController.getMessageLabel().setText("Μία Διεύθυνση διαγράφηκε");
        editContactController.getInvertHyperlink().setText("Αναίρεση");
        editContactController.getInvertHyperlink().setOnAction(this::hyperlinkActionHandler);
        setMessageVisible(true);
        fadeOutMessage = new FadeTransition(Duration.millis(MESSAGE_DURATION), node);
        fadeOutMessage.setOnFinished((t) -> node.setOpacity(0));
        fadeOutMessage.setFromValue(1);
        fadeOutMessage.setToValue(0);
        fadeOutMessage.play();
        return fadeOutMessage;
    }

    private void hyperlinkActionHandler(ActionEvent event){
        invertDelete();
    }
    
    public void invertDelete() {
        fadeOutMessage.jumpTo(Duration.millis(MESSAGE_DURATION));
        setMessageVisible(false);
        if (isAddressUnique()) {
            addressTable.getItems().add(addressDtoBuffer);
        }
    }

    private boolean isRowSelected(int rowIndex) {
        return rowIndex >= 0;
    }

    private void removeRow(int rowIndex) {
        addressDtoBuffer = addressTable.getItems().get(rowIndex);
        addressTable.getItems().remove(rowIndex);
    }

    private void setSampleData() {
        addressTable.getItems().add(newAddress("Μελετίου Μεταξάκη", "25", "73100", "Νέα Χώρα - ΧΑΝΙΑ"));
    }

    private AddressDto newAddress(String street, String number, String postCode, String region) {
        AddressDto address = springContext.getBean(AddressDto.class);
        address.setStreet(street);
        address.setNumber(number);
        address.setPostCode(postCode);
        address.setRegion(region);

        return address;
    }

    private void clearFields() {
        street_TextField.clear();
        number_TextField.clear();
        postCode_TextField.clear();
        region_TextField.clear();
    }

    private boolean isAddressUnique() {
        return !addressTable.getItems().stream().anyMatch(addressEqualsBuffer());
    }

    private Predicate<? super AddressDto> addressEqualsBuffer() {
        Predicate<? super AddressDto> predicate = (AddressDto t) -> t.equals(addressDtoBuffer);
        return predicate;
    }

    private void setMessageVisible(boolean visible) {
        EditContactController editContactController = springContext.getBean(EditContactController.class);
        editContactController.getMessageContainer().setVisible(visible);
    }

    private boolean isAddressEmpty(AddressDto address) {
        return address.getStreet().isEmpty() 
                && address.getNumber().isEmpty()
                && address.getPostCode().isEmpty()
                && address.getRegion().isEmpty();
    }

}
