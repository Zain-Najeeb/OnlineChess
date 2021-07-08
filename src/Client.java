import java.io.*;
import java.net.Socket;

public class Client {

    public static void client(String args) {
        int port = 1273;
        try {
            System.out.println("Connecting to " + args + " on port " + port);
            Socket client = new Socket(args, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());

            PieceHelper.oos = new ObjectOutputStream(client.getOutputStream());
            PieceHelper.ois = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
