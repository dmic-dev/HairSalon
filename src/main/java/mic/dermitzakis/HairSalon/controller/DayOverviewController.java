/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.view.FxmlController;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import mic.dermitzakis.HairSalon.event.MyLabel;
import mic.dermitzakis.HairSalon.event.RowEventObserver;
import mic.dermitzakis.HairSalon.event.RowEventPublisher;
import mic.dermitzakis.HairSalon.model.AppointmentStatus;
import mic.dermitzakis.HairSalon.services.DataLoaderService;

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
    
    private Optional<List<Appointment>> appointments;
    private long selectedItem;

    @Autowired
    public DayOverviewController(ApplicationContext springContext) {
        this.springContext = springContext;
        this.dataLoaderService = springContext.getBean(DataLoaderService.class);
        this.rowEventPublisher = springContext.getBean(RowEventPublisher.class);
        rowEventPublisher.registerObserver(this);
    }

    @Override
    public void update(long selectedItem, long focusedItem, AppointmentStatus status) {
        this.selectedItem = selectedItem;
    }

    @Override
    public void initialize() {
        assignTableEventHandler();
        assignTableColumns();
        insertDataIntoTable(); ///// ???????????????
        restorePreviusState(); /// what if date changes ????
    }

    private void assignTableEventHandler(){
        appointmentTable.setOnMouseExited(this);
    }
    
    private void assignTableColumns(){
        time_Column.setCellValueFactory(new PropertyValueFactory<>("timeLabel"));
        name_Column.setCellValueFactory(new PropertyValueFactory<>("namesVbox"));
        operations_Column.setCellValueFactory(new PropertyValueFactory<>("operationsVbox"));
        notes_Column.setCellValueFactory(new PropertyValueFactory<>("notesVbox"));
        status_Column.setCellValueFactory(new PropertyValueFactory<>("statusVbox"));
    }
    
    private void insertDataIntoTable(){
        appointments = dataLoaderService.getWeeksAppointments(); ///// Should be todays appointments
        appointmentTable.setItems(getTimetable());
    }
    
    private void restorePreviusState() { // incomplete
        if (appointments.isPresent()){
            InitializeTable();
            setDetailsArea();
        }
    }

    private void InitializeTable() {
        setRowInformation();
    }

    @Override
    public void handle(MouseEvent event) {
       if (event.getSource().getClass() == TableView.class){
            rowEventPublisher.setRowInformation(selectedItem, -1, null);
        }
    }

    private void setDetailsArea() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    private ObservableList<AppointmentDto> getTimetable(){
        DayOverviewManager dayOverviewManager = springContext.getBean(DayOverviewManager.class);
        List<Appointment> appointmentList = appointments.orElse(FXCollections.observableArrayList());
        return dayOverviewManager.getAppointmentDtoList(appointmentList);
    }

    
     
    private void setRowInformation(){
        long firstItemId = getFirstLabel().getIdentity();
        Appointment appointment = appointments.get().get((int)firstItemId);/// How does it work?
        rowEventPublisher.setRowInformation(firstItemId, firstItemId, AppointmentStatus.extractStatus(appointment));
    }
 
//    private void notifyTableRows(){
//        Iterator namesVBoxItr;
//        for (AppointmentDto appointmentDto : appointmentTable.getItems()){
//            namesVBoxItr = appointmentDto.getNamesVbox().getChildrenUnmodifiable().iterator();
//            while (namesVBoxItr.hasNext()){
//                myLabel = (MyLabel)namesVBoxItr.next();
//                notifyLabel();
//            }
//        } 
//    }
//    
//    private void notifyLabel() {
//        appointments.get().stream()
//                .findFirst()
//                .filter(this::matchingLabelIdentity).stream()
//                .forEach(this::updateLabel);
//    }
//    
//    private boolean matchingLabelIdentity(Appointment appointment){
//        return appointment.getAppointmentId() == myLabel.getIdentity();
//    }
//    
//    private void updateLabel(Appointment appointment){
//        myLabel.update(stateDetails.getSelectedItem(), stateDetails.getFocusedItem(), AppointmentStatus.extractStatus(appointment));
//    }
    
/////////////  DANGER - DANGER - DANGER  \\\\\\\\\\\\\\
    private MyLabel getFirstLabel(){ // !!!!!  επικίνδυνη ρουτίνα!!!!
        ObservableList<AppointmentDto> dtoList = appointmentTable.getItems();
        ObservableList<Node> namesVBoxList = dtoList.get(0).getNamesVbox().getChildrenUnmodifiable();
        for (var dto : dtoList){
            namesVBoxList = dto.getNamesVbox().getChildrenUnmodifiable();
            if (!namesVBoxList.isEmpty()){
                break;
            }
        }
        return (MyLabel)namesVBoxList.get(0); // !!!!!!!!
    }
    
//    @Override
//    public TableviewStateDetails getState() {
//        stateDetails.setPlaceholder(appointmentTable.getPlaceholder());
//        return stateDetails;
//    }
//
//    @Override
//    public void setState(TableviewStateDetails stateDetails) { // fix AppointmentStatus
//        rowEventPublisher.setRowInformation(stateDetails.getSelectedItem(), stateDetails.getFocusedItem(), AppointmentStatus.PENDING);
//        appointmentTable.setPlaceholder(stateDetails.getPlaceholder());
//    }

}
