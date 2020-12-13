/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import mic.dermitzakis.HairSalon.bootstrap.SampleData;
import mic.dermitzakis.HairSalon.custom.DayTableLabel;
import mic.dermitzakis.HairSalon.model.Appointment;
import mic.dermitzakis.HairSalon.model.EntityType;
import mic.dermitzakis.HairSalon.repository.AppointmentRepository;
import mic.dermitzakis.HairSalon.repository.CacheService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author mderm
 */

@SpringBootTest  //@DataJpaTest //
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DayOverviewControllerTest {
    private final ApplicationContext springContext;
    private DayOverviewController dayOverviewController = null;
    private SampleData sampleDataAccessManager;
    private CacheService dataLoaderService;
    private Optional<List<Appointment>> findByAppointedDateBetween;
    
    @MockBean
    private AppointmentRepository appointmentRepository;

    @Autowired
    public DayOverviewControllerTest(ApplicationContext springContext) {
        this.springContext = springContext;
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp(){
        dayOverviewController = springContext.getBean(DayOverviewController.class);
        sampleDataAccessManager = springContext.getBean(SampleData.class);
        dataLoaderService = springContext.getBean(CacheService.class);
        appointmentRepository = springContext.getBean(AppointmentRepository.class);
        EntityType entityType = dataLoaderService.getEntityType();
        List<Appointment> read = (List<Appointment>) sampleDataAccessManager.read(entityType);
        
        Mockito.when(appointmentRepository.findByAppointedDateBetween(entityType.getStartingDate(), entityType.getEndingDate()))
               .thenReturn(Optional.of(read));
        
//        findByAppointedDateBetween = Mockito.verify(appointmentRepository).findByAppointedDateBetween(entityType.getStartingDate(), entityType.getEndingDate());
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetFirstLabel_ShouldBeEqualToFirstContact(){
        DayTableLabel expected = springContext.getBean(DayTableLabel.class);
        expected.setText("Martin Welsch");
        DayTableLabel actual = dayOverviewController.getFirstNonEmptyLabel();
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Method getFirstVBox should not return null")
    public void testGetFirstVBox_ShouldNotBeNull(){
        ObservableList<Node> actual = dayOverviewController.getFirstVBox();
        assertNotNull(actual, "getFirstVBox returns null");
    }
    
    @Test 
    public void testGetAppointments_ShouldNotBeEmptyList(){
        List<Appointment> actual = dayOverviewController.getAppointments();
        assertNotEquals(actual, new ArrayList<>());
//        assertEquals(actual.getClass(),ArrayList.class);
    }
    
    @Test
    public void testGetAppointments_EqualsMockedList(){
        assertIterableEquals(findByAppointedDateBetween.get(), dayOverviewController.getAppointments());
    }
    
	@Test
	public void contextLoads() {
            assertTrue(springContext != null, "ApplicationContext found to be null");
	}
    
	@Test
	public void dayOverviewControllerLoads() {
            assertTrue(dayOverviewController != null, "ApplicationContext found to be null");
	}
    
}
