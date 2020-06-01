package model.db;

import model.Character;
import model.Monster;
import model.PlayerAction;
import model.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Avatar extends Character {

    public static final int PROFICIENCY_BONUS = 2;

    @Id
    private long id;

    private String name;
    @ManyToOne
    private Class charClass;
    @ManyToOne
    private Race race;
    private int level;
    private int hitPoints;
    private String imagePath;

    private String personalityTrait;
    private String bond;
    private String ideals;
    private String flaws;

    @ManyToMany
    @JoinTable(
            name = "avatar_spell",
            joinColumns = { @JoinColumn(name = "avatar_id") },
            inverseJoinColumns = { @JoinColumn(name = "spell_id") }
    )
    private List<Spell> spells;

    @ManyToMany
    @JoinTable(
            name = "avatar_language",
            joinColumns = { @JoinColumn(name = "avatar_id") },
            inverseJoinColumns = { @JoinColumn(name = "language_id") }
    )
    private List<Language> languages;

    @ManyToMany
    @JoinTable(
            name = "avatar_item",
            joinColumns = { @JoinColumn(name = "avatar_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private List<Item> items;

    @Transient
    private List<Spell> preparedSpells;

    @Transient
    private List<PlayerAction> playerActions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getCharClass() {
        return charClass;
    }

    public Race getRace() {
        return race;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public String getPersonalityTrait() {
        return personalityTrait;
    }

    public String getBond() {
        return bond;
    }

    public String getIdeals() {
        return ideals;
    }

    public String getFlaws() {
        return flaws;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<Spell> getPreparedSpells() {
        return preparedSpells;
    }

    public void setPreparedSpells(List<Spell> preparedSpells) {
        this.preparedSpells = preparedSpells;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<PlayerAction> getPlayerActions() {
        return playerActions;
    }

    public void addPlayerAction( PlayerAction action ) {
        playerActions.add( action );
    }

    public void clearPlayerActions()
    {
        playerActions.clear();
    }

    public void removePlayerAction( PlayerAction action )
    {
        for( PlayerAction playerAction : playerActions )
        {
            if( playerAction.getAction() == action.getAction() && playerAction.getTarget() == action.getTarget() && playerAction.getTitle().equalsIgnoreCase( action.getTitle() ) )
            {
                playerActions.remove( playerAction );
                break;
            }
        }
    }

    public boolean speaksLanguageOfAMonsterInRoom( Room room )
    {
        for( Monster monster : room.getMonsters() )
        {
            for( Language lang : languages )
            {
                if( lang.getId() == monster.getLanguage().getId() )
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getName();
    }
}
