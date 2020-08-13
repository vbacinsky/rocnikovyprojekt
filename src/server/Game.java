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


    Graph graph = createGraph();



    public Game(ArrayList<String> playersNick, ArrayList<ClientHandler> clients) {
        this.playersNick = playersNick;
        this.clients = clients;
    }


    public void createGame() {

        //potom nejak randomne vygenerovat tie misie, farbu, postavy




        for (int i = 0; i < playersNick.size(); i++) {
            this.players.add(new Player(playersNick.get(i), true, randomMission()));
        }

        //vytvorit mapu



        String message;


        Random random = new Random();

        System.out.println(players);
        String WhoisOnTurn = "." + players.get(random.nextInt(1)).getNick(); //zmenit na 4

        //vygenerovat id policok kde moze stupit aby si mohol client vybrat

        message = "start " + WhoisOnTurn + " " + players.get(0).getNick() + " " + randomColor() + " " + players.get(0).getCurrentMission().getFrom() + " " + players.get(0).getCurrentMission().getTo() + " " + randomCharacter();
        //players.get(2) + " " + randomColor() + " " + listOfMissions.get(2).getFrom() + " " + listOfMissions.get(2).getTo() + " " + "shrek" + " " +
        //players.get(1) + " " + randomColor() + " " + listOfMissions.get(1).getFrom() + " " + listOfMissions.get(1).getTo() + " " + "rychlobeh" + " " +
        //players.get(0) + " " + randomColor() + " " + listOfMissions.get(0).getFrom() + " " + listOfMissions.get(0).getTo() + " " + "rychlobeh");


        for (ClientHandler client : clients) {
            client.outMessage(message);
        }
    }


    public boolean checkWinner(Player player) {
        return (player.getPoints() >= 20);

    }





    //______________________________________________________________________________________//
    //create and random stuff


    private Map<String, Integer> createMap() {
        Map<String, Integer> map = new HashMap<>();

        map.put("Hanigovsky_hrad", 0);
        map.put("Lubovniansky_hrad", 1);
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
        map.put("Saris", 12);
        map.put("Spissky_hrad", 13);
        map.put("Blatnica", 14);

        return map;
    }




    private ArrayList<Mission> createMissions() {
        ArrayList<Mission> missions = new ArrayList<>();

        missions.add(new Mission("Blatnica", "Kosice", "4", "4"));
        missions.add(new Mission("Sasov", "Trnava", "4", "4"));
        missions.add(new Mission("Cabrad", "Spissky_hrad", "5", "5"));
        missions.add(new Mission("Filakovo", "Strecno", "2", "2"));

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
                    tryAddEdge(id_act, id_pole[i - 1][j], pole[i - 1][j], graph);
                    tryAddEdge(id_act, id_pole[i][j + 1], pole[i][j + 1], graph);
                    tryAddEdge(id_act, id_pole[i][j - 1], pole[i][j - 1], graph);
                    tryAddEdge(id_act, id_pole[i + 1][j], pole[i + 1][j], graph);
                } else if(act == 5) {
                    tryAddEdge(id_act, id_pole[i-2][j-1], pole[i-2][j-1], graph);
                    tryAddEdge(id_act, id_pole[i-2][j], pole[i-2][j], graph);
                    tryAddEdge(id_act, id_pole[i-2][j+1], pole[i-2][j+1], graph);
                    tryAddEdge(id_act, id_pole[i-1][j+2], pole[i-1][j+2], graph);
                    tryAddEdge(id_act, id_pole[i][j+2], pole[i][j+2], graph);
                    tryAddEdge(id_act, id_pole[i+1][j+1], pole[i+1][j+1], graph);
                    tryAddEdge(id_act, id_pole[i+1][j], pole[i+1][j], graph);
                    tryAddEdge(id_act, id_pole[i+1][j-1], pole[i+1][j-1], graph);
                    tryAddEdge(id_act, id_pole[i][j-2], pole[i][j-2], graph);
                    tryAddEdge(id_act, id_pole[i-1][j-2], pole[i-1][j-2], graph);
                }
            }
        }
        System.out.println(graph.getAdjLists());
        return graph;
    }


    private void tryAddEdge(int from, int to, int original, Graph graph) {
        if (original == 3 || original == 4 || original == 5) {
            graph.addEdge(from, to);
            if(from == 5) graph.addEdge(to, from);
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
