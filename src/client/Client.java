package client;

/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public Client(String host, int port) {
        Socket socket = new Socket(host, port);

        ServerConnection serverConnection = new ServerConnection(socket);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(serverConnection).start();

        while(true) {
            System.out.println("> ");
            String command = keyboard.readLine();
            if(command.equals("quit")) break;
            out.println(command);

        }



        socket.close();
    }

    private static void initgame() {
        System.out.println("inicializuj hru");
    }
}
*/