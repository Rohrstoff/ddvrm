package model;

import model.db.Avatar;
import utils.Action;

public class PlayerAction {
    private String title;
    private Action action;
    private Avatar avatar;
    private Avatar target;
    private int nextRoom;
    private int checkAgainst;
    private boolean singlePartyMemberCheck;
    private boolean combatAdvantage = false;

    public PlayerAction(String title, Action action)
    {
        this.title = title;
        this.action = action;
    }

    public PlayerAction(String title, Action action, boolean combatAdvantage)
    {
        this.title = title;
        this.action = action;
        this.combatAdvantage = combatAdvantage;
    }

    public PlayerAction( String title, Action action, Avatar avatar )
    {
        this.title = title;
        this.action = action;
        this.target = avatar;
    }

    public PlayerAction(String title, Action action, int nextRoom)
    {
        this.title = title;
        this.action = action;
        this.nextRoom = nextRoom;
    }

    public PlayerAction( String title, Action action, int checkAgainst, boolean singlePartyMemberCheck )
    {
        this.title = title;
        this.action = action;
        this.checkAgainst = checkAgainst;
        this.singlePartyMemberCheck = singlePartyMemberCheck;
    }

    public String getTitle() {
        return title;
    }

    public Action getAction() {
        return action;
    }

    public int getNextRoom() {
        return nextRoom;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getCheckAgainst() {
        return checkAgainst;
    }

    public void setCheckAgainst(int checkAgainst) {
        this.checkAgainst = checkAgainst;
    }

    public boolean isSinglePartyMemberCheck() {
        return singlePartyMemberCheck;
    }

    public boolean hasCombatAdvantage() {
        return combatAdvantage;
    }

    public Avatar getTarget() {
        return target;
    }
}
