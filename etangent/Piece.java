public interface Piece {
    public boolean isValid(Board.Move move, Board board);
    public Side getSide();
    public boolean hasMoved();
}