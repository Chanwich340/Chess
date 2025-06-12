public class Queen extends Piece {
    
    // Constructor
    public Queen(int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite, "Queen");
    }
    
    // Method to check if the queen can move to a specific position
    public boolean canMoveTo(int newX, int newY) {
        int deltaX = Math.abs(newX - getPosX());
        int deltaY = Math.abs(newY - getPosY());
        
        // Queen can move any number of squares in a straight line (horizontally, vertically, or diagonally)
        return (deltaX == 0 || deltaY == 0 || deltaX == deltaY);
    }
    
    // Override moveTo method to include queen-specific logic if needed
    @Override
    public void moveTo(int newX, int newY) {
        if (canMoveTo(newX, newY)) {
            super.moveTo(newX, newY);
        } else {
            throw new IllegalArgumentException("Invalid move for Queen");
        }
    }

}
