import java.net.*;
import java.io.*;
public class PieceHelper implements Serializable{


    static ObjectOutputStream oos;
    static ObjectInputStream ois;


    public static boolean PieceClicked = false;
    public static boolean IsPieceClicked(int x, int y, int a, int b) {
        for (int i = a; i < b; i ++) {
            if (x == ChessBoard.Pieces[i].getXCord() && y == ChessBoard.Pieces[i].getYCord() ) {
                PieceClicked = true;
                Main.Location = i;
                return true;
            }
        }
        return  false;
    }
    public static boolean RookBishopMoves(int x, int y, int posX, int posY, int directionX, int directionY, int location) {
        outer: for (int i = 1; i < 8; i++) {
                for (int ii = 0; ii < 32; ii++) {
                    if (posX + directionX * i == ChessBoard.Pieces[ii].getXCord() && posY + directionY * i == ChessBoard.Pieces[ii].getYCord()) {
                        if ((location < 16 && ii >= 16) || (location > 16 && ii < 16)) {
                            if ((x == posX + directionX * i  && y == posY + directionY * i)) {
                                Main.CapturedPiece = ii;
                                return true;
                            } else {
                                break outer;
                            }
                        } else {
                            break outer;
                        }
                    }
                }
            if (x == posX + directionX * i && y == posY + directionY * i) {
                return  true;
            }
        }
        return false;
    }
    public static boolean isKingAttacked(int location) {
        int b = 16;
        int c = 32;
        int d = 15;
        if (location >= 16) {
            c = b;
            b = 0;
            d = 31;
        }
        for (int i = b; i < c; i++) {
            if (ChessBoard.Pieces[i].getValidMoves(ChessBoard.Pieces[d].getXCord(), ChessBoard.Pieces[d].getYCord(), i  )) {
                Main.CapturedPiece = -1;
                return true;
            }
        }
        return  false;
    }
    public static ChessPiece getObj(int location, int x, int y) {
        ChessPiece p = new Pawn(x,y);
        if (ChessBoard.Pieces[location].getType().equalsIgnoreCase("knight")) {
            p = new Knight(x, y);
        }
        if (ChessBoard.Pieces[location].getType().equalsIgnoreCase("Rook")) {
            p = new Rook(x, y);
        }
        if (ChessBoard.Pieces[location].getType().equalsIgnoreCase("Bishop")) {
            p = new Bishop(x, y);
        }
        if (ChessBoard.Pieces[location].getType().equalsIgnoreCase("Queen")) {
            p = new Queen(x, y);
        }
        if (ChessBoard.Pieces[location].getType().equalsIgnoreCase("King")) {
            p = new King(x, y);
        }
        return p;
    }
    public static void Drawhelpr(int x, int y, int i, int location, int [][] arr) {
        ChessBoard c = ChessBoard.instance;
        if (ChessBoard.Pieces[location].getValidMoves(arr[i][0], arr[i][1], location)) {
            ChessBoard.Pieces[location]= PieceHelper.getObj(location, arr[i][0], arr[i][1]);
            if (Main.CapturedPiece == -1) {
                if (!PieceHelper.isKingAttacked(location)) {
                    c.fill(0, 255, 0);
                    c.ellipse(arr[i][0], arr[i][1], 30, 30);
                    if (King.castleLocation != -1) {
                        ChessBoard.Pieces[King.castleLocation].Highlight(ChessBoard.Pieces[King.castleLocation].getXCord(), ChessBoard.Pieces[King.castleLocation].getYCord(), King.castleLocation);
                    }
                }
            } else {
                ChessPiece p = PieceHelper.getObj(Main.CapturedPiece, ChessBoard.Pieces[Main.CapturedPiece].getXCord(), ChessBoard.Pieces[Main.CapturedPiece].getYCord());
                ChessBoard.Pieces[Main.CapturedPiece] = new Pawn(100000, 100000);
                int Captured = Main.CapturedPiece;
                if (!PieceHelper.isKingAttacked(location)) {
                    ChessBoard.Pieces[Captured] = p;
                    ChessBoard.Pieces[Main.CapturedPiece].Highlight(arr[i][0], arr[i][1], Main.CapturedPiece);
                }
                ChessBoard.Pieces[Captured] = p;
                ChessBoard.Pieces[location]= PieceHelper.getObj(location, x, y);
            }
            King.castleLocation = -1;
            Main.CapturedPiece = -1;
            ChessBoard.Pieces[location]= PieceHelper.getObj(location, x, y);
        }
    }
    public static void CastleChecker(int location) {
        switch (location) {
            case 15 -> Main.WhiteKingMoved = true;
            case 31 -> Main.BlackKingMoved = true;
            case 10 -> Main.LeftRookWhiteMoved = true;
            case 11 -> Main.RightRookWhiteMoved = true;
            case 26 -> Main.LeftBlackRookMoved = true;
            case 27 -> Main.RightBlackRookMoved = true;
        }
    }

    public static void PieceMoving(int x, int y, int location) {
        ChessBoard c = ChessBoard.instance;
        boolean correct = false;
        int oldX = ChessBoard.Pieces[Main.Location].getXCord();
        int Captured = -1;
        ChessPiece p = new Pawn(100000, 1000);
        int oldY = ChessBoard.Pieces[Main.Location].getYCord();
         if (ChessBoard.Pieces[Main.Location].getValidMoves(x, y, location)) {
             if (King.castleLocation != -1) {

                 if (ChessBoard.a == 0) {
                     if (King.castleLocation % 2 == 0) {
                         x = 250;
                     } else {
                         x = 650;
                     }
                 } else {
                     if (King.castleLocation%2 == 0) {
                         x = 550;
                     } else {
                         x = 150;
                     }
                 }

             }
            ChessBoard.Pieces[Main.Location] = getObj(location, x, y);
            if (Main.CapturedPiece != -1) {
                Captured = Main.CapturedPiece;
                p = getObj(Main.CapturedPiece, ChessBoard.Pieces[Main.CapturedPiece].getXCord(), ChessBoard.Pieces[Main.CapturedPiece].getYCord());
                ChessBoard.Pieces[Main.CapturedPiece] = new Pawn(100000, 100000);
            }
            if (isKingAttacked(location)) {
                ChessBoard.Pieces[Main.Location] = getObj(location, oldX, oldY);
                if (Captured != -1) {
                    ChessBoard.Pieces[Captured] = p;
                }
            } else {
                correct = true;
                if (King.castleLocation != -1) {
                    int  rookX;

                    if (ChessBoard.a == 0) {
                        if (King.castleLocation % 2 == 0) {
                            rookX = 350;
                        } else {
                            rookX = 550;
                        }
                    } else {
                        if (King.castleLocation%2 == 0) {
                            rookX = 450;
                        } else {
                            rookX = 250;
                        }
                    }

                    ChessBoard.Pieces[King.castleLocation] = new Rook (rookX, ChessBoard.Pieces[location].getYCord());
                }
                if (Captured != -1) {
                    CastleChecker(Captured);
                }
                CastleChecker(location);
                if ((y== 50 || y == 750) && ChessBoard.Pieces[location].getType().equalsIgnoreCase("PAWN")) {
                    ChessBoard.Pieces[location] = new Queen(x, y);
                }
            }
        }
        King.castleLocation = -1;
        Main.CapturedPiece = -1;
        PieceClicked = false;
        c.redrawEntireChessBoard();

        if (correct) {
            ChessPiece.check = false;
            try {
                 oos.writeBoolean(false);
                ChessBoard.PieceFlipper(ChessBoard.Pieces);
                oos.writeObject(ChessBoard.Pieces);

            } catch (IOException e) {
                e.printStackTrace();
            }
         ChessBoard.wait = true;

        }
    }
}