import java.io.Serializable;

abstract class ChessPiece implements Serializable {
    private int x;
    private int y;
    public static  boolean check = false;
    public ChessPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getXCord() {
        return  x;
    }
    public int getYCord() {
        return y;
    }
    abstract boolean getValidMoves(int x, int y, int location);
    public abstract void draw(int x, int y, int location);
    abstract String getType();
    public abstract void Highlight(int x,int y, int location);
}

