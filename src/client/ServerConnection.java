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



    @Override
    public void run() {
        try {
            while (true) {

                //zmenit ako server
                String serverResponse = in.readLine();
                if(serverResponse == null) break;
                if(serverResponse.contains("start")) {
                    System.out.println(serverResponse);
                    createGame(serverResponse);
                }

                if(serverResponse.contains("VYBER_POLICKO " + nick)) gameClient.vyber_policko(serverResponse);
                System.out.println("Server says: " + serverResponse);

                if(serverResponse.contains("NEW_MISSION")) gameClient.new_mission(serverResponse);
                if(serverResponse.contains("STARTED_MISSION")) gameClient.started_mission(serverResponse);
                if(serverResponse.contains("CHANGE_POSITION_OPPONENT")) gameClient.change_position_opponent(serverResponse);
                if(serverResponse.contains("NEW_PLAYER_MOVE")) gameClient.setNewPlayerMove(serverResponse);
                if(serverResponse.contains("END_GAME")) gameClient.end_game(serverResponse);
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

    private void createGame(String serverResponse) {
        boolean isYourTurn;
        String[] serverResponseTokens = serverResponse.split(" ");
        int count = 2;

        StartInfoPlayer myInfo = null;
        StartInfoPlayer opponentInfo = null;


        //nejak rozumnejsie potom :D
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
        System.out.println(isYourTurn);

        gameClient.createGame(isYourTurn, myInfo, opponentInfo);
    }
}
