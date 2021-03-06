package model.db;

import model.Character;

import javax.persistence.*;
import java.util.List;

@Entity
public class MonsterTemplate extends Character {

    @Id
    private long id;

    @ManyToOne
    private Language language;

    @ManyToMany
    @JoinTable(
            name = "monster_spell",
            joinColumns = { @JoinColumn(name = "monster_id") },
            inverseJoinColumns = { @JoinColumn(name = "spell_id") }
    )
    private List<Spell> spells;

    @ManyToMany
    @JoinTable(
            name = "monster_item",
            joinColumns = { @JoinColumn(name = "monster_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private List<Item> items;
    private String race;
    private String name;
    private int hitPointNrOfDice;
    private int hitPointNSidedDice;
    private int hitPointAddition;
    private int experience;

    private String imagePath;

    public Language getLanguage() {
        return language;
    }

    public String getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitPointNrOfDice() {
        return hitPointNrOfDice;
    }

    public int getHitPointNSidedDice() {
        return hitPointNSidedDice;
    }

    public int getHitPointAddition() {
        return hitPointAddition;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<Item> getItems() {
        return items;
    }
}
