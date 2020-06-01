package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        stage.setTitle( "Dungeons & Dragons vs Rick & Morty" );

        Text title = new Text("Dungeons & Dragons vs Rick & Morty");
        title.setFont(Font.font("Gothic MS", FontWeight.EXTRA_BOLD, 50));
        title.setTextAlignment( TextAlignment.CENTER );

        Image image = new Image(getClass().getResourceAsStream("/img/title-screen.jpg"));
        ImageView imageView = new ImageView( image );

        imageView.setFitWidth(900);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);

        Button btn = new Button();
        btn.setText("New Game");
        btn.setOnAction( getNewGameHandler() );

        VBox vBox = new VBox();
        vBox.setAlignment( Pos.CENTER );
        vBox.getChildren().addAll( title, imageView, btn );

        StackPane root = new StackPane();
        root.getChildren().add( vBox );
        stage.setScene(new Scene(root, 1440, 900));
    }

    private EventHandler<ActionEvent> getNewGameHandler()
    {
        return event -> {
            new CharacterSelection( stage ).setStage();
        };
    }
}
