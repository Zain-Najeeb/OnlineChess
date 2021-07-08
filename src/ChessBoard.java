import processing.core.PApplet;
import processing.core.PImage;

import java.io.*;

public class ChessBoard extends PApplet implements Serializable {
    public static PImage WhiteHorse, WhitePawn, BlackPawn, BlackKnight, WhiteRook, BlackRook, WhiteBishop, BlackBishop, WhiteQueen, BlackQueen, WhiteKing, BlackKing;
    public static ChessBoard instance;
    public boolean hasStartedYet = false;
    public static int a = 0;
    public static int b = 16;
    public static ChessPiece[] Pieces = new ChessPiece[32];
    public static int[][] squares = new int[64][2];
    static boolean wait = false;
    public void settings() {

        BlackKing = loadImage("Images/BlackKing.png");
        WhiteKing = loadImage("Images/WhiteKing.png");
        BlackQueen = loadImage("Images/BlackQueen.png");
        WhiteQueen = loadImage("Images/WhiteQueen.png");
        WhiteBishop = loadImage("Images/WhiteBishop.png");
        WhiteRook = loadImage("Images/WhiteRook.png");
        BlackRook = loadImage("Images/BlackRook.png");
        BlackKnight = loadImage("Images/BlackKnight.png");
        WhiteHorse = loadImage("Images/WhiteHorse.png");
        WhitePawn = loadImage("Images/WhitePawn.png");
        BlackPawn = loadImage("Images/BlackPawn.png");
        BlackBishop = loadImage("Images/BlackBishop.png");
        BlackBishop.resize(600, 450);
        WhiteRook.resize(600, 450);
        WhiteHorse.resize(500, 375);
        BlackKnight.resize(550, 425);
        WhitePawn.resize(600, 450);
        BlackPawn.resize(600, 450);
        BlackRook.resize(600, 450);
        WhiteBishop.resize(600, 450);
        WhiteQueen.resize(600, 450);
        BlackQueen.resize(600, 450);
        WhiteKing.resize(600, 450);
        BlackKing.resize(600, 450);
        int x = 50;
        int y = 50;
        for(int i = 0; i < 64; i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 1) {
                    squares[i][1] = y;
                    y += 100;
                    if (y == 850) {
                        y = 50;
                        x += 100;
                    }
                } else {
                    squares[i][0] = x;
                }
            }
        }
        size(800, 800);
        if (instance == null) {
            instance = this;
        }
        for (int i = 0; i < 8; i++) {
            Pieces[i] = new Pawn(50 + (i * 100), 650);
        }
        for (int i = 16, s = 0; i < 24; i++, s++) {
            Pieces[i] = new Pawn(50 + (s * 100), 150);
        }
        Pieces[15] = new King(450, 750);
        Pieces[31] = new King(450, 50);
        Pieces[30] = new Queen(350, 50);
        Pieces[14] = new Queen(350, 750);
        Pieces[9] = new Knight(150, 750);
        Pieces[8] = new Knight(650, 750);
        Pieces[24] = new Knight(150, 50);
        Pieces[25] = new Knight(650, 50);
        Pieces[10] = new Rook(50, 750);
        Pieces[11] = new Rook(750, 750);
        Pieces[26] = new Rook(50, 50);
        Pieces[27] = new Rook(750, 50);
        Pieces[28] = new Bishop(250, 50);
        Pieces[29] = new Bishop(550, 50);
        Pieces[12] = new Bishop(250, 750);
        Pieces[13] = new Bishop(550, 750);
        if (ChessBoard.a == 16) {
           PieceFlipper(Pieces);
        }

    }

    public void mousePressed() {
        int x = mouseX;
        int y = mouseY;
        x = ((x / 100) * 100) + 50;
        y = ((y / 100) * 100) + 50;

        if (!PieceHelper.PieceClicked) {
            if (PieceHelper.IsPieceClicked(x,y, a ,b)) {
                Pieces[Main.Location].Highlight(x, y, Main.Location);
                Pieces[Main.Location].draw(x, y, Main.Location);
            }
        } else {
            PieceHelper.PieceMoving(x,y, Main.Location);
        }
    }

    public void draw() {
        ChessBoard c = ChessBoard.instance;
        if (wait) {
            try {
                wait = false;
                  try {
                      ChessPiece.check = PieceHelper.ois.readBoolean();
                      Pieces = (ChessPiece[]) PieceHelper.ois.readObject();
                      PieceFlipper(Pieces);

                      c.redrawEntireChessBoard();
                  } catch (ClassNotFoundException e) {
                      e.printStackTrace();
                  } catch (EOFException ignore) {}
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        ellipseMode(CENTER);
        imageMode(CENTER);
        if (!hasStartedYet) {
            redrawEntireChessBoard();
            hasStartedYet = true;
            if (a == 16) {
               ChessBoard.wait = true;
            }
        }
    }
    public  void redrawEntireChessBoard() {
        int beige = color(207, 185, 151);
        int brown = color(89, 60, 31);
        for (int i = 0; i <= 8; i++) {
            for (int ii = 0; ii <= 8; ii++) {
                boolean isBlacksquare = (i + ii) % 2 == 0;
                var colour = (isBlacksquare) ? brown : beige;
                rect(0, 0, 100, 100);
                rect(ii * 100, i * 100, 100, 100);
                fill(colour);
            }
        }


        if (PieceHelper.isKingAttacked(30)) {
            ChessPiece.check = true;
            Pieces[31].Highlight(Pieces[31].getXCord(), Pieces[31].getXCord(), 31 );
        }
        else if (PieceHelper.isKingAttacked(15)) {
            ChessPiece.check = true;
            Pieces[31].Highlight(Pieces[15].getXCord(), Pieces[15].getXCord(), 15 );
        }


        image(BlackKing, Pieces[31].getXCord(), Pieces[31].getYCord());
        image(WhiteKing, Pieces[15].getXCord(), Pieces[15].getYCord());
        image(BlackQueen, Pieces[30].getXCord(), Pieces[30].getYCord());
        image(WhiteQueen, Pieces[14].getXCord(), Pieces[14].getYCord());
        image(BlackBishop, Pieces[28].getXCord(), Pieces[28].getYCord());
        image(BlackBishop, Pieces[29].getXCord(), Pieces[29].getYCord());
        image(WhiteBishop, Pieces[12].getXCord(), Pieces[12].getYCord());
        image(WhiteBishop, Pieces[13].getXCord(), Pieces[13].getYCord());
        image(BlackRook, Pieces[26].getXCord(), Pieces[26].getYCord());
        image(BlackRook, Pieces[27].getXCord(), Pieces[27].getYCord());
        image(WhiteRook, Pieces[10].getXCord(), Pieces[10].getYCord());
        image(WhiteRook, Pieces[11].getXCord(), Pieces[11].getYCord());
        image(BlackKnight, Pieces[24].getXCord(), Pieces[24].getYCord());
        image(BlackKnight, Pieces[25].getXCord(), Pieces[25].getYCord());
        image(WhiteHorse, Pieces[8].getXCord(), Pieces[8].getYCord());
        image(WhiteHorse, Pieces[9].getXCord(), Pieces[9].getYCord());
        for (int i = 0, s = 16; i < 8; i++, s++) {
            if (Pieces[i].getType().equalsIgnoreCase("QUEEN")) {
                image(WhiteQueen, Pieces[i].getXCord(), Pieces[i].getYCord());
            }
            else {
                image(WhitePawn, Pieces[i].getXCord(), Pieces[i].getYCord());
            }

            if (Pieces[s].getType().equalsIgnoreCase("QUEEN")) {
                image(BlackQueen, Pieces[s].getXCord(), Pieces[s].getYCord());
            }
            else {
                image(BlackPawn, Pieces[s].getXCord(), Pieces[s].getYCord());
            }
        }
    }
    //FLIP THAT FLOPPER
    public static void PieceFlipper(ChessPiece[] args) {
        int end = 800;
        int num = -1;
        if (ChessBoard.a != 16) {
            num = 1;
            end = 0;
        }
        for (int i = 0; i < ChessBoard.Pieces.length; i++) {
            args[i] = PieceHelper.getObj( i, end + (num* args[i].getXCord()), end + (num* args[i].getYCord()));
        }
    }
}
