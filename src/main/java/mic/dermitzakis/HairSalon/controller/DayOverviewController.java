/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.net.URL;
import java.util.ArrayList;
import mic.dermitzakis.HairSalon.view.FxmlController;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;
import mic.dermitzakis.HairSalon.dto.AppointmentDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import mic.dermitzakis.HairSalon.event.CustomLabel;
import mic.dermitzakis.HairSalon.event.RowEventObserver;
import mic.dermitzakis.HairSalon.event.RowEventPublisher;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import mic.dermitzakis.HairSalon.services.DataLoaderService;
import net.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Lazy;

//@Data
@Controller
public class DayOverviewController implements FxmlController, RowEventObserver, EventHandler<MouseEvent>{
    private final ApplicationContext springContext;
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
    private Text gender_txt;
    @FXML
    private Text dob_txt;
    @FXML
    private Text date_created_txt;
    @FXML
    private Text last_modified_txt;
    
    private List<Appointment> appointments;
    private long selectedItem;
    private static DayOverviewController instance;
    
    public static DayOverviewController getInstance(){
        return instance;
    }
    
    @Autowired
    public DayOverviewController(ApplicationContext springContext) {
        this.springContext = springContext;
        this.dataLoaderService = springContext.getBean(DataLoaderService.class);
        this.rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        instance = this;
    }

    @Override
    public void update(long selectedItem, long focusedItem, AppointmentStatus status) {
        this.selectedItem = selectedItem;
    }

    @Override
    public void initialize() {
        assignTableColumns();
        insertDataIntoTable(); ///// ???????????????
        assignTableEventHandler();
        notifyTableRows();
        setDetailsArea();
    }

    private void assignTableEventHandler(){
        appointmentTable.setOnMouseExited(this);
        rowEventPublisher.registerObserver(this);
    }
    
    private void assignTableColumns(){
        time_Column.setCellValueFactory(new PropertyValueFactory<>("timeLabel"));
        name_Column.setCellValueFactory(new PropertyValueFactory<>("namesVbox"));
        operations_Column.setCellValueFactory(new PropertyValueFactory<>("operationsVbox"));
        notes_Column.setCellValueFactory(new PropertyValueFactory<>("notesVbox"));
        status_Column.setCellValueFactory(new PropertyValueFactory<>("statusVbox"));
    }
    
    private void insertDataIntoTable(){
        // Should be todays appointments
        appointments = getAppointments();
//        if (appointments == FXCollections.observableArrayList())
//            System.out.println("");
        appointmentTable.setItems(getTimetable());
    }
    
    public List<Appointment> getAppointments(){
        return dataLoaderService.getWeeksAppointments().orElse(new ArrayList<>());// Should be todays appointments
    }
    
    @Override
    public void handle(MouseEvent event) {
       if (event.getSource().getClass() == TableView.class){
            rowEventPublisher.setRowInformation(selectedItem, 0, null);
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
        long firstItemId = getFirstLabel().getIdentity();
        Appointment appointment = appointments.get((int)firstItemId);/// How does it work? //check!
        rowEventPublisher.setRowInformation(firstItemId, firstItemId, AppointmentStatus.extractStatus(appointment));
    }
 
    public CustomLabel getFirstLabel(){ // !!!!!  επικίνδυνη ρουτίνα!!!!
        ObservableList<Node> namesVBox = getFirstVBox();
        for (var dto : appointmentTable.getItems()){
            namesVBox = dto.getNamesVbox().getChildrenUnmodifiable();
            if (((CustomLabel)namesVBox.get(0)).getIdentity() > 0){
                break;
            }
        }
        return (CustomLabel)namesVBox.get(0); // !!!!!!!! get first Label
    }

    public ObservableList<Node> getFirstVBox() {
        return appointmentTable.getItems().get(0).getNamesVbox().getChildrenUnmodifiable();
    }

}
