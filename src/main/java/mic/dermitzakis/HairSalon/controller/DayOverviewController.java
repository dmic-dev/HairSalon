/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.net.URL;
import java.util.ArrayList;
import mic.dermitzakis.HairSalon.view.FxmlController;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.dto.AppointmentViewDetailsDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import mic.dermitzakis.HairSalon.event.CustomLabel;
import mic.dermitzakis.HairSalon.event.EventRowSelected;
import mic.dermitzakis.HairSalon.event.RowEventObserver;
import mic.dermitzakis.HairSalon.event.RowEventPublisher;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import mic.dermitzakis.HairSalon.model.Contact;
import mic.dermitzakis.HairSalon.model.Contact.Gender;
import mic.dermitzakis.HairSalon.repository.DefaultImages;
import mic.dermitzakis.HairSalon.services.DataLoaderService;

@Data
@Controller
public class DayOverviewController implements FxmlController, RowEventObserver, EventHandler<MouseEvent> {
    private final ApplicationContext springContext;
    private final EventBus eventBus;
    private final DataLoaderService dataLoaderService;
    private final RowEventPublisher rowEventPublisher;
    private static final Logger LOG = Logger.getLogger(DayOverviewController.class.getName());
    
    @FXML
    private TableView<AppointmentDto> appointmentTable;
    @FXML
    private TableColumn<AppointmentDto, Label> time_Column;
    @FXML
    private TableColumn<AppointmentDto, VBox> status_Column;
    @FXML 
    @Getter 
    private TableColumn<AppointmentDto, VBox> name_Column;
    @FXML
    private TableColumn<AppointmentDto, VBox> operations_Column;
    @FXML
    private TableColumn<AppointmentDto, VBox> notes_Column;

    
    @FXML
    private Text name_txt;
    @FXML
    private Text id_txt;
    @FXML
    private StackPane picture;
    @FXML
    private Text gender_txt;
    @FXML
    private Text dob_txt;
    @FXML
    private Text date_created_txt;
    @FXML
    private Text last_modified_txt;

    @FXML
    private Text appointment_date_created_txt;

    private List<Appointment> appointments;
    private UUID selectedItem;
    private static DayOverviewController instance;
    
    public static DayOverviewController getInstance(){
        return instance;
    }
    
    @Autowired
    public DayOverviewController(ApplicationContext springContext) {
        this.springContext = springContext;
        this.eventBus = springContext.getBean(EventBus.class);
        this.dataLoaderService = springContext.getBean(DataLoaderService.class);
        this.rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        instance = this;
    }

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }
    
    @Subscribe
    public void contactDetailsListener(EventRowSelected event){
        AppointmentViewDetailsDto details = event.getDetails();

//        System.out.println(contactDetailsDto.getName());
//        if (name_txt == null) System.out.println("null pointer Exception");
        name_txt.setText(details.getName());/*contactDetailsDto.getName()*/
        id_txt.setText(details.getId());/*contactDetailsDto.getId()*/
        ImageView imageView = new ImageView();
        imageView.setImage(details.getPicture());
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        picture.getChildren().add(imageView);
        gender_txt.setText(details.getGender());
        dob_txt.setText(details.getDob());
        date_created_txt.setText(details.getDateCreated());
        last_modified_txt.setText(details.getLastModified());
        appointment_date_created_txt.setText(details.getAppDateCreated());
    }
    
    @Override
    public void update(UUID selectedItem, UUID focusedItem, AppointmentStatus status) {
        this.selectedItem = selectedItem;
    }

    @Override
    public void initialize() {
        assignTableColumns();
        insertDataIntoTable(); ///// ???????????????
        assignTableEventHandler();
        notifyTableRows();
        setDetailsArea(); // Boooo!!!!
    }

    private void assignTableEventHandler(){
        appointmentTable.setOnMouseExited(this);
        rowEventPublisher.registerObserver(this);
    }
    
    private void assignTableColumns(){
        time_Column.setCellValueFactory(new PropertyValueFactory<>("timeLabel"));
        status_Column.setCellValueFactory(new PropertyValueFactory<>("statusVbox"));
        name_Column.setCellValueFactory(new PropertyValueFactory<>("namesVbox"));
        operations_Column.setCellValueFactory(new PropertyValueFactory<>("operationsVbox"));
        notes_Column.setCellValueFactory(new PropertyValueFactory<>("notesVbox"));
    }
    
    private void insertDataIntoTable(){
        appointments = getTodaysAppointments();
        appointmentTable.setItems(getTimetable());
    }
    
    public List<Appointment> getTodaysAppointments(){
        // Should be todays appointments
        return dataLoaderService.getWeeksAppointments().orElse(new ArrayList<>());// Should be todays appointments normaly
    }
    
    @Override
    public void handle(MouseEvent event) {
        if (event.getSource().getClass() == TableView.class){
            rowEventPublisher.setRowInformation(selectedItem, null, null);
        }
    }

    private void setDetailsArea() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    private ObservableList<AppointmentDto> getTimetable(){
        DayOverviewManager dayOverviewManager = springContext.getBean(DayOverviewManager.class);
        return dayOverviewManager.getAppointmentDtoList(appointments);
    }

    private void notifyTableRows(){
        CustomLabel label = getFirstNonEmptyLabel();
        Appointment appointment = appointments.get((int)label.getAppointmentId());/// How does it work? //check!
        rowEventPublisher.setRowInformation(label.getIdentity(), label.getIdentity(), AppointmentStatus.extractStatus(appointment));
    }
 
    public CustomLabel getFirstNonEmptyLabel(){ // !!!!!  επικίνδυνη ρουτίνα!!!!
        ObservableList<Node> namesVBox = getFirstVBox();
        for (var dto : appointmentTable.getItems()){
            namesVBox = dto.getNamesVbox().getChildrenUnmodifiable();
            if (((CustomLabel)namesVBox.get(0)).isEmpty() == false){
                break;
            }
        }
        return (CustomLabel)namesVBox.get(0); // !!!!!!!! get first Label
    }

    public ObservableList<Node> getFirstVBox() {
        return appointmentTable.getItems().get(0).getNamesVbox().getChildrenUnmodifiable();
    }
    
}
