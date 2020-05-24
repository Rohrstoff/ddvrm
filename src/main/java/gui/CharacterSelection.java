package gui;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.db.Avatar;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.Drools;
import utils.Game;
import utils.Hibernate;

import java.util.ArrayList;
import java.util.List;

public class CharacterSelection {

    private Stage stage;

    public CharacterSelection(Stage stage)
    {
        this.stage = stage;
    }

    public void setStage()
    {
        GridPane root = new GridPane();
        root.setHgap(12);
        root.setVgap(3);
        root.setPadding(new Insets(5));
        //root.setGridLinesVisible(true);
        root.setAlignment( Pos.CENTER );

        //Title
        setTitle( root );

        //Avatar Panels
        int x = 1;
        int y = 2;
        for (VBox panel : getAvatars()) {
            root.add(panel, x, y);
            x += 2;
            y = y % 2 == 0 ? y + 1 : y - 1;
        }

        //Start Game Button
        setStartGameButton( root );

        stage.setScene( new Scene( root, 1440, 900 ));
    }

    private void setTitle( GridPane pane )
    {
        Text title = new Text("Assemble your group!");
        title.setFont(Font.font("Gothic MS", FontWeight.EXTRA_BOLD, 50));
        title.setTextAlignment( TextAlignment.CENTER );

        System.out.println( Font.getFontNames() );

        pane.add( title, 0, 0, 12, 1);
        GridPane.setHalignment( title, HPos.CENTER );
    }

    private List<VBox> getAvatars()
    {
        Session session = Hibernate.getSession();
        List<VBox> returnValue = new ArrayList<>();
        Query<Avatar> query = session.createQuery( "FROM Avatar", Avatar.class );

        for ( Avatar avatar : query.list() ) {
            try {
                Image image = new Image(getClass().getResourceAsStream(avatar.getImagePath()));
                ImageView imageView = new ImageView( image );

                imageView.setFitWidth(200);
                imageView.setFitHeight(400);
                imageView.setPreserveRatio(true);


                Label name = new Label();
                name.setText( avatar.getName() );

                Label raceAndClass = new Label();
                raceAndClass.setText( "Level " + avatar.getLevel() + " " + avatar.getRace().getName() + " " + avatar.getCharClass().getName() );


                VBox avatarPanel = new VBox(name, raceAndClass, imageView);
                avatarPanel.setAlignment( Pos.CENTER );

                avatarPanel.setOnMouseClicked( event -> {
                    if( !Game.isInParty( avatar ) ) {
                        Game.addToParty( avatar );
                    } else {
                        Game.removeFromParty( avatar );
                    }
                });

                Background focusBackground = new Background( new BackgroundFill( Color.YELLOWGREEN, CornerRadii.EMPTY, Insets.EMPTY ) );
                Background unfocusBackground = new Background( new BackgroundFill( Color.web("#f4f4f4", 1.0), CornerRadii.EMPTY, Insets.EMPTY ) );

                avatarPanel.backgroundProperty().bind( Bindings
                        .when( avatarPanel.hoverProperty() )
                        .then( focusBackground )
                        .otherwise( unfocusBackground )
                );

                returnValue.add( avatarPanel );
            } catch( Exception e) {
                e.printStackTrace();
            }
        }

        return returnValue;
    }

    private void setStartGameButton( GridPane pane )
    {
        Button btn = new Button();
        btn.setText( "Start Game" );
        btn.setOnAction( getActionHandler() );

        pane.add( btn, 0, 4, 12, 1 );
        GridPane.setHalignment( btn, HPos.CENTER );
    }

    private EventHandler<ActionEvent> getActionHandler()
    {
        return event -> {
            Game.init();
            new RoomScreen( stage ).setStage();
        };
    }

}
