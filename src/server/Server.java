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


    public void run() throws IOException {
        ServerSocket ss = new ServerSocket(22222);
        int id = 1;

        while(true) {
            if(id > 2) break;
            Socket client = ss.accept();
            System.out.println(id  + ". client connected");
            ClientHandler clientThread = new ClientHandler(client, id, clients, this);
            clients.add(clientThread);
            pool.execute(clientThread);
            id++;
        }
        pool.shutdown();
    }

    public void handleChangePositionRequest(String[] clientRequestTokens) {
        this.game.changePosition(clientRequestTokens);
    }

    public void handleStartMoveRequest(String[] clientRequestTokens) {
        this.game.startMove(clientRequestTokens);
    }



    public void handleNickRequest(String[] clientRequestTokens) {
        players.add(clientRequestTokens[1]);
        playersConnected++;
        if(playersConnected == 2) {
            this.game = new Game(players, clients);
            game.createGame();
        }
    }

    public void handleEndMove() {
        this.game.endMove();
    }

    public void handleTerminate() {
        this.game.terminate();
    }

    public void handlePutNewChip(String clientRequestTokens) {
        this.game.putNewChip(clientRequestTokens);
    }

    public void handleSomeoneEnteredOnCip(String[] clientRequestTokens) {
        this.game.someoneEnteredOnChip(clientRequestTokens);
    }

    public static ArrayList<String> getPlayers() {
        return players;
    }



}
