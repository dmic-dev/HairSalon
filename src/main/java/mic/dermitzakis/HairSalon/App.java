package mic.dermitzakis.HairSalon;

import mic.dermitzakis.HairSalon.view.FxmlView;
import mic.dermitzakis.HairSalon.view.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class App extends Application {

    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void init() {
        springContext = bootstrapSpringApplicationContext();
    }

    @Override
    public void start(Stage stage) throws Exception {
//        TestClass testClass = springContext.getBean(TestClass.class); testClass.show();
        stageManager = springContext.getBean(StageManager.class, stage);
        stageManager.newStage(FxmlView.MAIN);
    }
    
    @Override
    public void stop(){
        springContext.stop();
    }
    
    // PRIVATE METHOD //
    private ConfigurableApplicationContext bootstrapSpringApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        builder.headless(false); //needed for TestFX integration testing or else will get a java.awt.HeadlessException during tests
        return builder.run(args);
    }
    
}
//                SpringApplication.run(App.class, args);
