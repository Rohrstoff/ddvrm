package model.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class Spell {

    @Id
    private long id;
    private String name;
    private int level;
    private int atkBonus;
    private int dmgNrOfDice;
    private int dmgNSidedDice;
    private int dmgAddition;
    private boolean heal;
    private boolean targetSelf;

    @ManyToMany( mappedBy = "spells")
    private List<Avatar> avatarList;
    @ManyToMany(mappedBy = "spells")
    private List<MonsterTemplate> monsterList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAtkBonus() {
        return atkBonus;
    }

    public int getDmgNrOfDice() {
        return dmgNrOfDice;
    }

    public int getDmgNSidedDice() {
        return dmgNSidedDice;
    }

    public int getDmgAddition() {
        return dmgAddition;
    }

    public boolean isHeal() {
        return heal;
    }

    public boolean isTargetSelf() {
        return targetSelf;
    }
}
