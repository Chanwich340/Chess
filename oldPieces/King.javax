public class King extends Piece {
    
    // Constructor
    public King(int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite, "King");
    }
    
    // Method to check if the king can move to a specific position
    public boolean canMoveTo(int newX, int newY) {
        int deltaX = Math.abs(newX - getPosX());
        int deltaY = Math.abs(newY - getPosY());
        
        // King can move one square in any direction
        return (deltaX <= 1 && deltaY <= 1);
    }
    
    // Override moveTo method to include king-specific logic if needed
    @Override
    public void moveTo(int newX, int newY) {
        if (canMoveTo(newX, newY)) {
            super.moveTo(newX, newY);
        } else {
            throw new IllegalArgumentException("Invalid move for King");
        }
    }
    
}
