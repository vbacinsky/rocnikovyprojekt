package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, Server server) throws IOException {
        this.server = server;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void outMessage(String message) {
        out.println(message);
    }

    private void handleClientsRequest(String clientsRequest) {
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
            case "TERMINATE" :
                server.handleTerminate();
                break;
            case "PUT_NEW_CHIP" :
                server.handlePutNewChip(clientsRequest);
                break;
            case "ENTERED_ON_CHIP" :
                server.handleSomeoneEnteredOnCip(clientRequestTokens);
                break;
            default: break;
        }
    }


    @Override
    public void run() {
        try {
            while(true) {
                String clientRequest = in.readLine();
                if (clientRequest == null) break;
                System.out.println(clientRequest);
                handleClientsRequest(clientRequest);
                if(clientRequest.equals("TERMINATE")) break;
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
