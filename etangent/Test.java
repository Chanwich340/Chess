
public class Test {
    public static void main(String[] args) {
        notation();
        kingCheck();
        pawnMove();
        knightMove();
        rookMove();
        kingMove();
        bishopMove();
        promotion();
        valuation();
        lookAhead();
        testCastle();
        pawnRewards();
    }

    public static Board assertMove(Board.Move move, Side side, String tag, Board board) {
        assertB(board.isValid(move, side), tag);
        return board.movePiece(move, side);
    }
    
    public static void assertB(boolean condition, String tag) {
        if (!condition) {
            throw new RuntimeException(tag);
        }
    }

    public static Board assertMove(Board.Move move, Side side, Board board) {
        assertB(board.isValid(move, side));
        return board.movePiece(move, side);
    }

    public static void assertB(boolean condition)  {
        if (!condition) {
            throw new RuntimeException();
        }
    }

    private static void notation() {
        assertB(Human.isValid("a2a3"));
        assertB(!Human.isValid("1111"));
        assertB(!Human.isValid("e9e8"));
        assertB(!Human.isValid("a0a1"));
        assertB(!Human.isValid("j8hh8"));
        assertB(Human.isValid("a8h8"));
    }

    private static void kingCheck() {
        Piece[][] sampleBoardArr = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            sampleBoardArr[i] = Board.noRow();
        }

        sampleBoardArr[0][0] = new King(Side.BLACK);
        sampleBoardArr[1][1] = new Queen(Side.WHITE);
        sampleBoardArr[7][7] = new King(Side.WHITE);
        Board board = new Board(sampleBoardArr);
        assertB(board.legalMoves(Side.BLACK).size() != 0);
    }

    private static void kingMove() {
        Board board = new Board(Board.startBoard());

        board = assertMove(Human.moveOf("e2e3"), Side.WHITE, board);
        assertB(board.isValid(Human.moveOf("e1e2"), Side.WHITE));
        assertB(!board.isValid(Human.moveOf("e1e3"), Side.WHITE));
    }

    private static void rookMove() {
        Board board = new Board(Board.startBoard());

        board = assertMove(Human.moveOf("a7a5"), Side.BLACK, board);
        assertB(board.isValid(Human.moveOf("a8a6"), Side.BLACK));
    }

    private static void bishopMove() {
        Board board = new Board(Board.startBoard());

        board = assertMove(Human.moveOf("e2e4"), Side.WHITE, board);
        assertB(board.isValid(Human.moveOf("f1b5"), Side.WHITE));
    }

    private static void knightMove() {
        Board board = new Board(Board.startBoard());

        assertB(board.isValid(Human.moveOf("g8f6"), Side.BLACK));
        board = assertMove(Human.moveOf("d2d4"), Side.WHITE, board);
        assertB(board.isValid(Human.moveOf("b1d2"), Side.WHITE));
    }

    private static void pawnMove() {
        Board board = new Board(Board.startBoard());

        board = assertMove(Human.moveOf("a7a5"), Side.BLACK, board);
        board = assertMove(Human.moveOf("a2a4"), Side.WHITE, board);
        assertB(!board.isValid(Human.moveOf("a5a4"), Side.BLACK));
        board = assertMove(Human.moveOf("b2b4"), Side.WHITE, board);
        assertB(board.isValid(Human.moveOf("a5b4"), Side.BLACK));
    }

    private static void promotion() {
        Piece[][] boardArr = Board.noBoard();
        boardArr[0][0] = new King(Side.BLACK);
        boardArr[7][7] = new King(Side.WHITE);
        boardArr[6][0] = new Pawn(Side.BLACK);
        assertB(new Board(boardArr).isValid(new Board.Promotion(0, 6, 0, 7, s -> new Queen(s)), Side.BLACK), "alkfjalkds;fjd");
    }

    private static void valuation() {
        Piece[][] board = Board.startBoard();
        assertB(Bot.getValuation(new Board(board)) == 0);
        board[1][0] = new None();
        assertB(Bot.getValuation(new Board(board)) == 1);
        board[7][7] = new None();
        assertB(Bot.getValuation(new Board(board)) == -4);
        board[7][7] = new Queen(Side.WHITE);
        assertB(Bot.getValuation(new Board(board)) == 5);
    }

    private static void lookAhead() {
        int depth = 1;
        Piece[][] board = Board.noBoard();
        board[0][0] = new Rook(Side.BLACK);
        board[0][1] = new Rook(Side.WHITE);
        board[7][3] = new King(Side.WHITE);
        board[7][7] = new King(Side.BLACK);
        
        assertB(Bot.lookAhead(new Board(board), depth, -1 * 10^7, 1 * 10^7, Side.WHITE).move().equals(new Board.Move(1, 0, 0, 0)));
    }

    private static void testCastle() {
        Piece[][] boardArr = Board.noBoard();
        boardArr[0][4] = new King(Side.BLACK, false);
        boardArr[0][7] = new Rook(Side.BLACK, false);
        boardArr[7][4] = new King(Side.WHITE, false);
        boardArr[7][0] = new Rook(Side.WHITE, false);
        
        Board board1 = new Board(boardArr);
        assertB(board1.isValid(Human.moveOf("e8g8"), Side.BLACK));
        assertB(board1.isValid(Human.moveOf("e1c1"), Side.WHITE));
    }

    private static void pawnRewards() {
        Board board = new Board(Board.startBoard());
        assertB(Bot.getValuation(board) == 0);
    }
}
