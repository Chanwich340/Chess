import java.util.ArrayList;

public class Board  {
    // 8x8 grid to hold Piece objects (use null for empty squares)
    private ArrayList<ArrayList<Piece>> grid;

    public Board()  {
        initializeBoard();
    }

    // Initializes the 8x8 board with empty square
    private void initializeBoard()  {
        grid = new ArrayList<>(8);

        for (int row = 0; row < 8; row++) {
            ArrayList<Piece> currentRow = new ArrayList<>(8);
            for (int col = 0; col < 8; col++) {
                currentRow.add(null); // No piece at start
            }
            grid.add(currentRow);
        }
    }

    // Get the piece at a specific row and column
    public Piece getPiece(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid.get(row).get(col);
        }
        return null;
    }

    // To place a piece at a specific row and column
    public void setPiece(int row, int col, Piece piece) {
        if (isValidPosition(row, col)) {
            grid.get(row).set(col, piece);
        }
    }

    // Remove a piece from a specific position
    public void removePiece(int row, int col) {
        if (isValidPosition(row, col)) {
            grid.get(row).set(col, null);
        }
    }

    // Check if a position is within the board boundaries
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Convert chess notation to row and column
    public static int[] notationToIndex(String notation) {
        if (notation.length() != 2) return null;
        char file = notation.charAt(0);
        char rank = notation.charAt(1);
        int col = file - 'a';
        int row = 8 - (rank - '0');
        if (col < 0 || col > 7 || row < 0 || row > 7) return null;
        return new int[] { row, col };
    }

    // Get piece by chess notation
    public Piece getPiece(String notation) {
        int[] pos = notationToIndex(notation);
        if (pos != null) {
            return getPiece(pos[0], pos[1]);
        }
        return null;
    }

    // Set piece by chess notation
    public void setPiece(String notation, Piece piece) {
        int[] pos = notationToIndex(notation);
        if (pos != null) {
            setPiece(pos[0], pos[1], piece);
        }
    }
}