/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mderm
 */
@Service
//@CacheConfig(cacheNames = "appointmentCache")
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    
//    @Cacheable(cacheNames = "appointmentCache")
    public Optional<List<Appointment>> findAll() {
        return Optional.of(appointmentRepository.findAll());
    }
    
//    @Cacheable(cacheNames = "appointmentCache", key = "#id")
    public Optional<Appointment> findAppointmentById(Long id) {
        return appointmentRepository.findByAppointmentId(id);
    }
    
    public Optional<List<Appointment>> findAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointedDateBetween(date.atStartOfDay(), date.atTime(LocalTime.MAX));
    }
//    @CachePut(cacheNames = "appointmentCache", key = "#appointment.appointmentId")
    public Optional<Appointment> save(Appointment appointment) {
        return Optional.of(appointmentRepository.save(appointment));
    }
    
//    @CachePut(cacheNames = "appointmentCache", key = "#appointment.appointmentId")
    public Optional<Appointment> update(Appointment appointment) {
        return Optional.of(appointmentRepository.save(appointment));
    }
    
//    @CacheEvict(cacheNames = "appointmentCache", key = "#appointment.appointmentId")
    public void delete(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }
    
//    @CacheEvict(cacheNames = "appointmentCache", key = "#id")
    public void deleteById(Long id) {
        appointmentRepository.deleteByAppointmentId(id);
    }
    
    
}
