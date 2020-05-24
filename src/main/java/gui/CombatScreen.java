package gui;

import actions.Attack;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Character;
import model.Monster;
import model.PlayerAction;
import model.Room;
import model.db.Avatar;
import model.db.Item;
import model.db.Spell;
import state.Combat;
import utils.Action;
import utils.Drools;
import utils.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatScreen {

    private Stage stage;
    private VBox combatLog = new VBox();
    private VBox rhsBox = new VBox();
    private ListView<Character> orderOfCombat = new ListView<>();

    private int attackingCharacter = 0;


    public CombatScreen(Stage stage)
    {
        this.stage = stage;
    }

    public void setStage()
    {
        GridPane root = new GridPane();
        root.setHgap(12);
        root.setVgap(3);
        root.setPadding(new Insets(5));
        root.setAlignment( Pos.CENTER );


        //Order of Combat
        Combat.sortCombatants();
        orderOfCombat.getItems().addAll(Combat.getCombatants());
        orderOfCombat.setCellFactory( lv -> new ListCell<>() {
            @Override
            public void updateItem(Character item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        orderOfCombat.getSelectionModel().select( attackingCharacter );
        Character currentCombatant = orderOfCombat.getSelectionModel().getSelectedItems().get(0);

        //VBox on RHS
        rhsBox.getChildren().add( orderOfCombat );
        GUIElements.getPlayerTable( rhsBox, stage, true );

        //combat log
        ScrollPane centralPane = new ScrollPane();
        centralPane.setBackground( new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) ) );

        Text txt = new Text();
        txt.setText( "It's " + currentCombatant + "'s turn!" );
        txt.setFont(Font.font("Gothic MS", FontWeight.SEMI_BOLD, 15));
        combatLog.getChildren().add( txt );

        centralPane.setContent( combatLog );

        root.getChildren().clear();
        root.add( centralPane, 0,0, 6, 2 );
        root.add( rhsBox, 7, 0, 6, 1 );

        if( currentCombatant instanceof Avatar )
        {
            rhsBox.getChildren().addAll( getActionsFor( currentCombatant ) );
        }
        else
        {
            executeMonsterAttack( (Monster) currentCombatant );
        }


        stage.setScene( new Scene( root, 1440, 900 ));
    }

    public List<Button> getActionsFor( Character combatant )
    {
        List<Button> returnValue = new ArrayList<>();

        if( combatant instanceof Avatar )
        {
            Avatar avatar = (Avatar) combatant;

            for(Item item : avatar.getItems())
            {
                Button btn = new Button();
                btn.setText( "Attack with " + item.getName() );
                btn.setOnAction( e -> {
                    Character target = orderOfCombat.getSelectionModel().getSelectedItems().get(0);

                    Text txt = new Text();
                    txt.setText("Attack " + target.toString() + " with " + item.getName());
                    combatLog.getChildren().add( txt );

                    Attack attack = new Attack( item, target );
                    Drools.getWorkingMemory().insert( attack );
                    Drools.getWorkingMemory().fireAllRules();

                    Text dmgTxt = new Text();
                    if( attack.isSuccessful() )
                    {
                        dmgTxt.setText( target.toString() + " takes " + attack.getDamage() + " damage!" );
                    }
                    else
                    {
                        dmgTxt.setText( avatar.getName() + " misses!" );
                    }

                    combatLog.getChildren().add( dmgTxt );

                    if( target.isDead() )
                    {
                        Text deathText = new Text();
                        deathText.setText( target + " is dead!" );
                        combatLog.getChildren().add( deathText );
                        setAttackingCharacterIndex( target );
                    }

                    finishTurn();

                });

                returnValue.add( btn );
            }
        }

        return returnValue;
    }

    private void finishTurn()
    {
        rhsBox.getChildren().clear();
        rhsBox.getChildren().add( orderOfCombat );
        GUIElements.getPlayerTable( rhsBox, stage, true );

        if( Combat.isOver() && Game.isOver() )
        {
            Text txt = new Text();
            txt.setText( "All your Characters died!\n" +
                    "Game Over!");
            txt.setFont(Font.font("Gothic MS", FontWeight.BOLD, 15));
            combatLog.getChildren().add( txt );

            Button exitButton = new Button();
            exitButton.setText( "Exit Game" );
            exitButton.setOnAction( e -> {
                System.exit(0);
            });

            rhsBox.getChildren().add( exitButton );
        }
        else if( Combat.isOver() )
        {
            Text txt = new Text();
            txt.setText( "Combat over!\n" +
                        "You gain Experience"); //TODO implement XP and check for lvl up
            txt.setFont(Font.font("Gothic MS", FontWeight.SEMI_BOLD, 15));
            combatLog.getChildren().add( txt );

            PlayerAction action = new PlayerAction( "Back to room screen", Action.BACK_TO_ROOM );
            Button backToRoomButton = new Button();
            backToRoomButton.setText( action.getTitle() );
            backToRoomButton.setOnAction( e -> {
                System.out.println( action.getAction() );
                Drools.getWorkingMemory().insert( action );
                Drools.getWorkingMemory().insert( stage );

                Drools.getWorkingMemory().fireAllRules();
            });

            rhsBox.getChildren().add( backToRoomButton );
        }
        else
        {
            setAttackingCharacterIndex();
            orderOfCombat.getSelectionModel().select( attackingCharacter );
            Character currentCombatant = orderOfCombat.getSelectionModel().getSelectedItems().get(0);

            while( Combat.hasPartyAdvantage() && currentCombatant instanceof Monster )
            {
                setAttackingCharacterIndex();
                orderOfCombat.getSelectionModel().select( attackingCharacter );
                currentCombatant = orderOfCombat.getSelectionModel().getSelectedItems().get(0);
            }

            Text txt = new Text();
            txt.setText( "It's " + currentCombatant + "'s turn!" );
            txt.setFont(Font.font("Gothic MS", FontWeight.SEMI_BOLD, 15));
            combatLog.getChildren().add( txt );

            if( currentCombatant instanceof Avatar )
            {
                rhsBox.getChildren().addAll( getActionsFor( currentCombatant ) );
            }
            else
            {
                executeMonsterAttack( (Monster) currentCombatant );
            }
        }

    }

    private void setAttackingCharacterIndex()
    {
        setAttackingCharacterIndex( null );
    }

    private void setAttackingCharacterIndex( Character target )
    {
        int targetIndex = -1;
        if( target != null )
        {
            targetIndex = orderOfCombat.getItems().indexOf( target );
            orderOfCombat.getItems().remove( target );
            Combat.getCombatants().remove( target );
        }

        if( targetIndex == -1 )
        {
            attackingCharacter++;
        }
        else if ( attackingCharacter > targetIndex )
        {
            attackingCharacter--;
        }

        System.out.println( "Attack index: " + attackingCharacter );

        if( attackingCharacter >= orderOfCombat.getItems().size() )
        {
            attackingCharacter = 0;
            Combat.setPartyAdvantage( false );
        }
        else if( attackingCharacter == -1 )
        {
            attackingCharacter = 0;
        }

    }

    private void executeMonsterAttack( Monster monster )
    {
        Attack attack = null;
        Text txt = new Text();
        Character target = selectRandomTarget();
        if( monster.getSpells().size() > 0 )
        {
            Spell spell = monster.getSpells().get(0);
            txt.setText("Attack " + target.toString() + " with " + spell.getName());
            attack = new Attack( spell, target );
        }
        else
        {
            Item item = monster.getItems().get( 0 );
            txt.setText("Attack " + target.toString() + " with " + item.getName());
            attack = new Attack( item, target );
        }

        combatLog.getChildren().add( txt );

        Drools.getWorkingMemory().insert( attack );
        Drools.getWorkingMemory().fireAllRules();

        Text dmgTxt = new Text();
        if( attack.isSuccessful() )
        {
            dmgTxt.setText( target.toString() + " takes " + attack.getDamage() + " damage!" );
        }
        else
        {
            dmgTxt.setText( monster.toString() + " misses!" );
        }

        combatLog.getChildren().add( dmgTxt );

        if( target.isDead() )
        {
            Text deathText = new Text();
            deathText.setText( target + " is unconscious!" );
            combatLog.getChildren().add( deathText );
            setAttackingCharacterIndex( target );
        }

        finishTurn();
    }

    private Character selectRandomTarget()
    {
        Random rand = new Random();
        Character character = Game.getParty().get( rand.nextInt( Game.getParty().size() ) );
        while( !orderOfCombat.getItems().contains( character ) )
        {
            character = Game.getParty().get( rand.nextInt( Game.getParty().size() ) );
        }

        return character;
    }
}
