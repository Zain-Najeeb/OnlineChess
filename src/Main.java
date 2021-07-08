import processing.core.*;

import java.util.Scanner;

public class Main {

    public static int Location;
    public static int CapturedPiece = -1;
    public static boolean WhiteKingMoved = false ;
    public static boolean BlackKingMoved = false;
    public static boolean LeftBlackRookMoved = false;
    public static boolean RightBlackRookMoved = false;
    public static boolean RightRookWhiteMoved = false ;
    public static boolean LeftRookWhiteMoved = false ;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


    while (true) {
        System.out.println("Hello!, Are you the Client or server?");
        String nInput = input.nextLine();
        if (nInput.equalsIgnoreCase("server")) {
            ChessBoard.a = 0;
            ChessBoard.b = 16;
            Server.server();
            break;
        } else if (nInput.equalsIgnoreCase("client")) {
            ChessBoard.a = 16;
            ChessBoard.b = 32;
            System.out.println("What is the ipv4 address of the player you're trying to connect with? If you are playing by yourself please type in 'localhost' ");
            nInput = input.nextLine();
            Client.client(nInput);
            break;
        } else {
            System.out.println("It seems like you didn't type in 'server' or 'client' (caps do not matter), did you make a typo? ");
        }
    }


        String[] processingArgs = {"MySketch"};
        ChessBoard Sketch = new ChessBoard();


        PApplet.runSketch(processingArgs, Sketch);
    }
}
