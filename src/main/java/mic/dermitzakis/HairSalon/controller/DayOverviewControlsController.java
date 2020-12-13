/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.various.DateInputHandler;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lombok.Data;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import mic.dermitzakis.HairSalon.model.Activity;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.view.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * FXML Controller class
 *
 * @author mderm
 */
@Controller
@Data
public class DayOverviewControlsController implements FxmlController {
    private final ApplicationContext springContext;
    @FXML private final DayOverviewController dayOverviewController;
    @FXML private final CurrentTimeIndicatorController timeIndicatorController;
    
    @FXML private AnchorPane dayOverviewControls;
    
    @FXML private DatePicker datePicker;
    @FXML private TextField searchField;
    
    @FXML private Button previusDateButton;
    @FXML private Button nextDateButton;
    @FXML private Button todayButton;
    @FXML private Button clearButton;

    @Autowired 
    public DayOverviewControlsController(ApplicationContext springContext, DayOverviewController dayOverviewController, CurrentTimeIndicatorController timeIndicatorController) {
        this.springContext = springContext;
        this.dayOverviewController = dayOverviewController;
        this.timeIndicatorController = timeIndicatorController;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        setDatePickerProperties();
    }    

    @FXML
    private void searchFieldHandler(){
        SearchString searchString = new SearchString();
        FilteredData filteredData = new FilteredData();
        String newValue = searchField.getText().strip();
        
        timeIndicatorController.getIndicator().setLayoutY(0);
        filteredData.setData(new FilteredList<>(getTableData(), filter -> true));
        filteredData.getData().setPredicate((Predicate<? super Appointment>) appointment -> {
            searchString.setString(newValue);
            String lowerCasefilter = newValue.toLowerCase();
            if(newValue.isBlank()) {
                return true;
            } else if (timeContains(appointment, lowerCasefilter)) {
                return true;
            } else if (appointment.getContact().getFullName().toLowerCase().contains(lowerCasefilter)) {
                return true;
            } else if (operationsContains(appointment, lowerCasefilter)) {
                return true;
            } else if (notesContains(appointment, lowerCasefilter)) {
                return true;
            }
            return false;
        });
        DayOverviewTableLoader tableLoader = springContext.getBean(DayOverviewTableLoader.class);
        ObservableList<DayOverviewDto> data = tableLoader.getDayOverviewDataList(filteredData.getData());

        if (searchString.getString().isBlank()){
            dayOverviewController.getAppointmentTable().setItems(data);
        } else {
            ObservableList<DayOverviewDto> searchedData = tableLoader.getFilteredData(data, searchString.getString());
            dayOverviewController.getAppointmentTable().setItems(searchedData);
        }
        
        dayOverviewController.getBoundsHashMap().clear();
    } 
    
    private boolean timeContains(Appointment appointment, String string) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
        return appointment.getAppointedDateTime().toLocalTime().format(dtf).contains(string);
    }

    private boolean operationsContains(Appointment appointment, String string) {
        if (appointment.getOperations().isEmpty()) return false;
        return appointment.getOperations().stream().anyMatch((Activity e) -> e.getDescription().toLowerCase().equals(string));
    }
    
    private boolean notesContains(Appointment appointment, String string){
        if (appointment.getNotes() == null) return false;
        return appointment.getNotes().getText().toLowerCase().contains(string);
    }
    
/**
 * Gets Data from appointments field variable. 
 */
    private ObservableList<Appointment> getTableData() {
        ObservableList<Appointment> observableList = FXCollections.observableArrayList();
        if (dayOverviewController.getAppointments().isPresent()) {
            observableList.addAll(dayOverviewController.getAppointments().get());
        }
        return observableList;
    }
   
    @FXML
    private void datePickerActionHandler(){
        dayOverviewController.setCurrentDate(datePicker.getValue());
        dayOverviewController.getTodaysAppointments();
        searchFieldHandler();
        dayOverviewController.restoreState();
    }
    
    @FXML
    private void todayButtonHandler(){
        searchField.setText("");
        timeIndicatorController.getIndicator().setLayoutY(0);
        dayOverviewController.setCurrentDate(LocalDate.now());
        datePicker.setValue(dayOverviewController.getCurrentDate());
        dayOverviewController.getTodaysAppointments();
        dayOverviewController.getBoundsHashMap().clear();
        
        DayOverviewTableLoader tableLoader = springContext.getBean(DayOverviewTableLoader.class);
        dayOverviewController.getAppointmentTable().setItems(tableLoader.getTableItems(dayOverviewController.getAppointments()));
        
        dayOverviewController.restoreState();

    }

    @FXML
    private void clearButtonHandler(){
        searchField.setText("");
        searchFieldHandler();
    }
    
    @FXML
    private void previusDateHandler(){
        dayOverviewController.setCurrentDate(datePicker.getValue().minusDays(1));
        datePicker.setValue(dayOverviewController.getCurrentDate());
        datePickerActionHandler();
    }
    
    @FXML
    private void nextDateHandler(){
        dayOverviewController.setCurrentDate(datePicker.getValue().plusDays(1));
        datePicker.setValue(dayOverviewController.getCurrentDate());
        datePickerActionHandler();
    }
    
    private void setDatePickerProperties(){
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E, d MMM yyyy");
                return date.format(dtf);
            }

            @Override
            public LocalDate fromString(String string) {
                return DateInputHandler.getDate(string).orElse(LocalDate.now());
            }
        };
        
        datePicker.setConverter(converter);
    }

    @Data
    private class FilteredData {
        private FilteredList<Appointment> data = new FilteredList<>(getTableData(), filter -> true);
    }
    
    @Data
    private final class SearchString {
        private String string = "";
    }
    
}
