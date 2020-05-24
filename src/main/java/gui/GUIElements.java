package gui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.PlayerAction;
import model.db.Avatar;
import utils.Drools;
import utils.Game;

public class GUIElements {

    public static void getPlayerTable(VBox vBox, Stage stage, boolean combatScreen)
    {
        TableView<Avatar> avatarTable = new TableView<>();
        avatarTable.setEditable( false );

        TableColumn<Avatar, String> nameColumn = new TableColumn<>( "Name" );
        nameColumn.setCellValueFactory( cellData -> new ReadOnlyStringWrapper( cellData.getValue().getName() ));
        nameColumn.setResizable( false );

        TableColumn<Avatar, Number> currentHitPointsColumn = new TableColumn<>( "Current" );
        currentHitPointsColumn.setCellValueFactory( cellData -> new ReadOnlyIntegerWrapper( cellData.getValue().getCurrentHitPoints() ));
        currentHitPointsColumn.setResizable( false );

        TableColumn<Avatar, Number> maxHitPointsColumn = new TableColumn<>( "Max" );
        maxHitPointsColumn.setCellValueFactory( cellData -> new ReadOnlyIntegerWrapper( cellData.getValue().getHitPoints() ));
        maxHitPointsColumn.setResizable( false );

        TableColumn hitPointsColumn = new TableColumn( "Hit Points" );
        hitPointsColumn.getColumns().addAll( currentHitPointsColumn, maxHitPointsColumn );
        hitPointsColumn.setResizable( false );

        avatarTable.getColumns().addAll( nameColumn, hitPointsColumn );
        avatarTable.getItems().addAll( Game.getParty() );

        avatarTable.setFixedCellSize(25);
        avatarTable.prefHeightProperty().bind(avatarTable.fixedCellSizeProperty().multiply(Bindings.size(avatarTable.getItems()).add(2)));
        avatarTable.minHeightProperty().bind(avatarTable.prefHeightProperty());
        avatarTable.maxHeightProperty().bind(avatarTable.prefHeightProperty());

        if( !combatScreen )
        {
            avatarTable.getSelectionModel().select( 0 );
            VBox vBox2 = new VBox();
            getPlayerActions( avatarTable.getSelectionModel().getSelectedItem(), vBox2, stage );
            avatarTable.setRowFactory( tv -> {
                TableRow<Avatar> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if ( !row.isEmpty() ) {
                        getPlayerActions( row.getItem(), vBox2, stage );
                    }
                });
                return row ;
            });
            vBox.getChildren().add( vBox2 );
        }
        else
        {
            avatarTable.setSelectionModel(null);
        }

        vBox.getChildren().add( avatarTable );
    }

    private static void getPlayerActions( Avatar avatar, VBox vBox2, Stage stage )
    {
        vBox2.getChildren().clear();

        if( avatar.getPlayerActions().size() > 0 )
        {
            Text txtPlayerActions = new Text();
            txtPlayerActions.setText( "Player Actions" );
            txtPlayerActions.setTextAlignment( TextAlignment.CENTER );
            txtPlayerActions.setFont(Font.font("Gothic MS", FontWeight.BOLD, 14));

            vBox2.getChildren().add( txtPlayerActions );

            for(PlayerAction action : avatar.getPlayerActions())
            {
                Button btn = new Button();
                btn.setText( action.getTitle() );
                btn.setOnAction( e -> {
                    action.setAvatar( avatar );
                    Drools.getWorkingMemory().insert( action );
                    Drools.getWorkingMemory().insert( stage );
                    Drools.getWorkingMemory().insert( Game.getCurrentRoom() );
                    Drools.getWorkingMemory().fireAllRules();
                });

                vBox2.getChildren().add( btn );
            }
        }
    }
}
