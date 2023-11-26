package com.example.myapplication;

public class Character {
    private CharacterCore characterCore;
    private int playerFlags;
    private int health;
    private int armor;
    private int ammoCount;
    private int weapon;
    private int emote;
    private int attackTick;
    private int clientId;

    // Vnitřní třída CharacterCore
    public static class CharacterCore {
        private int tick;
        private int x;
        private int y;
        private int velX;
        private int velY;
        private int angle;
        private int direction;
        private int jumped;
        private int hookedPlayer;
        private int hookState;
        private int hookTick;
        private int hookX;
        private int hookY;
        private int hookDx;
        private int hookDy;

        // Getters a Setters

        public int getTick() {
            return tick;
        }

        public void setTick(int tick) {
            this.tick = tick;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getVelX() {
            return velX;
        }

        public void setVelX(int velX) {
            this.velX = velX;
        }

        public int getVelY() {
            return velY;
        }

        public void setVelY(int velY) {
            this.velY = velY;
        }

        public int getAngle() {
            return angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public int getJumped() {
            return jumped;
        }

        public void setJumped(int jumped) {
            this.jumped = jumped;
        }

        public int getHookedPlayer() {
            return hookedPlayer;
        }

        public void setHookedPlayer(int hookedPlayer) {
            this.hookedPlayer = hookedPlayer;
        }

        public int getHookState() {
            return hookState;
        }

        public void setHookState(int hookState) {
            this.hookState = hookState;
        }

        public int getHookTick() {
            return hookTick;
        }

        public void setHookTick(int hookTick) {
            this.hookTick = hookTick;
        }

        public int getHookX() {
            return hookX;
        }

        public void setHookX(int hookX) {
            this.hookX = hookX;
        }

        public int getHookY() {
            return hookY;
        }

        public void setHookY(int hookY) {
            this.hookY = hookY;
        }

        public int getHookDx() {
            return hookDx;
        }

        public void setHookDx(int hookDx) {
            this.hookDx = hookDx;
        }

        public int getHookDy() {
            return hookDy;
        }

        public void setHookDy(int hookDy) {
            this.hookDy = hookDy;
        }
    }

    // Getters a Setters pro Character

    public CharacterCore getCharacterCore() {
        return characterCore;
    }

    public void setCharacterCore(CharacterCore characterCore) {
        this.characterCore = characterCore;
    }

    public int getPlayerFlags() {
        return playerFlags;
    }

    public void setPlayerFlags(int playerFlags) {
        this.playerFlags = playerFlags;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getAmmoCount() {
        return ammoCount;
    }

    public void setAmmoCount(int ammoCount) {
        this.ammoCount = ammoCount;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getEmote() {
        return emote;
    }

    public void setEmote(int emote) {
        this.emote = emote;
    }

    public int getAttackTick() {
        return attackTick;
    }

    public void setAttackTick(int attackTick) {
        this.attackTick = attackTick;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
