public class Rook extends Piece {

    // Constructor
    public Rook(int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite, "Rook");
    }

    // Method to check if the rook can move to a specific position
    public boolean canMoveTo(int newX, int newY) {
        int deltaX = Math.abs(newX - getPosX());
        int deltaY = Math.abs(newY - getPosY());

        // Rook can move any number of squares in a straight line (horizontally or vertically)
        return (deltaX == 0 || deltaY == 0);
    }

    // Override moveTo method to include rook-specific logic if needed
    @Override
    public void moveTo(int newX, int newY) {
        if (canMoveTo(newX, newY)) {
            super.moveTo(newX, newY);
        } else {
            throw new IllegalArgumentException("Invalid move for Rook");
        }
    }

}
