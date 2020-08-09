package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(22223);
        int id = 1;

        while(true) {
            if(id > 4) break;
            Socket client = ss.accept();
            System.out.println(id  + ". client connected");
            ClientHandler clientThread = new ClientHandler(client, id, clients);
            clients.add(clientThread);
            pool.execute(clientThread);
            id++;
        }

        for (ClientHandler client : clients) {
            client.ready();
        }

        for (ClientHandler client : clients) {
            //client.initgame();
        }


        ss.close();


    }
}
