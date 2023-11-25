package com.example.myapplication;

import java.util.List;

public class Server {
    private List<String> addresses;
    private String location;

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ServerInfo getInfo() {
        return info;
    }

    public void setInfo(ServerInfo info) {
        this.info = info;
    }

    private ServerInfo info;

    // Gettery a settery pro všechny výše uvedené proměnné

    public static class ServerInfo {
        private int max_clients;
        private int max_players;
        private boolean passworded;
        private String game_type;
        private String name;
        private Map map;
        private String version;
        private List<Client> clients;

        public int getMax_clients() {
            return max_clients;
        }

        public void setMax_clients(int max_clients) {
            this.max_clients = max_clients;
        }

        public int getMax_players() {
            return max_players;
        }

        public void setMax_players(int max_players) {
            this.max_players = max_players;
        }

        public boolean isPassworded() {
            return passworded;
        }

        public void setPassworded(boolean passworded) {
            this.passworded = passworded;
        }

        public String getGame_type() {
            return game_type;
        }

        public void setGame_type(String game_type) {
            this.game_type = game_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public List<Client> getClients() {
            return clients;
        }

        public void setClients(List<Client> clients) {
            this.clients = clients;
        }

        // Gettery a settery pro všechny výše uvedené proměnné
    }

    public static class Map {
        private String name;
        private String sha256;
        private int size;

        // Gettery a settery pro všechny výše uvedené proměnné
    }

    public static class Skin {
        private String name;
        private Integer color_body; // Může být null, proto Integer místo int
        private Integer color_feet; // Může být null, proto Integer místo int

        // Gettery a settery
    }
    public static class Client {
        private String name;
        private String clan;
        private int country;
        private int score;
        private boolean is_player;
        private Skin skin;
        private boolean afk;
        private int team;

        // Gettery a settery
    }
}

