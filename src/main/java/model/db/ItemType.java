package model.db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemType {

    private static final int ITEMTYPE_WEAPON = 1;
    private static final int ITEMTYPE_EQUIPMENT = 2;
    private static final int ITEMTYPE_CONSUMABLE = 3;

    @Id
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
