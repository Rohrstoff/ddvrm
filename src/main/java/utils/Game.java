package utils;

import model.Character;
import model.Monster;
import model.PlayerAction;
import model.Room;
import model.db.Avatar;
import model.db.MonsterTemplate;
import org.hibernate.query.Query;
import org.kie.api.runtime.rule.FactHandle;
import state.Init;
import state.Play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private static final int TOTAL_ROOMS = 2;
    private static int currentRoomNo = 0;

    private static List<Avatar> party = new ArrayList<>();
    private static Map<Integer, Room> rooms = new HashMap<>();

    private static boolean over = false;

    public static List<Avatar> getParty() {
        return party;
    }

    public static void addToParty(Avatar avatar) {
        party.add( avatar );
    }

    public static void removeFromParty(Avatar avatar) {
        party.remove( avatar );
    }

    public static boolean isInParty( Avatar avatar )
    {
        return party.contains( avatar );
    }

    public static Room getCurrentRoom()
    {
        return rooms.get( currentRoomNo );
    }

    public static void init()
    {
        Drools.getWorkingMemory().insert( new Init() );

        for( Avatar avatar : Game.getParty() )
        {
            Drools.getWorkingMemory().insert( avatar );
        }

        for( int i = 0; i <= TOTAL_ROOMS; i++ )
        {
            Room room = new Room( i );
            rooms.put( i, room );
            Drools.getWorkingMemory().insert( room );
        }

        Query<MonsterTemplate> query = Hibernate.getSession().createQuery( "FROM MonsterTemplate ", MonsterTemplate.class );

        for( MonsterTemplate monster : query.list() )
        {
            Drools.getWorkingMemory().insert( monster );
        }

        Drools.getWorkingMemory().fireAllRules();
    }

    public static boolean rollSuccessful( int roll, int checkAgainst )
    {
        return roll > checkAgainst;
    }

    public static void setCurrentRoomNo(int currentRoomNo) {
        Game.currentRoomNo = currentRoomNo;
    }

    public static boolean isOver() {
        return over;
    }

    public static void setOver(boolean over) {
        Game.over = over;
    }

    public static void removePlayerActionFromPartyMembers( PlayerAction action )
    {
        for( Avatar avatar : getParty() )
        {
            avatar.removePlayerAction( action );
        }
    }

    public static void clearPlayerActions()
    {
        for( Avatar avatar : getParty() )
        {
            avatar.clearPlayerActions();
        }
    }
}
