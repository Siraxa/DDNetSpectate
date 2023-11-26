package com.example.myapplication;

public class Snapshot {
    private ClientInfo clientInfo;
    private PlayerInfo playerInfo;
    private Character character;

    public Snapshot(ClientInfo clientInfo, PlayerInfo playerInfo, Character character) {
        this.clientInfo = clientInfo;
        this.playerInfo = playerInfo;
        this.character = character;
    }





    // Getters a Setters
    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

}
