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


    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(22223);
        int id = 1;

        while(true) {
            if(id > 1) break;
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

    public String handleNickRequest(String[] clientRequestTokens) {
        players.add(clientRequestTokens[1]);
        playersConnected++;
        if(playersConnected == 1) {
            Game game = new Game(players, clients);
            game.createGame();
        }
        return "OK";
    }


    public static ArrayList<String> getPlayers() {
        return players;
    }



}
