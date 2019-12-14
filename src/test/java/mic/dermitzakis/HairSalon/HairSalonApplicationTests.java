package mic.dermitzakis.HairSalon;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.junit.jupiter.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class HairSalonApplicationTests {
//    @Autowired
    private final ApplicationContext springContext;

    @Autowired
    public HairSalonApplicationTests(ApplicationContext springContext) {
        this.springContext = springContext;
    }

	@Test
	public void contextLoads() {
            assertTrue(springContext != null, "ApplicationContext found to be null");
	}

}
