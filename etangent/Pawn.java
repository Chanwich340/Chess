public class Pawn implements Piece {
    private final Side side;
    public final boolean hasMoved;

    public Pawn(Side side) {
        this.side = side;
        this.hasMoved = true;
    }

    public Pawn(Side side, Boolean hasMoved) {
        this.side = side;
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValid(Board.Move move, Board board) {
        if (move instanceof Board.Promotion && move.getEndPiece(board) != null) {
            if (!(board.getPiece(move.startX, move.startY) instanceof Pawn && move.startY == 1) && side == Side.WHITE || !(board.getPiece(move.startX, move.startY) instanceof Pawn && move.startY== 6) && side == Side.BLACK) {
                return false;
            }

            return isValid(new Board.Promotion(move.startX, move.startY, move.endX, move.endY, null), board);
        } else if (!(move instanceof Board.Promotion)) {
            if (move.startY == 1 && side == Side.WHITE || move.startY== 6 && side == Side.BLACK) {
                return false;
            }
        }

        if (board.getPiece(move.endX, move.endY).getSide() == side) {
            return false;
        }
        
        //two space moves
        if (move.endY - move.startY != 1 && side == Side.BLACK) {
            if (!(move.endY - move.startY == 2 && move.startY == 1 && board.getPiece(move.startX, 2) instanceof None)) {
                return false;
            }
        } else if (move.endY - move.startY != -1 && side == Side.WHITE) {
            if (!(move.endY - move.startY == -2 && move.startY == 6 && board.getPiece(move.startX, 5) instanceof None)) {
            return false;
            }
        }

        if (move.endX - move.startX == 0 && board.getPiece(move.endX, move.endY) instanceof None) {
            return true;
        } else if (Math.abs(move.endX - move.startX) == 1 && Math.abs(move.endY - move.startY) == 1 && (!(board.getPiece(move.endX, move.endY) instanceof None) || board.getPiece(move.endX, move.endY) instanceof GhostPawn)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "P" + side;
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
