package state;

import model.Character;
import model.Monster;
import model.db.Avatar;
import utils.Action;
import utils.Drools;
import utils.Game;

import java.util.ArrayList;
import java.util.List;

public class Combat {
    private static List<Character> combatants = new ArrayList<>();
    private static boolean partyAdvantage = false;

    public static void initiate( List<Monster> monsters ){
        for( Avatar avatar : Game.getParty() )
        {
            avatar.setAction( Action.INITIATIVE );
            Drools.updateObject( avatar );
        }

        for( Monster monster : monsters )
        {
            monster.setAction( Action.INITIATIVE );
            Drools.getWorkingMemory().insert( monster );
        }

        setCombatants( Game.getParty(), monsters );
    }

    public static void setCombatants( List<? extends Character> ...lists )
    {
        combatants.clear();
        for ( List<? extends Character> combatantList : lists )
        {
            combatants.addAll( combatantList );
        }
    }

    public static void sortCombatants()
    {
        combatants.sort( (o1, o2) -> {
            if( o1.getRoll() > o2.getRoll() )
            {
                return -1;
            }
            else if( o1.getRoll() < o2.getRoll() )
            {
                return 1;
            }
            else
            {
                return 0;
            }
        });
    }

    public static List<Character> getCombatants()
    {
        return combatants;
    }

    public static boolean isOver()
    {
        for( Character character : getCombatants() )
        {
            if( character instanceof Monster  &&  getCombatants().get(0) instanceof Avatar )
            {
                return false;
            }

            if( character instanceof Avatar && getCombatants().get(0) instanceof Monster )
            {
                return false;
            }
        }

        Game.setOver( getCombatants().get(0) instanceof Monster );
        return true;
    }

    public static boolean hasPartyAdvantage() {
        return partyAdvantage;
    }

    public static void setPartyAdvantage(boolean partyAdvantage) {
        Combat.partyAdvantage = partyAdvantage;
    }
}
