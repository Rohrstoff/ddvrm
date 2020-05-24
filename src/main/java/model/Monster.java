package model;

import model.db.Item;
import model.db.Language;
import model.db.MonsterTemplate;
import model.db.Spell;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Monster extends Character {

    private String race;
    private int hitPoints;
    private List<Spell> spells;
    private List<Item> items;
    private Language language;

    public Monster(MonsterTemplate template)
    {
        this.race = template.getRace();
        setArmorClass( template.getArmorClass() );
        calculateHitPoints( template.getHitPointNrOfDice(), template.getHitPointNSidedDice(), template.getHitPointAddition() );
        this.spells = template.getSpells();
        this.items = template.getItems();
        this.language = template.getLanguage();
    }

    private void calculateHitPoints( int nrOfDice, int nSidedDice, int addition )
    {
        hitPoints = (nrOfDice * ThreadLocalRandom.current().nextInt( 1, nSidedDice )) + addition;
        setCurrentHitPoints( hitPoints );
    }

    public String getRace() {
        return race;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<Item> getItems() {
        return items;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return getRace();
    }
}
