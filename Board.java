import java.util.ArrayList;

public class Board {
    private final Piece[][] board;

    public static record WrapperMove(Move move, double valuation) {};

    public static class Move {
        public final int startX;
        public final int startY;
        public final int endX;
        public final int endY;

        public Move(int startX, int startY, int endX, int endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        public void print() {
            System.out.println(startX + ", " + startY + ", " + endX + ", " + endY);
        }

        public PieceSupplier getEndPiece(Board board) {
            return idc -> board.getPiece(startX, startY);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Move)) {
                return false;
            }

            Move move = (Move) obj;

            return startX == move.startX && startY == move.startY && endX == move.endX && endY == move.endY;
        }
    }

    public static class Promotion extends Move {
        public final PieceSupplier promotionPiece;

        public Promotion(int startX, int startY, int endX, int endY, PieceSupplier promotionPiece) {
            super(startX, startY, endX, endY);
            this.promotionPiece = promotionPiece;
            
        }

        @Override
        public PieceSupplier getEndPiece(Board board) {
            return promotionPiece;
        }

        @Override
        public void print() {
            System.out.println(startX + ", " + startY + ", " + endX + ", " + endY + ", " + promotionPiece.accept(Side.BLACK));
        }
    }

    public static class Position {
        public final int x;
        public final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) {
            return false;
        }

        Board other = (Board) obj;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(getPiece(j, i).getClass() == other.getPiece(j, i).getClass())) {
                    return false;
                }
            }
        }

        return true;
    }

    public void print() {
        String block = "|----";
        System.out.println("  " + block + block + block + block + block + block + block + block +"|");
        for (int i = 0; i < 8; i++) {
            System.out.print(8-i + " |");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println();
            System.out.println("  " + block + block + block + block + block + block + block + block +"|");
        }

        System.out.println("     a    b    c    d    e    f    g    h");
    }

    public Board(Piece[][] board) {
        this.board = board;
    }

    public static Piece[] pawnRow(Side side) {
        Piece[] row = new Piece[8];

        for (int i = 0; i < row.length; i++) {
            row[i] = new Pawn(side, false);
        }

        return row;
    }

    public static Piece[] faceRow(Side side) {
        Piece[] row = {new Rook(side, false), new Knight(side, false), new Bishop(side, false), new Queen(side, false), new King(side, false), new Bishop(side, false), new Knight(side, false), new Rook(side, false)};

        return row;
    }

    public static Piece[] noRow() {
        Piece[] row = new Piece[8];

        for (int i = 0; i < row.length; i++) {
            row[i] = new None();
        }

        return row;
    }

    public static Piece[][] noBoard() {
        Piece[][] sampleBoardArr = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            sampleBoardArr[i] = Board.noRow();
        }

        return sampleBoardArr;
    }

    public static Piece[][] startBoard() {
        final Piece[][] board = new Piece[8][8];

        board[0] = Board.faceRow(Side.BLACK);
        board[1] = Board.pawnRow(Side.BLACK);
        for (int i = 2; i < 6; i++) {
            board[i] = Board.noRow();
        }
        board[6] = Board.pawnRow(Side.WHITE);
        board[7] = Board.faceRow(Side.WHITE);

        return board;
    }

    public Board movePiece(Board.Move move, Side currentSide) {
        Piece endPiece = move.getEndPiece(this).accept(currentSide);
        Piece[][] newBoardArr = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            newBoardArr[i] = board[i].clone();
            for (int j = 0; j < 8; j++) {
                if (newBoardArr[i][j] instanceof GhostPawn) {
                    newBoardArr[i][j] = new None();
                }
            }
        }

        newBoardArr[move.endY][move.endX] = endPiece;
        newBoardArr[move.startY][move.startX] = new None();

        if (getPiece(move.startX, move.startY) instanceof Pawn) {
            if (move.endY - move.startY == 2) {
                newBoardArr[move.endY - 1][move.startX] = new GhostPawn();
            } else if (move.endY - move.startY == -2) {
                newBoardArr[move.endY + 1][move.startX] = new GhostPawn();
            }
            
            if (getPiece(move.endX, move.endY) instanceof GhostPawn && move.endY == 2) {
                newBoardArr[move.endY + 1][move.endX] = new None();
            } else if (getPiece(move.endX, move.endY) instanceof GhostPawn && move.endY == 5) {
                newBoardArr[move.endY - 1][move.endX] = new None();
            }
        }

        else if (getPiece(move.startX, move.startY) instanceof King && !getPiece(move.startX, move.startY).hasMoved() && move.endY - move.startY == 0 && Math.abs(move.endX - move.startX) == 2) {
            //kingside
            if (move.endX > move.startX) {
                newBoardArr[move.startY][move.startX + 1] = new Rook(currentSide);
                newBoardArr[move.startY][7] = new None();
            }

            //queenside
            if (move.startX > move.endX) {
                newBoardArr[move.startY][move.startX - 1] = new Rook(currentSide);
                newBoardArr[move.startY][0] = new None();
            }
        }

        return new Board(newBoardArr);
    }

    public Piece getPiece(int x, int y) {
        return board[y][x];
    }

    public Position findKing(Side side) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] instanceof King && board[i][j].getSide() == side) {
                    return new Position(j, i);
                }
            }
        }
        throw new RuntimeException("King missing!" + side);
    }

    public boolean isCheck(Side otherSide) {
        Position otherKingPos = findKing(otherSide);

        for (int i = 0; i < 8; i++) { 
            for (int j = 0; j < 8; j++) {
                if (board[i][j].isValid(new Move(j, i, otherKingPos.x, otherKingPos.y), this)) {
                        return true;
                } 
                if (board[i][j].isValid(new Board.Promotion(j, i, otherKingPos.x, otherKingPos.y, s -> new None()), this)) {
                    return true;
                };
            }
        }
        return false;
    }

    public ArrayList<Move> legalMoves(Side side) {
        ArrayList<Move> legalMoves = new ArrayList<>(); 
        PieceSupplier[] promotionList = {s -> new Queen(s), s -> new Rook(s), s -> new Bishop(s), s -> new Knight(side)};
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int i2 = 0; i2 < 8; i2++) {
                    for (int j2 = 0; j2 < 8; j2++) {
                        if (isValid(new Move(j, i, j2, i2), side)) {
                            legalMoves.add(new Move(j, i, j2, i2));
                        }

                        for (int a = 0; a < 4; a++) {
                            if (isValid(new Promotion(j, i, j2, i2, promotionList[a]), side)) {
                                legalMoves.add(new Promotion(j, i, j2, i2, promotionList[a]));
                            }
                        }
                    }
                }
            }
        }

        return legalMoves;
    }

    public boolean isValid(Move move, Side currentSide) {
        if (move instanceof Board.Promotion && !(getPiece(move.startX, move.startY) instanceof Pawn)) {
            return false;
        }

        if (getPiece(move.startX, move.startY) instanceof King && !getPiece(move.startX, move.startY).hasMoved() && move.endY - move.startY == 0 && Math.abs(move.endX - move.startX) == 2) {
            if (castleValid(move, currentSide)) {
                return true;
            }
            return false;
        }

        Piece piece = board[move.startY][move.startX];
        Piece[][] newBoardArr = new Piece[8][8];

        //How does java not have a deep copy method
        for (int i = 0; i < 8; i++) {
            newBoardArr[i] = board[i].clone();
        }

        if (board[move.startY][move.startX].getSide() != currentSide) {
            return false;
        }

        if(!piece.isValid(move, this)) {
            return false;
        }

        //staging the move
        newBoardArr[move.endY][move.endX] = piece;
        newBoardArr[move.startY][move.startX] = new None();

        Board newBoard = new Board(newBoardArr);

        if (newBoard.isCheck(currentSide)) {
            return false;
        };

        return true;
    }

    public boolean castleValid(Move move, Side currentSide) {
        Piece[][] newBoardArr = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            newBoardArr[i] = board[i].clone();
        }

        if (isCheck(currentSide) || getPiece(move.startX, move.startY).hasMoved()) {
            return false;
        }

        //Kingside
        if (move.endX > move.startX) {
            if (getPiece(7, move.startY) instanceof Rook && !getPiece(7, move.startY).hasMoved() && getPiece(7, move.startY).isValid(new Move(7, move.startY, 5, move.startY), this) && getPiece(5, move.startY) instanceof None) {
                if (!isValid(new Move(move.startX, move.startY, move.startX + 1, move.startY), currentSide)) {
                    return false;
                }
                newBoardArr[move.startY][move.startX + 1] = new King(currentSide);
                newBoardArr[move.startY][move.startX] = new None();
                Board newBoard = new Board(newBoardArr);
                if (!newBoard.isValid(new Move(move.startX + 1, move.startY, move.endX, move.endY), currentSide)) {
                    return false;
                }
                return true;
            }

            return false;
        }

        //Queenside
        else if (move.startX > move.endX) {
            if (getPiece(0, move.startY) instanceof Rook && !getPiece(0, move.startY).hasMoved() && getPiece(0, move.startY).isValid(new Move(0, move.startY, 3, move.startY), this) && getPiece(3, move.startY) instanceof None) {
                if (!isValid(new Move(move.startX, move.startY, move.startX - 1, move.startY), currentSide)) {
                    return false;
                }
                newBoardArr[move.startY][move.startX - 1] = new King(currentSide);
                newBoardArr[move.startY][move.startX] = new None();
                Board newBoard = new Board(newBoardArr);
                if (!newBoard.isValid(new Move(move.startX - 1, move.startY, move.endX, move.endY), currentSide)) {
                    return false;
                }
                return true;
            }

            return false;
        }

        else {
            throw new RuntimeException("Math doesn't work");
        }
    }
}
