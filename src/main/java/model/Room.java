package model;


import model.db.Avatar;
import model.db.MonsterTemplate;
import state.PartyAction;
import utils.Action;
import utils.Drools;
import utils.Game;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int no = 0;
    private String description;
    private List<Monster> monsters = new ArrayList<>();
    private List<Integer> nextRooms = new ArrayList<>();
    private boolean cleared = false;
    private Action actionUponEntering;
    private boolean actionSuccessful = true;
    private int checkAgainstForEnterAction;
    private boolean partyEntering;

    private List<PlayerAction> playerActionsList = new ArrayList<>();
    private List<PlayerAction> partyActionsList = new ArrayList<>();

    public Room( int no )
    {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void addMonster(MonsterTemplate monsterTemplate )
    {
        Monster monster = new Monster( monsterTemplate );
        this.monsters.add( monster );
    }

    public void addMonster( MonsterTemplate monsterTemplate, int amount )
    {
        for( int i = 1; i <= amount; i++ )
        {
            addMonster( monsterTemplate );
        }
    }

    public boolean hasMonsters()
    {
        return monsters.size() > 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getNextRooms()
    {
        return nextRooms;
    }

    public void addNextRoom( int roomNo )
    {
        nextRooms.add( roomNo );
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public Action getActionUponEntering() {
        return actionUponEntering;
    }

    public void setActionUponEntering(Action actionUponEntering) {
        this.actionUponEntering = actionUponEntering;
    }

    public boolean hasActionUponEntering()
    {
        return this.actionUponEntering != null;
    }

    public void setCheckAgainstForEnterAction(int checkAgainstForEnterAction) {
        this.checkAgainstForEnterAction = checkAgainstForEnterAction;
    }

    public void partyEntering()
    {
        System.out.println( getActionUponEntering() );
        for( Avatar avatar : Game.getParty() )
        {
            PlayerAction action = new PlayerAction( null, getActionUponEntering() );
            action.setAvatar( avatar );

            action.setCheckAgainst( checkAgainstForEnterAction );
            Drools.getWorkingMemory().insert( action );
        }
        Drools.getWorkingMemory().insert( this );
    }

    public void addPlayerAction( PlayerAction action )
    {
        playerActionsList.add( action );
    }

    public void addPartyAction( PlayerAction action )
    {
        partyActionsList.add( action );
    }

    public List<PlayerAction> getPlayerActions()
    {
        return playerActionsList;
    }

    public void removePlayerAction( PlayerAction action )
    {
        playerActionsList.remove( action );
    }

    public void removePartyAction( PlayerAction action )
    {
        partyActionsList.remove( action );
    }

    public List<PlayerAction> getPartyActions() {
        return partyActionsList;
    }

    //needed in drools
    public boolean isActionSuccessful()
    {
        return actionSuccessful;
    }

    public void addToDescription( String addition )
    {
        this.description += addition;
    }

    public void clearActions()
    {
        this.playerActionsList.clear();
        this.partyActionsList.clear();
    }

    public boolean isPartyEntering() {
        return partyEntering;
    }

    public void setPartyEntering(boolean partyEntering) {
        this.partyEntering = partyEntering;
    }
}
