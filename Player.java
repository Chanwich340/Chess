public interface Player {
    Board.Move getInput(Board board);
    Side getSide();
}
