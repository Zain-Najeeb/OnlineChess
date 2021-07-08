import processing.core.PImage;

public class King extends ChessPiece{
    public King(int x, int y) {
        super(x, y);
    }
    public static int castleLocation =-1;
    public boolean getValidMoves(int x, int y, int location) {
        int posX = ChessBoard.Pieces[location].getXCord();
        int posY = ChessBoard.Pieces[location].getYCord();
        outer: for (int i = 0; i < 3; i++) {
            for (int ii =0; ii< 3; ii++) {
                if (x == posX +(-100 + 100*ii) && y == posY +(-100 + 100*i) && !( (-100 + 100*ii) ==  0 &&  0== (-100 + 100*i)) ) {
                    for (int j = 0; j < ChessBoard.Pieces.length; j++) {
                        if (x == ChessBoard.Pieces[j].getXCord() && ChessBoard.Pieces[j].getYCord() == y) {
                            if ((location < 16 && j >= 16) || (location > 16 && j < 16)) {
                                Main.CapturedPiece = j;
                                return true;
                            } else {
                                break outer;
                            }
                        }
                    }
                    return true;
                }
            }

        }
        if (!check) {
            if (ChessBoard.a == 0 && !Main.WhiteKingMoved) {
                if (!Main.RightRookWhiteMoved) {
                    if (castling(posX, posY, x, y, 3, 1)) {
                        castleLocation = 11;
                        return  true;
                    }
                }
                if (!Main.LeftRookWhiteMoved) {
                    if (castling(posX, posY, x, y, 4, -1)) {
                        castleLocation = 10;
                        return  true;
                    }
                }
            }
            else if (ChessBoard.a == 16 && !Main.BlackKingMoved) {
                if (!Main.RightBlackRookMoved) {
                    if (castling(posX, posY, x, y, 3, -1)) {
                        castleLocation = 27;
                        return  true;
                    }

                }
                if (!Main.LeftBlackRookMoved) {
                    if (castling(posX, posY, x, y, 4, 1)) {
                        castleLocation = 26;
                        return  true;
                    }
                }
            }
        }
        return false;
    }
    public void draw(int x, int y, int location) {

        for (int i = 0; i < 64; i++) {
            PieceHelper.Drawhelpr(x, y, i, location, ChessBoard.squares);
        }
    }
    @SuppressWarnings("DuplicateExpressions")
    boolean castling(int posx, int posy ,int x, int y, int j, int signum) {
        for (int i = 0; i < j; i++) {
            for (int ii = 0; ii < 32; ii++) {
                if (posy == ChessBoard.Pieces[ii].getYCord() && posx + signum*(100 + (i*100)) == ChessBoard.Pieces[ii].getXCord() && i != j-1) {
                    return false;
                }
            }
            if (x == posx  + signum*(100 + (i*100)) && y == posy ) {
                return  true;
            }
        }
        return false;
    }
    public void Highlight(int x, int y, int location) {
        ChessBoard c = ChessBoard.instance;
        PImage King = ChessBoard.WhiteKing;
        x = ChessBoard.Pieces[15].getXCord();
        y = ChessBoard.Pieces[15].getYCord();
        if (location >= 16) {
            x = ChessBoard.Pieces[31].getXCord();
            y = ChessBoard.Pieces[31].getYCord();
            King = ChessBoard.BlackKing;
        }
        var green = c.color(0, 255, 0);
        var red = c.color(255, 0, 0);
        var fill = check? red: green;
        c.fill(fill);
        c.rect(x - 50, y - 50, 100, 100);
        c.image(King, x, y);
    }
    String getType() {
        return "KINg";
    }
}
