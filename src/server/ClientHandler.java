package server;

import client.GameClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    final static String unknownRequestMsg = "Client sent unknown request.";
    private Server server;
    private Socket client;
    private int id;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, int id, ArrayList<ClientHandler> clients, Server server) throws IOException {
        this.server = server;
        this.clients = clients;
        this.id = id;
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }


    public void ready() {
        out.println("We are ready to play");
    }


    public void outMessage(String message) {
        out.println(message);
    }


    private void handleClientsRequestAndRespond(String clientsRequest) {
        String[] clientRequestTokens = clientsRequest.split(" ");

        if(clientRequestTokens.length == 0)
        {
            return;
        }

        switch (clientRequestTokens[0])
        {
            case "NICK":
                server.handleNickRequest(clientRequestTokens);
                break;
            case "START_MOVE":
                server.handleStartMoveRequest(clientRequestTokens);
                break;
            case "CHANGE_POSITION" :
                server.handleChangePositionRequest(clientRequestTokens);
                break;
            case "END_MOVE" :
                server.handleEndMove();
                break;

        }

        return;
    }




    @Override
    public void run() {
        try {
            while(true) {
                String clientRequest = in.readLine();
                if (clientRequest == null) break;
                System.out.println(clientRequest);
                handleClientsRequestAndRespond(clientRequest);

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
