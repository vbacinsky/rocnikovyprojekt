package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static int playersConnected = 0;
    private Game game;


    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(22222);
        int id = 1;

        while(true) {
            if(id > 2) break;
            Socket client = ss.accept();
            System.out.println(id  + ". client connected");
            ClientHandler clientThread = new ClientHandler(client, id, clients, new Server());
            clients.add(clientThread);
            pool.execute(clientThread);
            id++;
        }

        for (ClientHandler client : clients) {
            client.ready();
        }

    }

    public void handleChangePositionRequest(String[] clientRequestTokens) {
        this.game.changePosition(clientRequestTokens);
    }

    public void handleStartMoveRequest(String[] clientRequestTokens) {
        System.out.println("start 2");
        this.game.startMove(clientRequestTokens);
        System.out.println("koniec 2");
    }



    public void handleNickRequest(String[] clientRequestTokens) {
        players.add(clientRequestTokens[1]);
        playersConnected++;
        if(playersConnected == 2) {
            System.out.println("Start 1");
            this.game = new Game(players, clients);
            game.createGame();
            System.out.println("Koniec 1");
        }
    }

    public void handleEndMove() {
        this.game.endMove();
    }


    public static ArrayList<String> getPlayers() {
        return players;
    }



}
