public class King implements Piece {
    private final Side side;
    public final boolean hasMoved;

    public King(Side side) {
        this.side = side;
        this.hasMoved = true;
    }

    public King(Side side, Boolean hasMoved) {
        this.side = side;
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValid(Board.Move move, Board board) {
        if (move instanceof Board.Promotion) {
            return false;
        }

        if (board.getPiece(move.endX, move.endY).getSide() == side) {
            return false;
        }

        return Math.abs(move.endX - move.startX) <= 1 && (Math.abs(move.endY - move.startY) <= 1);
    }

    @Override
    public String toString() {
        return "K" + side;
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
