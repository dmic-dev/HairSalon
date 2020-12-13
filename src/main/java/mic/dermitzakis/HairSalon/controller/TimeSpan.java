/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author mderm
 */
@Data
class TimeSpan {
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;
}
