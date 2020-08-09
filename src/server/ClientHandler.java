package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket client;
    private int id;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, int id, ArrayList<ClientHandler> clients) throws IOException {
        this.clients = clients;
        this.id = id;
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }


    public void ready() {
        out.println("We are ready to play");
    }

    /*
    public void initgame() {
        out.println("init game");
    }
    */

    @Override
    public void run() {
        try {
            while(true) {
                String request = in.readLine();
                if (request.contains("hi")) {
                    out.println("server says: hi my client");
                } else {
                    out.println("server says: try say hi");
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
