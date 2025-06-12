public class Bishop implements Piece {
    private final Side side;
    public final boolean hasMoved;

    public Bishop(Side side) {
        this.side = side;
        this.hasMoved = true;
    }

    public Bishop(Side side, Boolean hasMoved) {
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

        if (Math.abs(move.endX - move.startX) != Math.abs(move.endY - move.startY)) {
            return false;
        }

        for (int i = move.startX + (int) Math.signum(move.endX - move.startX), j = move.startY + (int) Math.signum(move.endY - move.startY); i != move.endX && j != move.endY; i += Math.signum(move.endX - move.startX), j += Math.signum(move.endY - move.startY)) {
            if (!(board.getPiece(i, j) instanceof None)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "B" + side;
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
