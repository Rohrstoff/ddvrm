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
    private Character attacker;

    private boolean successful;
    private int damage;
    private boolean heal;

    public Attack( Item item, Character target, Character attacker )
    {
        this.item = item;
        this.target = target;
        this.attacker = attacker;
    }

    public Attack( Spell spell, Character target, Character attacker )
    {
        this.spell = spell;
        this.target = target;
        this.attacker = attacker;
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

    public Character getAttacker() {
        return attacker;
    }

    public void heal( int amount )
    {
        this.heal = true;
        this.damage = amount;
        if( spell.isTargetSelf() )
        {
            target = attacker;
            target.setCurrentHitPoints( target.getCurrentHitPoints() + amount );
        }
        else
        {
            target.setCurrentHitPoints( target.getCurrentHitPoints() + amount );
        }

        if( target instanceof Avatar && target.getCurrentHitPoints() > ((Avatar) target).getHitPoints() )
        {
            target.setCurrentHitPoints( ((Avatar) target).getHitPoints() );
        }
        else if( target instanceof Monster && target.getCurrentHitPoints() > ((Monster) target).getHitPoints() )
        {
            target.setCurrentHitPoints( ((Monster) target).getHitPoints() );
        }
    }

    public boolean isHeal() {
        return heal;
    }
}
