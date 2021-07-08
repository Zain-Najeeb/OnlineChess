import processing.core.PImage;
public class Rook extends ChessPiece{
    public Rook(int x, int y) {
        super(x, y);
    }
    public boolean getValidMoves(int x, int y, int location) {
        int posX = ChessBoard.Pieces[location].getXCord();
        int posY = ChessBoard.Pieces[location].getYCord();
        return PieceHelper.RookBishopMoves(x, y, posX, posY, 0, -100, location) || PieceHelper.RookBishopMoves(x, y, posX, posY, 0, 100, location) || PieceHelper.RookBishopMoves(x, y, posX, posY, 100, 0, location) || PieceHelper.RookBishopMoves(x, y, posX, posY, -100, 0, location);
    }
    public void draw(int x, int y, int location) {
        for (int i = 0; i < 64; i++) {
            PieceHelper.Drawhelpr(x ,y, i, location, ChessBoard.squares);
        }
    }
    public void Highlight(int x, int y, int location) {
        ChessBoard c = ChessBoard.instance;
        PImage rook = ChessBoard.WhiteRook;
        if (location >= 16) {
            rook = ChessBoard.BlackRook;
        }
        c.fill(0, 255, 0);
        c.rect(x - 50, y - 50, 100, 100);
        c.image(rook, x, y);

    }
    String getType() {
        return "Rook";
    }
}

