/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.various.TimeInputHandler;
import mic.dermitzakis.HairSalon.various.DateInputHandler;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mic.dermitzakis.HairSalon.controller.ContactEdit.MainContentController;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.PENDING;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.Note;
import mic.dermitzakis.HairSalon.services.AppointmentService;
import mic.dermitzakis.HairSalon.services.ContactService;
import mic.dermitzakis.HairSalon.services.NotesService;
import mic.dermitzakis.HairSalon.view.FxmlController;
import mic.dermitzakis.HairSalon.view.FxmlView;
import mic.dermitzakis.HairSalon.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mderm
 */
@Data
@EqualsAndHashCode
@Controller
public class EditAppointmentController implements FxmlController {
    private final ApplicationContext springContext;
    private final StageManager stageManager;
    private final DayOverviewController dayOverviewController;
    private final DayOverviewControlsController dayOverviewControlsController;
    private final MainContentController mainAnchorController;
    private final ContactService contactService;
    private final AppointmentService appointmentService;
    private final NotesService notesService;

    @FXML private TextField searchField;
    @FXML private ListView listView;
    @FXML private DatePicker datePicker;
    @FXML private Spinner hourSpinner;
    @FXML private Spinner minuteSpinner;
    @FXML private TextArea notesTextArea;
    @FXML private Button createButton;
    @FXML private Button cancelButton;
    @FXML private Label messageLabel;

    @Autowired
    @Lazy
    public EditAppointmentController(ApplicationContext springContext, StageManager stageManager, DayOverviewController dayOverviewController, DayOverviewControlsController dayOverviewControlsController, MainContentController mainAnchorController, ContactService contactService, AppointmentService appointmentService, NotesService notesService) {
        this.springContext = springContext;
        this.stageManager = stageManager;
        this.dayOverviewController = dayOverviewController;
        this.dayOverviewControlsController = dayOverviewControlsController;
        this.mainAnchorController = mainAnchorController;
        this.contactService = contactService;
        this.appointmentService = appointmentService;
        this.notesService = notesService;
    }


    @Override
    public void initialize() {
        initListView();
        initDatePicker();
        initTimeSpinners();
        initNotesTextArea();
    }

    private void initListView() {
        listView.setPlaceholder(new Label("Κενή Λίστα"));
        listView.setItems(getListItems());
    }

    private ObservableList<Contact> getListItems() {
        return FXCollections.observableArrayList(getContactList());
    }

    private List<Contact> getContactList() {
        return contactService.findAll().orElse(new ArrayList<>());
    }

    private void initDatePicker() {
        setDatePickerValue();
        setDatePickerConverter();
    }

    private void setDatePickerValue() {
        LocalDate value = LocalDate.now();
        if (dayOverviewControlsController.getDatePicker() != null) {
            value = dayOverviewControlsController.getDatePicker().getValue();
        }
        datePicker.setValue(value);
    }

    private void setDatePickerConverter() {
        StringConverter<LocalDate> stringConverter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy");
                return date.format(dtf);
            }

            @Override
            // Should try other formats (maybe with try-catch)
            public LocalDate fromString(String dateString) {
                return DateInputHandler.getDate(dateString).orElse(LocalDate.now());
            }
        };

        datePicker.setConverter(stringConverter);
    }

    private void initTimeSpinners() {
        String hourString = "00";
        String minuteString = "00";
        TimeInputHandler timeInputHandler = new TimeInputHandler(hourSpinner, minuteSpinner);
        if (dayOverviewController.getAppointmentTable() != null) {
            DayOverviewDto selectedItem = dayOverviewController.getAppointmentTable().getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                hourString = Integer.toString(selectedItem.getTimeLabel().getTime().getHour());

                minuteString = Integer.toString(selectedItem.getTimeLabel().getTime().getMinute());
                if (minuteString.equals("0")) {
                    minuteString = "00";
                }
            }
            hourSpinner.getValueFactory().setValue(hourString);
            minuteSpinner.getValueFactory().setValue(minuteString);
        }
    }

//    private boolean isInteger(String string){
//        try {
//            int i = Integer.parseInt(string);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
    @FXML
    private void datePickerHandler() {

    }

    /**
     *
     * Filters and sorts listView data according to the given searchField string.
     * Activated when a key is typed in the search field.
     */
    @FXML
    private void searchFieldHandler() {
        FilteredList<Contact> filteredData = new FilteredList<>(getListItems(), filter -> true);
        final String newValue = searchField.getText();

        filteredData.setPredicate((Predicate<? super Contact>) contact -> {
            String lowerCasefilter = newValue.toLowerCase();
            if (newValue.isEmpty()) {
                return true;
            } else if (contact.getFullName().toLowerCase().contains(lowerCasefilter)) {
                return true;
            }
            return false;
        });

        SortedList<Contact> sortedData = new SortedList<>(filteredData);
        listView.setItems(sortedData);
    }

    @FXML
    private void createButtonHandler() {
        if (isValidFields()) {
            setInfoMessage("");
            if (appointmentDataConfirmation()) {
                persistAppointment();
                closeStage();
            }
        }
    }

    @FXML
    private void cancelButtonHandler() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean isValidFields() {
        if (isEmptyDate()) {
            setInfoMessage("* Παρακαλώ εισάγετε ημερομηνία");
            return false;
        } else if (isEmptyTime()) {
            setInfoMessage("* Παρακαλώ εισάγετε ώρα");
            return false;
        } else if (isEmptyList()) {
            return addContactConfirmation();
        } else if (isItemNotSelected()) {
            setInfoMessage("* Παρακαλώ επιλέξτε όνομα απο την λίστα");
            return false;
        }
        return true;
    }

    // Probably animated with Opacity
    private void setInfoMessage(String string) {
//        Timeline (javafx.util.animation)
        messageLabel.setText(string);
    }

    private void animateMessage() {
//        Timeline timeline = new Timeline(
//          new KeyFrame(Duration.seconds(0),
//            new EventHandler<ActionEvent>() {
//              @Override public void handle(ActionEvent actionEvent) {
//                Calendar time = Calendar.getInstance();
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                setText(simpleDateFormat.format(time.getTime()));
//              }
//            }
//          ),
//          new KeyFrame(Duration.seconds(1))
//        );
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
    }

    private boolean addContactConfirmation() {
        ButtonType confirm = new ButtonType("Ναι", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Όχι", ButtonBar.ButtonData.CANCEL_CLOSE);
        String head = String.format("* Δεν υπάρχει επαφή με όνομα : [ %s ]\nΝα δημιουργηθεί ;", searchField.getText());
        boolean result = customCommonDialogue(head, "", confirm, cancel);
        if (result) {
            persistContact();
        }
        return result;
    }

    private boolean appointmentDataConfirmation() {
        ButtonType confirm = new ButtonType("Επιβεβαίωση", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Ακύρωση", ButtonBar.ButtonData.CANCEL_CLOSE);
        String head = "Το ραντεβού με τα παρακάτω στοιχεία πρόκειται να αποθηκευτεί";
        String fullName = ((Contact)listView.getSelectionModel().getSelectedItem()).getFullName();
        String date = datePicker.getEditor().getText();
        String hour = hourSpinner.getEditor().getText();
        String minute = minuteSpinner.getEditor().getText();
        String body = String.format("\t-Ονοματεπώνυμο : %s\n\n"
                                  + "\t-Ημερομηνία         : %s\n\n"
                                  + "\t-Ώρα : %s:%s\n\n", fullName, date, hour, minute);
        return customCommonDialogue(head, body, confirm, cancel);
    }

    private boolean customCommonDialogue(String header, String body, ButtonType confirm, ButtonType cancel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, body, confirm, cancel);
        alert.setTitle("Επιβεβαίωση");
        alert.setHeaderText(header);
        alert.showAndWait();
        ButtonType result = alert.getResult();
        return result == confirm;
    }

    /**
     * Fix this Method.
     */
    private void persistContact() {
        Contact contact = springContext.getBean(Contact.class);
        contact.setFirstName(getFirstName());
        contact.setLastName(getLastName());

        Stage newContactStage = stageManager.newStage(FxmlView.EDIT_CONTACT);
        mainAnchorController.setFields(contact);
        newContactStage.showAndWait();
    }

    private void persistAppointment() {
        appointmentService.save(getAppointmentData());
    }

    private Appointment getAppointmentData() {
        Appointment appointment = springContext.getBean(Appointment.class);
        appointment.setContact(getContact()); // Checked!
        appointment.setAppointedDateTime(getDateTime());// Checked for empty with validFields() and passed
        LocalDateTime currentTime = LocalDateTime.now();
        appointment.setTimeCreated(currentTime);
        appointment.setLastModified(currentTime);
        appointment.setStatus(PENDING.toString());
        appointment.setNotes(getNotes());

        return appointment;
    }

    private Contact getContact() { // a pile of bull
        Contact lastContact = mainAnchorController.getLastContact();
        Contact contact;
        if (lastContact != null) {
            contact = lastContact;
            mainAnchorController.setLastContact(null);
        } else {
            contact = (Contact) listView.getSelectionModel().getSelectedItem();
        }

        return contact;
    }

    private LocalDateTime getDateTime() {
        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.of(Integer.parseInt(getHour()), Integer.parseInt(getMinute()));
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return dateTime;
    }

    private Note getNotes() {
        if (!notesTextArea.getText().isEmpty()) {
            return persistNote();
        }
        return null;
    }

    private Note persistNote() {
        Note note = springContext.getBean(Note.class);
        note.setText(notesTextArea.getText());
        return notesService.save(note).orElseThrow();
    }

    private boolean isEmptyDate() {
        return datePicker.editorProperty().getValue().getText().equals("");
    }

    private boolean isEmptyTime() {
        return getHour().equals("") || getMinute().equals("");
    }

    private boolean isEmptyList() {
        return listView.getItems().isEmpty();
    }

    private boolean isItemNotSelected() {
        return listView.getSelectionModel().getSelectedItem() == null;
    }

    private List<String> getWordsListFromString(String text) {
        byte textArray[] = text.getBytes(StandardCharsets.UTF_8);
        String inputString = new String(textArray, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(inputString);
        List<String> stringList = new ArrayList<>();
        while (scanner.hasNext()) {
            stringList.add(scanner.next().strip());
        }
        return stringList;
    }

    private String getFirstName() {
        String firstName;
        List<String> textList = getWordsListFromString(searchField.getText());
        firstName = textList.get(0);
        return firstName;
    }

    private String getLastName() {
        List<String> textList = getWordsListFromString(searchField.getText());
        String emptyString = "";
        int size = textList.size();
        if (size > 1) {
            int lastElement = size - 1;
            return textList.get(lastElement);
        }
        return emptyString;
    }

    private void initNotesTextArea() {

    }

    private String getHour() {
        return (String) hourSpinner.getValue();
    }

    private String getMinute() {
        return (String) minuteSpinner.getValue();
    }

    public void setUserData(Appointment appointment) {
        searchField.setText(appointment.getContact().getFullName());
        searchFieldHandler();
        listView.getSelectionModel().selectFirst();
    }

}
