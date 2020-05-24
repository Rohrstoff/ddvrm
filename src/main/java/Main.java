import gui.WelcomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(final String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new WelcomeScreen( stage );
    }
}