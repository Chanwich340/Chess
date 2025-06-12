public class Pawn extends Piece {

    // Constructor
    public Pawn(int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite, "Pawn");
    }

    // Method to check if the pawn can move to a specific position
    public boolean canMoveTo(int newX, int newY) {
        int deltaX = Math.abs(newX - getPosX());
        int deltaY = Math.abs(newY - getPosY());

        // Pawn can move forward one square (or two squares from its starting position)
        if (isWhite()) {
            return (deltaX == 0 && (deltaY == 1 || (getPosY() == 1 && deltaY == 2)));
        } else {
            return (deltaX == 0 && (deltaY == -1 || (getPosY() == 6 && deltaY == -2)));
        }
    }

    // Override moveTo method to include pawn-specific logic if needed
    @Override
    public void moveTo(int newX, int newY) {
        if (canMoveTo(newX, newY)) {
            super.moveTo(newX, newY);
        } else {
            throw new IllegalArgumentException("Invalid move for Pawn");
        }
    }   

}
