/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.various;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.Data;

/**
 *
 * @author mderm
 */
@Data
public class DateInputHandler {

    private static final List<DateTimeFormatter> PATTERN_LIST = List.of(
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("d-M-yyyy"),
                DateTimeFormatter.ofPattern("d/M/yy"),
                DateTimeFormatter.ofPattern("d-M-yy")
        );

    private static Optional<LocalDate> validateInput(String text, DateTimeFormatter formatter) {
        LocalDate validDate;
        try {
            validDate = LocalDate.parse(text, formatter);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(validDate);
    }

    public static Optional<LocalDate> getDate(String text) {
        Optional<LocalDate> optDate = Optional.empty();
        for (var formatter : PATTERN_LIST) {
            optDate = validateInput(text, formatter);
            if (optDate.isPresent()) {
                return optDate;
            }
        }
        return optDate;
    }
}
