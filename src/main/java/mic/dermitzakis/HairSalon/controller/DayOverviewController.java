/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import static mic.dermitzakis.HairSalon.controller.TableIndex.MINUS_ONE;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import mic.dermitzakis.HairSalon.model.Appointment;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.custom.TimeLabel;
import mic.dermitzakis.HairSalon.dto.RowDetailsDto;
import mic.dermitzakis.HairSalon.event.RowChangedEvent;
import mic.dermitzakis.HairSalon.repository.CacheService;
import mic.dermitzakis.HairSalon.services.AppointmentService;
import static mic.dermitzakis.HairSalon.model.Appointment.AppointmentStatus.extractStatus;

@Controller
@Data
@EqualsAndHashCode(callSuper = true)
public class DayOverviewController extends AbstractTableViewController implements EventHandler<MouseEvent> {
    private final ApplicationContext springContext;
    private final EventBus eventBus;
    private final CacheService cacheService;
    private final AppointmentService appointmentService;
    private final RowDetailsDto rowDetailsDto;
    private final RowChangedEvent rowChangedEvent;
    private final DayTableIndex tableIndex;  // for restoreState
    private static final Logger LOG = Logger.getLogger(DayOverviewController.class.getName());
    
    private final HashMap<LocalTime, Bounds> boundsHashMap = new HashMap<>();
    private LocalDate currentDate = LocalDate.now();
    private Optional<List<Appointment>> appointments;
    private UUID selectedItem;

    @FXML private BorderPane dayOverview;
    @FXML private Group tableGoup;
    @FXML private Group indicator;

    /*  Table */
    @FXML private TableView<DayOverviewDto> appointmentTable;
    @FXML private TableColumn<DayOverviewDto, TimeLabel> time_Column;
    @FXML private TableColumn<DayOverviewDto, VBox> status_Column;
    @FXML private TableColumn<DayOverviewDto, VBox> name_Column;
    @FXML private TableColumn<DayOverviewDto, VBox> operations_Column;
    @FXML private TableColumn<DayOverviewDto, VBox> notes_Column;


    public DayOverviewController(ApplicationContext springContext, EventBus eventBus, CacheService cacheService, AppointmentService appointmentService, RowDetailsDto rowDetailsDto, RowChangedEvent rowChangedEvent, DayTableIndex tableIndex) {
        this.springContext = springContext;
        this.eventBus = eventBus;
        this.cacheService = cacheService;
        this.appointmentService = appointmentService;
        this.rowDetailsDto = rowDetailsDto;
        this.rowChangedEvent = rowChangedEvent;
        this.tableIndex = tableIndex;
    }

    @PostConstruct
    private void init() {
        eventBus.register(this);
    }

    @Subscribe
    public void rowChangesListener(RowChangedEvent event) {
        RowDetailsDto data = event.getData();
        this.selectedItem = data.getSelectedItem();
    }

    @Override
    protected void setEventHandler() {
        appointmentTable.setOnMouseExited(this);
        appointmentTable.setOnMouseClicked(this);
    }

    @Override
    protected void assignTableColumns() {
        time_Column.setCellValueFactory(new PropertyValueFactory<>("timeLabel"));
        status_Column.setCellValueFactory(new PropertyValueFactory<>("statusVbox"));
        name_Column.setCellValueFactory(new PropertyValueFactory<>("namesVbox"));
        operations_Column.setCellValueFactory(new PropertyValueFactory<>("operationsVbox"));
        notes_Column.setCellValueFactory(new PropertyValueFactory<>("notesVbox"));
        setRowFactory();
    }

    @Override
    protected void insertDataIntoTable() {
        getTodaysAppointments();
//        getWeeksAppointments();   // for testing reasons
        appointmentTable.setItems(getTimetable());
    }

    public void getTodaysAppointments() {
        getAppointmentsByDate(currentDate);
    }

    private void getAppointmentsByDate(LocalDate date) {
        appointments = appointmentService.findAppointmentsByDate(date);
    }

    private void getWeeksAppointments() {
        appointments = cacheService.getWeeksAppointments();
    }

    /**
     * Should be changed... No use Of Manager...
     *
     * @return
     */
    private ObservableList<DayOverviewDto> getTimetable() {
        var tableLoader = springContext.getBean(DayOverviewTableLoader.class);
        return tableLoader.getTableItems(appointments);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getSource().getClass() == appointmentTable.getClass()) {

            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                setTableIndex();
                rowDetailsDto.setSelectedItem(selectedItem);
                rowChangedEvent.setData(rowDetailsDto);
                eventBus.post(rowChangedEvent);
            } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
                rowDetailsDto.setFocusedItem(null);
                rowChangedEvent.setData(rowDetailsDto);
                eventBus.post(rowChangedEvent);
            }
        }
    }

    /**
     * Apparently FIX this!!!.
     */
    private void setTableIndex() {
        int value = 0;
        int prevOffset = tableIndex.calcPrevOffset(appointmentTable);
        int rowOffset = tableIndex.getRowsFrom9oClockOnwards(appointmentTable); // bull

//        if (prevOffset == 0)
//            value = appointmentTable.getSelectionModel().getSelectedIndex();
//        
//        tableIndex.setValue(value);
        tableIndex.setValue(appointmentTable.getSelectionModel().getSelectedIndex());
    }

    /**
     * Fix This.
     *
     * Maybe by adding postRowChangedEvent to label
     */
    @Override
    protected void restoreState() { // table.restoreState();
        Optional<DayTableLabel> label = getFirstNonEmptyLabel(); // table.getFirstNonEmptyRow();
        if (label.isPresent()) { // !row.isEmpty();
//            row.postRowChangedEvent(); // Theoretically
            selectRow(tableIndex.getValue()); // row.select();
/////  Perhaps should put this chunk of code inside (Row class)
            Appointment appointment = appointmentService.findAppointmentById(label.get().getAppointmentId()).orElseThrow(); // How does it work? //checked!
            rowDetailsDto.setDetails(label.get().getRowId(), label.get().getRowId(), extractStatus(appointment));
            rowChangedEvent.setData(rowDetailsDto);
            eventBus.post(rowChangedEvent);
/////-------------------------------------            
            label.get().postSideDetails(); // row.postSideDetails(); //

        } else {
//            selectRow(tableIndex.getValue()); // row.select();
            DayTableLabel nameLabel = (DayTableLabel) appointmentTable.getItems().get(0).getNamesVbox().getChildrenUnmodifiable().get(0);
            nameLabel.postSideDetails();
        }

        DayOverviewControlsController controlsController = springContext.getBean(DayOverviewControlsController.class);
        controlsController.getDatePicker().setValue(currentDate);
    }

    private void selectRow(Integer value) {
        appointmentTable.getSelectionModel().select(value);
        appointmentTable.getSelectionModel().focus(value);
    }

    // !!!!!  επικίνδυνη ρουτίνα (-not any more-)!!!!  FIXED!!!!! Yeepy!!!!!
    public Optional<DayTableLabel> getFirstNonEmptyLabel() {
        DayTableLabel nameLabel;
        tableIndex.setValue(MINUS_ONE);
        for (var dto : appointmentTable.getItems()) {
            tableIndex.increase();
            nameLabel = (DayTableLabel) dto.getNamesVbox().getChildrenUnmodifiable().get(0);
            if (!nameLabel.getText().equals("")) {
                return Optional.of(nameLabel);
            }
        }
        tableIndex.setValue(MINUS_ONE);
        return Optional.empty();
    }

    private ImageView newImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        return imageView;
    }

//    @Override
//    public void restoreState() {
//        appointmentTable.scrollTo(tableIndex.getValue());
//        appointmentTable.getSelectionModel().select(tableIndex.getValue());
////        appointmentTable.getFocusModel().focus(currentItemPosition);
//        rowDetailsDto.setDetails(selectedItem, selectedItem, Appointment.AppointmentStatus.EMPTY);
//        rowChangedEvent.setData(rowDetailsDto);
//        eventBus.post(rowChangedEvent);
//    }
    
    public void setRowFactory() {
        appointmentTable.setRowFactory((TableView<DayOverviewDto> tableView) -> {
            TableRow<DayOverviewDto> row = new TableRow<>();
            row.boundsInParentProperty().addListener((ObservableValue, oldBounds, newBounds) -> {
                if (row.getItem() != null){
                    LocalTime time = row.getItem().getTimeLabel().getTime();
                    boundsHashMap.put(time, newBounds);
                }
            });
            return row;
        });
    }

    
}
