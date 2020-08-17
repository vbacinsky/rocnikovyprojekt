package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class  ServerConnection implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private static PrintWriter out;
    private GameClient gameClient;
    private String nick;

    public ServerConnection(String host, int port, GameClient gameClient, String nick) throws IOException {
        this.socket = new Socket(host, port);
        this.nick = nick;
        this.gameClient = gameClient;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("NICK " + nick);
        new Thread(this).start();
    }


    public void sendMessage(String message) {
        out.println(message);
    }


    private void handleServerResponse(String serverResponse) {
        String[] serverResponseTokens = serverResponse.split(" ");

        if (serverResponseTokens.length == 0) {
            return;
        }

        switch (serverResponseTokens[0]) {
            case "start":
                createGame(serverResponseTokens);
                break;
            case "VYBER_POLICKO":
                gameClient.vyber_policko(serverResponseTokens);
                break;
            case "NEW_MISSION":
                gameClient.new_mission(serverResponseTokens);
                break;
            case "STARTED_MISSION":
                gameClient.started_mission(serverResponseTokens);
                break;
            case "CHANGE_POSITION_OPPONENT":
                gameClient.change_position_opponent(serverResponseTokens);
                break;
            case "NEW_PLAYER_MOVE":
                gameClient.setNewPlayerMove(serverResponseTokens);
                break;
            case "END_GAME":
                gameClient.end_game(serverResponseTokens);
                break;
            case "PUT_NEW_CHIP" :
                gameClient.add_chip(serverResponseTokens);
                break;
            case "SOMEONE_ENTERED_ON_CHIP" :
                gameClient.someone_entered_on_chip(serverResponseTokens);

            default: break;
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                String serverResponse = in.readLine();
                if(serverResponse == null) break;
                System.out.println(serverResponse);
                handleServerResponse(serverResponse);
                if(serverResponse.contains("SERVER_END")) break;

            }
        } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    private void createGame(String[] serverResponseTokens) {
        boolean isYourTurn;
        int count = 2;

        StartInfoPlayer myInfo = null;
        StartInfoPlayer opponentInfo = null;

        for (int i = 0; i < 2; i++) {
            if (serverResponseTokens[count].equals(nick)) {
               myInfo = new StartInfoPlayer(serverResponseTokens[count], serverResponseTokens[count+1],
                        serverResponseTokens[count+2], serverResponseTokens[count+3], serverResponseTokens[count+4], serverResponseTokens[count+5], false);
            } else {
               opponentInfo = new StartInfoPlayer(serverResponseTokens[count], serverResponseTokens[count+1],
                        serverResponseTokens[count+2], serverResponseTokens[count+3], serverResponseTokens[count+4], serverResponseTokens[count+5], true);
            }
            count = count + 6;
        }

        isYourTurn = serverResponseTokens[1].equals("." + nick);
        gameClient.createGame(isYourTurn, myInfo, opponentInfo);
    }
}
