import processing.core.PImage;

public class Knight extends ChessPiece {
    public Knight(int x, int y) {
        super(x, y);
    }
    public boolean getValidMoves(int x, int y, int location) {
        int posX = ChessBoard.Pieces[location].getXCord();
        int posY = ChessBoard.Pieces[location].getYCord();
        int[][] KnightMoves = getKnightMoves(posX, posY);
        outer: for (int i = 0; i < 8; i++) {
            if (x == KnightMoves[i][0] && y == KnightMoves[i][1]) {
                for (int ii = 0; ii < ChessBoard.Pieces.length; ii++) {
                    if (KnightMoves[i][1] == ChessBoard.Pieces[ii].getYCord() && ChessBoard.Pieces[ii].getXCord() == KnightMoves[i][0]) {
                        if ((location < 16 && ii >= 16) || (location > 16 && ii < 16)) {
                            Main.CapturedPiece = ii;
                            return true;
                        } else {
                            break outer;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void draw(int x, int y, int location) {
        int[][] KnightMoves = getKnightMoves(x, y);
        for (int i = 0; i < 8; i++) {
            PieceHelper.Drawhelpr(x ,y, i, location, KnightMoves);
        }
    }
    public int[][] getKnightMoves(int posX, int posY) {
        int[][] KnightMoves = new int[8][2];
        int num = -200;
        int num2 = -100;
        for (int i = 0, s = 0; i < 8; i++, s++) {
            if (i % 2 == 0 && i != 0) {
                s = 0;
                if (i != 4) {
                    num2 = num2 * -1;
                } else {
                    num = num * -1;
                }
            }
            KnightMoves[i][1] = posY - num + ((int) Math.signum(num) * (s * 100));
            KnightMoves[i][0] = posX + num2 + ((int) Math.signum(num2) * (s * 100));
        }
        return KnightMoves;
    }

    public void Highlight(int x, int y, int location) {
        ChessBoard c = ChessBoard.instance;
        PImage knight = ChessBoard.WhiteHorse;
        if (location >= 16) {
            knight = ChessBoard.BlackKnight;
        }
        c.fill(0, 255, 0);
        c.rect(x - 50, y - 50, 100, 100);
        c.image(knight, x, y);
    }
    String getType() {
        return "KNIGHT";
    }
}
