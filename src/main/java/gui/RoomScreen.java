package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.PlayerAction;
import model.Room;
import model.db.Avatar;
import state.PartyAction;
import utils.Drools;
import utils.Game;

import java.util.ArrayList;
import java.util.List;

public class RoomScreen {

    private Stage stage;

    GridPane pane = new GridPane();

    public RoomScreen(Stage stage)
    {
        this.stage = stage;
    }

    public void setStage()
    {
        pane.setHgap(12);
        pane.setVgap(3);
        pane.setPadding(new Insets(5));
        pane.setAlignment( Pos.CENTER );

        setRoom();

        stage.setScene( new Scene(pane, 1440, 900 ));
    }

    public void setRoom() {
        Room room = Game.getCurrentRoom();
        room.setPartyEntering( false );

        //VBox on RHS
        VBox vBox = new VBox();
        GUIElements.getPlayerTable( vBox, stage, false );

        //Description
        Pane centralPane = new Pane();
        Text description = new Text();
        description.setText( room.getDescription() );
        description.setTextAlignment( TextAlignment.CENTER );
        description.setFont(Font.font("Gothic MS", FontWeight.BOLD, 15));

        centralPane.getChildren().add( description );

        pane.getChildren().clear();
        pane.add( centralPane, 0,0, 6, 2 );
        GridPane.setHalignment( description, HPos.CENTER );
        pane.add( vBox, 7, 0, 6, 1 );

        //Actions
        Text txtPartyActions = new Text();
        txtPartyActions.setText( "Party Actions" );
        txtPartyActions.setTextAlignment( TextAlignment.CENTER );
        txtPartyActions.setFont(Font.font("Gothic MS", FontWeight.BOLD, 14));

        vBox.getChildren().add( txtPartyActions );
        vBox.getChildren().addAll( getPartyActions() );
    }

    private List<Button> getPartyActions()
    {
        List<Button> returnValue = new ArrayList<>();

        for(PlayerAction action : Game.getCurrentRoom().getPartyActions())
        {
            Button btn = new Button();
            btn.setText( action.getTitle() );
            btn.setOnAction( event -> {
                if( !action.isSinglePartyMemberCheck() )
                {
                    Drools.getWorkingMemory().insert( action );
                }
                else
                {
                    for( Avatar avatar : Game.getParty() )
                    {
                        PlayerAction newAction = new PlayerAction( null, action.getAction(), action.getCheckAgainst(), false );
                        newAction.setAvatar( avatar );
                        Drools.getWorkingMemory().insert( newAction );
                    }
                    Drools.getWorkingMemory().insert( new PartyAction() );
                }


                Drools.getWorkingMemory().insert( stage );
                Drools.getWorkingMemory().insert( Game.getCurrentRoom() );
                Drools.getWorkingMemory().fireAllRules();
            });

            returnValue.add( btn );
        }

        return returnValue;
    }
}
