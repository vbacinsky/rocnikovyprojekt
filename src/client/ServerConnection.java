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
                String serverResponse = in.readLine();
                if(serverResponse == null) break;
                if(serverResponse.contains("start")) {
                    System.out.println(serverResponse);
                    createGame(serverResponse);
                }
                System.out.println("Server says: " + serverResponse);
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
        System.out.println(serverResponseTokens[1]);
        System.out.println(nick);

        isYourTurn = serverResponseTokens[1].equals("." + nick);

        System.out.println(isYourTurn);
        while (!serverResponseTokens[count].equals(nick)) {
            count++;
        }
        System.out.println(count);
        String color = serverResponseTokens[count + 1];
        String from = serverResponseTokens[count + 2];
        String to = serverResponseTokens[count + 3];
        String image = serverResponseTokens[count + 4];
        System.out.println(image);
        gameClient.createGame(nick, color, from, to, image, isYourTurn);
    }
}
