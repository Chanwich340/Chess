public class Bishop extends Piece {

    // Constructor
    public Bishop(int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite, "Bishop");
    }

    // Method to check if the bishop can move to a specific position
    public boolean canMoveTo(int newX, int newY) {
        int deltaX = Math.abs(newX - getPosX());
        int deltaY = Math.abs(newY - getPosY());

        // Bishop can move any number of squares diagonally
        return (deltaX == deltaY);
    }

    // Override moveTo method to include bishop-specific logic if needed
    @Override
    public void moveTo(int newX, int newY) {
        if (canMoveTo(newX, newY)) {
            super.moveTo(newX, newY);
        } else {
            throw new IllegalArgumentException("Invalid move for Bishop");
        }
    }

}
