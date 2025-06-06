public class None implements Piece {
    @Override
    public boolean isValid(Board.Move move, Board board) {
        return false;
    }

    @Override
    public String toString() {
        return "  ";
    }

    @Override
    public Side getSide() {
        return null;
    }

    @Override
    public boolean hasMoved() {
        return false;
    }
}
