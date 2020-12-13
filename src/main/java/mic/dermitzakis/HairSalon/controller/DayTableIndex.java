/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.time.LocalTime;
import java.util.List;
import javafx.scene.control.TableView;
import lombok.Data;
import mic.dermitzakis.HairSalon.dto.DayOverviewDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class DayTableIndex implements TableIndex {
    public static final Integer STANDARD_ROWS = 25;
    private Integer value = -1;

    @Override
    public void increase() {
        value++;
    }

    @Override
    public void decrease() {
        value--;
    }
    
    public int calcPrevOffset(TableView<DayOverviewDto> table){
        List<DayOverviewDto> items = table.getItems();
        int accumulator = 0;
        for(var item : items){
            if(item.getTimeLabel().getTime().isBefore(LocalTime.of(9, 0))) accumulator++;
            else return accumulator;
        }
        return accumulator;
    }
    
    public int calcNextOffset(TableView<DayOverviewDto> table){
        List<DayOverviewDto> items = table.getItems();
        return 0;
    }
    
    public int getRowsFrom9oClockOnwards(TableView<DayOverviewDto> table){
        List<DayOverviewDto> items = table.getItems();
        int accumulator = 0;
        for(var item : items){
            if(item.getTimeLabel().getTime().isBefore(LocalTime.of(9, 0))) {}
            else accumulator++;
        }
        return accumulator;
    }

}
