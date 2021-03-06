/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Appointment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mderm
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    @EntityGraph(value = "Appointment.Overview", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT appointment FROM Appointment appointment WHERE appointedDateTime BETWEEN :startingDate AND :endingDate")
    Optional<List<Appointment>> findByAppointedDateBetween(@Param("startingDate")LocalDateTime startingDate, @Param("endingDate")LocalDateTime endingDate);
    @Query("SELECT appointment FROM Appointment appointment WHERE appointedDateTime BETWEEN :startingDate AND :endingDate AND contact.firstName = :first" )
    Optional<List<Appointment>> findByAppointedDateBetweenWhereFirstNameEquals(@Param("startingDate")LocalDateTime startingDate, @Param("endingDate")LocalDateTime endingDate, @Param("first")String firstName);
    Optional<List<Appointment>> findByAppointedDateTime(LocalDateTime date);
    
    public Optional<Appointment> findByAppointmentId(Long id);

    public void deleteByAppointmentId(Long id);
    
}
