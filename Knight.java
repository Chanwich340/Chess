public class Knight extends Piece {

    // Constructor
    public Knight(int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite, "Knight");
    }

    // Method to check if the knight can move to a specific position
    public boolean canMoveTo(int newX, int newY) {
        int deltaX = Math.abs(newX - getPosX());
        int deltaY = Math.abs(newY - getPosY());

        // Knight moves in an "L" shape: two squares in one direction and one square perpendicular
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }

    // Override moveTo method to include knight-specific logic if needed
    @Override
    public void moveTo(int newX, int newY) {
        if (canMoveTo(newX, newY)) {
            super.moveTo(newX, newY);
        } else {
            throw new IllegalArgumentException("Invalid move for Knight");
        }
    }

}
