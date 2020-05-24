package model.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {

    @Id
    private long id;
    @ManyToOne
    private ItemType itemType;
    private String name;
    private int atkBonus;
    private int dmgNrOfDice;
    private int dmgNSidedDice;
    private int dmgAddition;

    public ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
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
}
