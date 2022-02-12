package us.ajg0702.vactop;

import me.frep.vulcan.api.data.IPlayerData;

public enum ViolationType {
    TOTAL, COMBAT, MOVEMENT, PLAYER, AUTOCLICKER, TIMER, SCAFFHOLD;

    public int get(IPlayerData data) {
        switch(this) {
            case TOTAL:
                return data.getTotalViolations();
            case COMBAT:
                return data.getCombatViolations();
            case MOVEMENT:
                return data.getMovementViolations();
            case PLAYER:
                return data.getPlayerViolations();
            case AUTOCLICKER:
                return data.getAutoClickerViolations();
            case TIMER:
                return data.getTimerViolations();
            case SCAFFHOLD:
                return data.getScaffoldViolations();
        }
        throw new IllegalStateException("Somehow this is nothing?");
    }
}
