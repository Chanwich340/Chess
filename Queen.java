public class Queen implements Piece {
    private final Side side;
    public final boolean hasMoved;

    public Queen(Side side) {
        this.side = side;
        this.hasMoved = true;
    }

    public Queen(Side side, Boolean hasMoved) {
        this.side = side;
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValid(Board.Move move, Board board) {
        return new Bishop(side).isValid(move, board) || new Rook(side).isValid(move, board);
    }

    @Override
    public String toString() {
        return "Q" + side;
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public boolean hasMoved() {
        return hasMoved;
    }
}
