import java.util.ArrayList;

public class Bot implements Player {
    public final int depth;
    public final Side side;

    public Bot(Side side, int depth) {
        this.side = side;
        if (depth < 1) {
            throw new RuntimeException("Invalid bot configuration!");
        }
        this.depth = depth;
    }

    @Override
    public String toString() {
        return side.toString();
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public Board.Move getInput(Board board) {
        Board.WrapperMove move = lookAhead(board, depth, -1 * Math.pow(10, 7), 1 * Math.pow(10, 7), side);
        move.move().print();
        System.out.println("original board: " + getValuation(board));
        System.out.println(move.valuation());
    
        return move.move();
    }
    
    public static Board.WrapperMove lookAhead(Board board, int depth, double alpha, double beta, Side side) {
        ArrayList<Board.Move> myLegalMoves = board.legalMoves(side);
        Side otherSide = side == Side.WHITE ? Side.BLACK : Side.WHITE;
        int scalar = side == Side.WHITE ? 1 : -1;

        if (depth == 1) {
            Board.WrapperMove currBest = new Board.WrapperMove(null, -scalar * (Math.pow(10, 7)));
    
            for (int i = 0; i < myLegalMoves.size(); i++) {
                double newValuation = getValuation(board.movePiece(myLegalMoves.get(i), side));
                if (newValuation * scalar > currBest.valuation() * scalar) {
                    currBest = new Board.WrapperMove(myLegalMoves.get(i), newValuation);
                }
            }

            return currBest;
        }

        Board.WrapperMove oppWorst = new Board.WrapperMove(null, -scalar * Math.pow(10, 7));
        int index = 0;

        for (int i = 0; i< myLegalMoves.size(); i++) {
            Board.WrapperMove curr = lookAhead(board.movePiece(myLegalMoves.get(i), side), depth - 1, alpha, beta, otherSide);

            if (curr.valuation() * scalar > oppWorst.valuation() * scalar) {
                oppWorst = curr;
                index = i;
            }
            
            if (side == Side.WHITE) {
                alpha = Math.max(alpha, curr.valuation());
            } else {
                beta = Math.min(beta, curr.valuation());
            }

            if (beta <= alpha) {
                break;
            }
        }
        
        return new Board.WrapperMove(myLegalMoves.get(index), oppWorst.valuation());
    }

    public static double getValuation(Board board) {
        if (board.isCheck(Side.WHITE) && board.legalMoves(Side.WHITE).size() == 0) {
            return -1 * 10^6;
        }

        if (board.isCheck(Side.BLACK) && board.legalMoves(Side.BLACK).size() == 0) {
            return 1 * 10^6;
        }

        double valuation = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                double piece_value = getPieceValue(board, new Board.Position(j, i));
                valuation += piece_value;
            }
        }

        return valuation;
    }

    public static double getPieceValue(Board board, Board.Position piecePosition) {
        Piece piece = board.getPiece(piecePosition.x, piecePosition.y);
        int scalar = 1;
        
        if (piece.getSide() == Side.BLACK) {
            scalar = -1;
            piecePosition = new Board.Position(7 - piecePosition.x, 7 - piecePosition.y);
        }

        if (piece instanceof Pawn) {
            return scalar * (1 + ValuationConstants.whitePawnRewards[piecePosition.y][piecePosition.x]);
        } else if (piece instanceof Knight) {
            return scalar * (3 + ValuationConstants.whiteKnightRewards[piecePosition.y][piecePosition.x]);
        } else if (piece instanceof Bishop) {
            return scalar * (3 + 0);
        } else if (piece instanceof Rook) {
            return scalar * (5 + 0);
        } else if (piece instanceof Queen) {
            return scalar * (9 + 0);
        } else if (piece instanceof King) {
            return scalar * ValuationConstants.whiteKingRewards[piecePosition.y][piecePosition.x];
        }

        //None
        return 0;
    }
}
