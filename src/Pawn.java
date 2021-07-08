import processing.core.PImage;
public class Pawn extends ChessPiece{
    public Pawn(int x, int y) {
        super(x, y);
    }
    public boolean getValidMoves(int x, int y, int location) {
        int posX = ChessBoard.Pieces[location].getXCord();
        int posY = ChessBoard.Pieces[location].getYCord();
        int a = -2;
        if (posY  != 650 ) {
            a = a / 2;
        }
       outer: for (int i =1; i <= Math.abs(a); i++) {
           int directionY = i * -100;
           for (int ii = 0; ii < 32; ii ++) {
                if (posX == ChessBoard.Pieces[ii].getXCord() && posY + directionY == ChessBoard.Pieces[ii].getYCord() ) {
                    break outer;
                }
            }
            if (posX == x && posY + directionY == y) {
                return true;
            }
        }
        int b = 16;
        int c = 32;
        if (ChessBoard.a == 16) {
            c = b;
            b = 0;
        }
        for (int i = b; i < c; i++)
        if ( (posX + 100 == x || posX - 100 == x) && posY - 100 == y ) {
            if (ChessBoard.Pieces[i].getXCord() == x && ChessBoard.Pieces[i].getYCord() == y) {
                Main.CapturedPiece = i;
                return true;
            }
        }
        return  false;

    }
    @SuppressWarnings("DuplicateExpressions")
    public void draw(int x, int y, int location) {
        ChessBoard c = ChessBoard.instance;
      int a = -2;
      if (y != 650) {
          a = a/2;
      }
      for (int i =1; i <= Math.abs(a); i++) {
          if (getValidMoves(x, y + (i*100 *(Math.abs(a)/a)) , location)) {
              ChessBoard.Pieces[Main.Location] = PieceHelper.getObj(location, x, y + (i*100 *(Math.abs(a)/a)));
              if (!PieceHelper.isKingAttacked(location)) {
                  c.ellipse(x, y + (i * 100 * ((int) (Math.abs(a) / a))), 30, 30);
                  c.fill(0, 255, 0);
              }
             ChessBoard.Pieces[location]= PieceHelper.getObj(location, x, y);
          } else {
              break;
          }
      }
      for (int i =0; i <2; i++) {
          if (getValidMoves(x-100 + i*(i*100 + 100), y + 100* (Math.abs(a)/a), location)) {
              ChessBoard.Pieces[Main.Location] = PieceHelper.getObj(location, x-100 + i*(i*100 + 100), y + (i*100 *(Math.abs(a)/a)));
              ChessPiece p = PieceHelper.getObj(Main.CapturedPiece, ChessBoard.Pieces[Main.CapturedPiece].getXCord(), ChessBoard.Pieces[Main.CapturedPiece].getYCord());
              ChessBoard.Pieces[Main.CapturedPiece] = new Pawn(100000, 100000);
              int Captured = Main.CapturedPiece;
              if (!PieceHelper.isKingAttacked(location)) {
                  ChessBoard.Pieces[Captured] = p;
                  ChessBoard.Pieces[Main.CapturedPiece].Highlight(x - 100 + i * (i * 100 + 100), y + 100 * (Math.abs(a) / a), Main.CapturedPiece);
              }
              ChessBoard.Pieces[Captured] = p;
              ChessBoard.Pieces[location]= PieceHelper.getObj(location, x, y);
              Main.CapturedPiece = -1;
          }
      }
    }
    public void Highlight(int x, int y, int location) {
        ChessBoard c = ChessBoard.instance;
        PImage pawn = ChessBoard.WhitePawn;
        if (location >= 16) {
            pawn = ChessBoard.BlackPawn;
        }
        c.fill(0,255,0);
        c.rect(x - 50, y - 50,100, 100);
        c.image(pawn,x,y);
    }
    public String getType() {
        return  "PAWN";
    }
}
