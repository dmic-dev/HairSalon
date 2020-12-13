/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.various;

import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mderm
 */

public class TimeInputHandler {
    private final Spinner hourSpinner;
    private final Spinner minuteSpinner;
    private static final ObservableList<String> HOUR_LIST = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private static final ObservableList<String> MINUTE_LIST = FXCollections.observableArrayList("00", "15", "30", "45");

    @Autowired
    public TimeInputHandler(Spinner hourSpinner, Spinner minuteSpinner) {
        this.hourSpinner = hourSpinner;
        this.minuteSpinner = minuteSpinner;
        setHourSpinnerProperties();
        setMinuteSpinnerProperties();
    }


    private void setHourSpinnerProperties(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change c) -> {
            if (isInteger(c.getControlNewText())) {
                if (c.isContentChange()) {
                    if (c.isReplaced()) {
                        if (isInvalidHour(c) || isInvalidLength(c)) {
                            return null;
                        }
                    } else 
                    if (c.isDeleted()) {
                        return c;
                    } else 
                    if (c.isAdded()) {
                        if (isInvalidHour(c) || isInvalidLength(c)) {
                            return null;
                        }
                    }
                }
            } else c.setText("");

            return c;
        };
        TextFormatter<String> formatter = new TextFormatter<>(getStringConverter(hourSpinner), "00", filter);
        hourSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(HOUR_LIST));
        hourSpinner.setEditable(true);
        hourSpinner.getEditor().setTextFormatter(formatter);

    };
    
    private void setMinuteSpinnerProperties(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change c) -> {
            if (isInteger(c.getControlNewText())) {
                if (c.isContentChange()) {
                    if (c.isReplaced()) {
                        if (isInvalidMinute(c) || isInvalidLength(c)) {
                            return null;
                        }
                    } else 
                    if (c.isDeleted()) {
                        return c;
                    } else 
                    if (c.isAdded()) {
                        if (isInvalidMinute(c) || isInvalidLength(c)) {
                            return null;
                        }
                    }
                }
            } else c.setText("");

            return c;
        };
        TextFormatter<String> formatter = new TextFormatter<>(getStringConverter(minuteSpinner), "00", filter);
        minuteSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(MINUTE_LIST));
        minuteSpinner.setEditable(true);
        minuteSpinner.getEditor().setTextFormatter(formatter);
    };
    
    
    private StringConverter getStringConverter(Spinner spinner){
        StringConverter<String> stringConverter = new StringConverter<>() {
            @Override
            public String toString(String t) { // final outcome
                if (t.length() == 1) {
                    t = "0" + t;
                } else if (t.length() == 0) {
                    t = "00";
                }
                spinner.getValueFactory().setValue(t); // = crap
                spinner.commitValue();
                FXCollections.sort(MINUTE_LIST);

                return t;
            }

            @Override
            public String fromString(String string) { // process user input
                return string;
            }
        };
        return stringConverter;
    }

    private boolean isInvalidHour(TextFormatter.Change c) {
        return Integer.parseInt(c.getControlNewText()) > 23;
    }

    private boolean isInvalidMinute(TextFormatter.Change c) {
        return Integer.parseInt(c.getControlNewText()) > 59;
    }

    private boolean isInvalidLength(TextFormatter.Change c) {
        return c.getControlNewText().length() > 2; 
    }    

    private boolean isInteger(String string){
        try {
            int i = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
