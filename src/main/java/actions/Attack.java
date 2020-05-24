package actions;

import model.Character;
import model.Monster;
import model.db.Avatar;
import model.db.Item;
import model.db.Spell;
import utils.Drools;

public class Attack {
    private Item item;
    private Character target;
    private Spell spell;

    private boolean successful;
    private int damage;

    public Attack( Item item, Character target )
    {
        this.item = item;
        this.target = target;
    }

    public Attack( Spell spell, Character target )
    {
        this.spell = spell;
        this.target = target;
    }

    public Item getItem() {
        return item;
    }

    public Spell getSpell() {
        return spell;
    }

    public Character getTarget() {
        return target;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public int getDamage() {
        return damage;
    }

    public void checkSuccess( int acRoll )
    {
        successful = acRoll >= target.getArmorClass();
        System.out.println( "Success on " + target + ": " + acRoll + " / " + target.getArmorClass() );
    }

    public void doDamage( int dmg )
    {
        this.damage = dmg;
        target.setCurrentHitPoints( target.getCurrentHitPoints() - dmg );
        Drools.updateObject( target );
    }
}
