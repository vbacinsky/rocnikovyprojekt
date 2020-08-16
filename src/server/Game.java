package server;

import com.sun.corba.se.impl.protocol.MinimalServantCacheLocalCRDImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


//####### Misie farby a postavy v jednej triede, neskor :D ##########

// rovnako vytvorit triedu character a jej funckionalitu ale az na konci

public class Game {
    private  ArrayList<String> playersNick;
    private ArrayList<ClientHandler> clients;
    private ArrayList<Mission> listOfMissions = createMissions();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<String> colors = createColors();
    private ArrayList<String> characters = createCharacters();
    private Map<String, Integer> castlesByID = createMap();
    private ArrayList<String> idByCastles = createArray();
    private int idWhoIsOnTurn = 0;


    private Graph graph = createGraph();
    private Map<String, Player> playersByNick = new HashMap<>();



    public Game(ArrayList<String> playersNick, ArrayList<ClientHandler> clients) {
        this.playersNick = playersNick;
        this.clients = clients;
    }


    public void createGame() {

        for (String nick : playersNick) {
            Mission mission = randomMission();
            String color = randomColor();
            int start_position = this.castlesByID.get(mission.getFrom());
            Player player = new Player(nick, true, mission, start_position, color);
            this.players.add(player);
            playersByNick.put(nick, player);
        }


        String message;

        Random random = new Random();

        String WhoisOnTurn = "." + players.get(idWhoIsOnTurn).getNick(); //zmenit na 4
        //vygenerovat id policok kde moze stupit aby si mohol client vybrat

        message = "start " + WhoisOnTurn + " " + players.get(0).getNick() + " " + players.get(0).getColor() + " " + players.get(0).getCurrentMission().getFrom() + " " + players.get(0).getCurrentMission().getTo() + " " + randomCharacter() + " " + players.get(0).getAct_position() + " " +
        players.get(1).getNick() + " " + players.get(1).getColor() + " " +  players.get(1).getCurrentMission().getFrom() + " " +  players.get(1).getCurrentMission().getTo() + " " + randomCharacter() + " " + players.get(1).getAct_position();
        //players.get(1) + " " + randomColor() + " " + listOfMissions.get(1).getFrom() + " " + listOfMissions.get(1).getTo() + " " + "rychlobeh" + " " +
        //players.get(0) + " " + randomColor() + " " + listOfMissions.get(0).getFrom() + " " + listOfMissions.get(0).getTo() + " " + "rychlobeh");


        for (ClientHandler client : clients) {
            client.outMessage(message);
        }
    }


    public void endMove() {
        if(this.idWhoIsOnTurn == 1) idWhoIsOnTurn = 0;
        else idWhoIsOnTurn++;
        String message = "NEW_PLAYER_MOVE" + " " + players.get(idWhoIsOnTurn).getNick();

        for (ClientHandler client : clients) {
            client.outMessage(message);
        }
    }



    public void changePosition(String[] clientRequestTokens) {
        String nick = clientRequestTokens[1];
        int act_policko = Integer.valueOf(clientRequestTokens[2]);
        String previousPolicko = playersByNick.get(nick).getAct_position() + "";
        playersByNick.get(nick).setAct_position(act_policko);
        String message = "CHANGE_POSITION_OPPONENT " + nick + " " + clientRequestTokens[2] + " " + playersByNick.get(nick).getColor() + " " + previousPolicko;

        for (ClientHandler client : clients) {
            client.outMessage(message);
        }


        //posli vseetkym clientom
        if(act_policko <= 14) {
            check_if_started_mission(nick, act_policko);
            check_if_finished_mission(nick, act_policko);
        }
    }

    private void check_if_started_mission(String nick, int act_policko) {
        Player player = playersByNick.get(nick);
        Mission currentm = player.getCurrentMission();
        if(act_policko == castlesByID.get(currentm.getFrom())) {
            player.setStartedMission(true);


            String message = "STARTED_MISSION " + nick;
            for (ClientHandler client : clients) {
                client.outMessage(message);
            }
        }
    }


    private void check_if_finished_mission(String nick, int act_policko) {
        Player player = playersByNick.get(nick);
        Mission currentm = player.getCurrentMission();
        if(player.getStartedMission() && act_policko == castlesByID.get(currentm.getTo())) {

            //poslat clientom parametre novej misie

            Mission mission = randomMission();
            player.setCurrentMission(mission);


            String isStarted = "false";
            if(mission.getFrom().equals(idByCastles.get(act_policko))) {
                player.setStartedMission(true);
                isStarted = "true";
            }
            else player.setStartedMission(false);

            player.setPoints(Integer.valueOf(currentm.getPoints()));
            if (checkWinner(player)) System.out.println("KONEEC");
            String message;
            message = "NEW_MISSION " + nick +" " + mission.getFrom() + " " + mission.getTo() + " " + currentm.getPoints() + " " + isStarted;

            for (ClientHandler client : clients) {
                client.outMessage(message);
            }

        }

    }


    public void startMove(String[] clientRequestTokens) {


        Random random = new Random();
        int cislo = random.nextInt(6) + 1;

        String nick = clientRequestTokens[1];
        Player player = playersByNick.get(nick);

        int act_position = player.getAct_position();
        ArrayList<Integer> listPolicok = findVerticesInDsitance(act_position, cislo);

        //funkcia na vytvorenie message z pola stringov


        StringBuilder message;

        message = new StringBuilder("VYBER_POLICKO " + nick);
        for (Integer policko : listPolicok) {
            message.append(" ").append(policko);
        }

        for (ClientHandler client : clients) {
            client.outMessage(message.toString());
        }


    }


    private ArrayList<Integer> findVerticesInDsitance(int start, int distance) {
        ArrayList<ArrayList<Integer>> vrcholy_vo_vzdialenosti = new ArrayList<>();
        ArrayList<Integer> castles = new ArrayList<>();
        int[] vzdialenosti = new int[140];

        for (int i = 0; i < 140 ; i++) {
            vzdialenosti[i] = -1;
        }

        vzdialenosti[start] = 0;
        vrcholy_vo_vzdialenosti.add(new ArrayList<>());
        vrcholy_vo_vzdialenosti.get(0).add(start);
        //vrcholy_vo_vzdialenosti = [[start]]

        System.out.println(start);
        System.out.println(distance);

        for (int aktualna_vzdialenost = 0; aktualna_vzdialenost < distance; aktualna_vzdialenost++) {
            vrcholy_vo_vzdialenosti.add(new ArrayList<>());
            for (Integer x : vrcholy_vo_vzdialenosti.get(aktualna_vzdialenost) ) {
                ArrayList<Integer> neighbors = graph.getAdjLists().get(x);
                for (Integer neigh : neighbors) {
                    if(vzdialenosti[neigh] == -1) {
                        vzdialenosti[neigh] = aktualna_vzdialenost + 1;
                        vrcholy_vo_vzdialenosti.get(aktualna_vzdialenost + 1).add(neigh);
                        if ((neigh <=14)) castles.add(neigh);
                    }
                }
            }
        }
        for (Integer cas : castles) {
            if(!vrcholy_vo_vzdialenosti.get(distance).contains(cas))
            vrcholy_vo_vzdialenosti.get(distance).add(cas);
        }
        return vrcholy_vo_vzdialenosti.get(distance);

    }

    public boolean checkWinner(Player player) {
        return (player.getPoints() >= 20);

    }



    //______________________________________________________________________________________//
    //create and random stuff


    private Map<String, Integer> createMap() {
        Map<String, Integer> map = new HashMap<>();

        map.put("Lubovniansky_hrad", 0);
        map.put("Hanigovsky_hrad", 1);
        map.put("Kamenicky_hrad", 2);
        map.put("Kapusany", 3);
        map.put("Kosice", 4);
        map.put("Trencin", 5);
        map.put("Trnava", 6);
        map.put("Sasov", 7);
        map.put("Strecno", 8);
        map.put("Bardejov", 9);
        map.put("Filakovo", 10);
        map.put("Bratislava", 11);
        map.put("Blatnica", 12);
        map.put("Spissky_hrad", 14);
        map.put("Saris", 13);

        return map;
    }




    private ArrayList<Mission> createMissions() {
        ArrayList<Mission> missions = new ArrayList<>();

        missions.add(new Mission("Blatnica", "Kosice",  "4"));
        missions.add(new Mission("Sasov", "Trnava", "4"));
        missions.add(new Mission("Strecno", "Saris",  "4"));
        missions.add(new Mission("Filakovo", "Strecno", "2"));
        missions.add(new Mission("Saris", "Bratislava", "3"));
        missions.add(new Mission("Hanigovsky_hrad", "Kosice", "1"));
        missions.add(new Mission("Lubovniansky_hrad", "Blatnica", "6"));
        missions.add(new Mission("Trnava", "Bardejov", "4"));
        missions.add(new Mission("Kosice", "Trnava", "1"));
        missions.add(new Mission("Blatnica", "Spissky_hrad", "3"));
        missions.add(new Mission("Spissky_hrad", "Bratislava", "1"));
        missions.add(new Mission("Hanigovsky_hrad", "Filakovo", "5"));
        missions.add(new Mission("Bratislava", "Sasov", "3"));
        missions.add(new Mission("Sasov", "Spissky_hrad", "4"));
        missions.add(new Mission("Kosice", "Filakovo", "4"));
        missions.add(new Mission("Trnava", "Filakovo", "5"));
        missions.add(new Mission("Trnava", "Lubovniansky_hrad", "2"));
        missions.add(new Mission("Spissky_hrad", "Trnava", "4"));
        missions.add(new Mission("Kamenicky_hrad", "Blatnica", "5"));
        missions.add(new Mission("Trencin", "Filakovo", "5"));
        missions.add(new Mission("Kamenicky_hrad", "Sasov", "5"));
        missions.add(new Mission("Kapusany", "Bardejov", "4"));
        missions.add(new Mission("Strecno", "Trencin", "4"));
        missions.add(new Mission("Trnava", "Bratislava", "2"));
        missions.add(new Mission("Kamenicky_hrad", "Spissky_hrad", "6"));
        missions.add(new Mission("Trnava", "Kamenicky_hrad","1"));
        missions.add(new Mission("Trnava", "Kapusany", "2"));
        missions.add(new Mission("Sasov", "Hanigovsky_hrad", "4"));






        return missions;
    }

    private ArrayList<String> createColors() {
        ArrayList<String> colors = new ArrayList<>();

        colors.add("BLUE");
        colors.add("RED");
        colors.add("GREEN");
        colors.add("YELLOW");

        return colors;

    }

    private ArrayList<String> createArray() {
        ArrayList<String> castles = new ArrayList<>();

        castles.add("Lubovniansky_hrad");
        castles.add("Hanigovsky_hrad");
        castles.add("Kamenicky_hrad");
        castles.add("Kapusany");
        castles.add("Kosice");
        castles.add("Trencin");
        castles.add("Trnava");
        castles.add("Sasov");
        castles.add("Strecno");
        castles.add("Bardejov");
        castles.add("Filakovo");
        castles.add("Bratislava");
        castles.add("Blatnica");
        castles.add("Saris");
        castles.add("Spissky_hrad");

        return castles;
    }

    private ArrayList<String> createCharacters() {
        ArrayList<String> characters = new ArrayList<>();

        characters.add("rychlobeh");
        characters.add("shrek");
        characters.add("rozbijac_ciest");
        characters.add("marco_polo");
        characters.add("zbojnik");

        return characters;
    }


    private Graph createGraph() {
        Graph graph = new Graph(121);

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("resources\\mapa.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        int id_ciest = 15;
        int id_hradov = 0;
        int[][] id_pole = new int[row][col];
        int[][] pole = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int tmp = scanner.nextInt();
                if(tmp == 3) {
                    id_pole[i][j] = id_ciest;
                    id_ciest++;
                } else if(tmp == 4 || tmp == 5) {
                    id_pole[i][j] = id_hradov;
                    id_hradov++;
                } else {
                    id_pole[i][j] = tmp;
                }
                pole[i][j] = tmp;
            }
        }


        for (int i = 1; i < row-1; i++) {
            for (int j = 1; j <col-1 ; j++) {
                int id_act = id_pole[i][j];
                int act = pole[i][j];
                if(act == 3 || act == 4){
                    tryAddEdge(id_act, id_pole[i - 1][j], pole[i - 1][j], graph, false);
                    tryAddEdge(id_act, id_pole[i][j + 1], pole[i][j + 1], graph, false);
                    tryAddEdge(id_act, id_pole[i][j - 1], pole[i][j - 1], graph, false);
                    tryAddEdge(id_act, id_pole[i + 1][j], pole[i + 1][j], graph, false);
                } else if(act == 5) {
                    tryAddEdge(id_act, id_pole[i-2][j-1], pole[i-2][j-1], graph, true);
                    tryAddEdge(id_act, id_pole[i-2][j], pole[i-2][j], graph, true);
                    tryAddEdge(id_act, id_pole[i-2][j+1], pole[i-2][j+1], graph, true);
                    tryAddEdge(id_act, id_pole[i-1][j+2], pole[i-1][j+2], graph, true);
                    tryAddEdge(id_act, id_pole[i][j+2], pole[i][j+2], graph, true);
                    tryAddEdge(id_act, id_pole[i+1][j+1], pole[i+1][j+1], graph, true);
                    tryAddEdge(id_act, id_pole[i+1][j], pole[i+1][j], graph, true);
                    tryAddEdge(id_act, id_pole[i+1][j-1], pole[i+1][j-1], graph, true);
                    tryAddEdge(id_act, id_pole[i][j-2], pole[i][j-2], graph, true);
                    tryAddEdge(id_act, id_pole[i-1][j-2], pole[i-1][j-2], graph, true);
                }
            }
        }
        graph.addEdge(62, 51);
        graph.addEdge(51, 62);
        System.out.println(graph.getAdjLists());
        return graph;
    }


    private void tryAddEdge(int from, int to, int original, Graph graph, boolean isCastle) {
        if (original == 3 || original == 4 || original == 5) {
            graph.addEdge(from, to);
            if(isCastle) {
                graph.addEdge(to, from);
            }

        }
    }


    private String randomColor() {
        Random r = new Random();
        String string = colors.get(r.nextInt(colors.size()));
        colors.remove(string);

        return string;
    }

    private Mission randomMission() {
        Random r = new Random();
        Mission mission = listOfMissions.get(r.nextInt(listOfMissions.size()));
        listOfMissions.remove(mission);

        return mission;
    }

    private String randomCharacter() {
        Random r = new Random();
        String string = characters.get(r.nextInt(characters.size()));
        characters.remove(string);

        return string;
    }




}
