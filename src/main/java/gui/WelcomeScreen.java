package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomeScreen {

    private Stage stage;

    public WelcomeScreen( Stage stage )
    {
        this.stage = stage;
        setStage();
        stage.show();
    }

    public void setStage()
    {
        stage.setTitle( "Dungeons and Dragons vs Rick and Morty" );
        Button btn = new Button();
        btn.setText("New Game");
        btn.setOnAction( getNewGameHandler() );

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        stage.setScene(new Scene(root, 1440, 900));
    }

    private EventHandler<ActionEvent> getNewGameHandler()
    {
        return event -> {
            new CharacterSelection( stage ).setStage();
        };
    }
}
