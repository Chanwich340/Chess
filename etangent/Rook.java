public class Rook implements Piece {
    private final Side side;
    public final boolean hasMoved;

    public Rook(Side side) {
        this.side = side;
        this.hasMoved = true;
    }

    public Rook(Side side, Boolean hasMoved) {
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
        
        //makes sure one is constant
        if (!(move.endX - move.startX == 0 || move.endY - move.startY == 0)) {
            return false;
        }

        //assuming y is the constant
        for (int i = Math.min(move.endX, move.startX) + 1; i < Math.max(move.endX, move.startX); i++) {
            if (!(board.getPiece(i, move.startY) instanceof None)) {
                return false;
            }
        }

        //assuming x is the constant
        for (int i = Math.min(move.endY, move.startY) + 1; i < Math.max(move.endY, move.startY); i++) {
            if (!(board.getPiece(move.startX, i) instanceof None)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "R" + side;
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
